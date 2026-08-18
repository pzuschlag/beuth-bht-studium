import {Toasts, GlobalHelperService} from "../../../services/global-helper-service/global-helper-service";
import {Station} from "../../../services/station-helper/station";
import {Component, OnInit, OnDestroy} from '@angular/core';
import {CordovaGeofenceService} from "../../../services/cordova-geofence/cordova-geofence.service";
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {GoogleMapsWrapperComponent} from "../../util/google-maps/components/google-maps-wrapper/google-maps-wrapper.component";
import {Meteor} from 'meteor/meteor';
import {ModalHelperService} from "../../../services/modal-helper-service/modal-helper.service";
import {NotificationService} from "../../../services/notification-service/notification-service.service";
import {ProviderAcceptComponent} from "../provider-accept/provider-accept.component";
import {ProviderKarmaComponent} from "../provider-karma/provider-karma.component";
import {ProviderMeetingpointComponent} from "../provider-meetingpoint/provider-meetingpoint.component";
import {ProviderSettingsComponent} from "../provider-settings/provider-settings.component";
import {RedisPubSubService} from "../../../services/redis-pubsub/redis-pubsub.service";
import {StationHelperService} from "../../../services/station-helper/station-helper.service";
import {Subscription} from "rxjs/Subscription";
import {TICKETS, TYPE, JsonHandlerService} from "../../../services/json-handler/json-handler.service";

@Component({
    selector: 'provider-main',
    templateUrl: 'client/imports/components/provider/provider-main/provider-main.component.html', // OR html in file:
    styleUrls: ['./styles/provider-main.component.min.css'],
    directives: [
        GoogleMapsWrapperComponent,
        ProviderAcceptComponent,
        ProviderKarmaComponent,
        ProviderMeetingpointComponent,
        ProviderSettingsComponent
    ]
})
export class ProviderMainComponent implements OnDestroy {

    // incomming:
    private _station: Station;

    private _passengerId: string;

    private _line: string;    // passed to child comp: accept
    private _direction: string; // passed to child comp: accept

    private _lastMessage: string;

    private _meetingpointOpen: boolean = false;

    private _loadingNotice: string;

    constructor(
        private _redisService: RedisPubSubService,
        private _notificationService: NotificationService,
        private _modalHelper: ModalHelperService,
        private _geofence: CordovaGeofenceService,
        private _jsonHandler: JsonHandlerService,
        private _stationHelper: StationHelperService,
        private _globalHelper: GlobalHelperService
    ) {
        // handle if openend from notification click
        this._globalHelper.openedFromNotification(
            this._geofence, this._stationHelper, (station) => this.subscribe(station));

    }

    ngOnInit() {
        this._geofence.init();

        if (!this._jsonHandler.type || !this._jsonHandler.ticket || !this._jsonHandler.ticketProvided) {
            this._modalHelper.openSettingsModal();
        }
        this._jsonHandler.type = TYPE.provider;
        this._jsonHandler.saveFile();
    }

    /**
     * ngOnDestroy - lifecycle hook shortly prior to destruction of comp
     *               handle unsubscriptions + clears all notifications
     *
     * @return {void}
     */
    ngOnDestroy() {
        this.onAccept(false);
        this._notificationService.clearAll();
    }

    /**
     * onDisabled - Event handler listens to 'onDisabled' event in child comp: settings
     *
     * @param  {boolean} val
     * @returns {void}
     */
    private onDisabled(val: boolean) {
        this.onAccept(false);
    }

    /**
     * noTicketProvided - Trigger toast, open settings modal
     *
     * @returns {void}
     */
    private noTicketProvided() {
        Toasts.noTicket();
        this._modalHelper.openSettingsModal();
    }

    /**
     * subscribe - sub & set channel, listen for messages,
     *             and start timer for unsub after 5 min
     *
     * @param  {string} channel the channel to be subscribed to
     * @return {void}
     */
    private subscribe(station: Station) {
        if (!this._jsonHandler.ticketProvided) {
            this.noTicketProvided();
        } else if (station) {
            this._station = station;
            this._globalHelper.loadingMessage = 'entering station';
            this._redisService.subscribe(this._station.id, (res) => {
                this._globalHelper.loadingMessage = null;
                if (res === 'OK') {
                    this.receiveRequest();
                    Toasts.greeting(this._station.name);
                } else {
                    Toasts.subscribeError(this._station.name);
                    console.error("subscribe error", res);
                }
            });
        } else {
            console.error("station not existing", station);
        }
    }

    /**
     * onSend - Event handler listens to 'onSend' event in child comp: meetingpoint
     *
     * @param  {string} mpid semicolon seperated meetingpoint and identifier
     * @returns {void}
     */
    private onSend(mpid: string) {
        this.sendConfirmation(mpid);
    }

    /**
     * onAccept - if the request is accepted unsubscribe from channel
     *
     * @param  {boolean} bool value if accepted || !
     * @return {void}
     */
    private onAccept(accepted: boolean) {
        if (this._station) {
            if (!accepted) {
                //local notification if app is closed
                this.unsubscribe(this._station.id, true);
            } else {
                this._meetingpointOpen = true;
            }
        }
    }

    /**
     * onDecline - if the request is declined or the modal closed
     *            subscribe to messageObjects again
     *
     * @param  {boolean} bool value if declined || !
     * @return {void}
     */
    private onDecline(acceptModal: boolean) {
        if (acceptModal && this._station) {
            this.receiveRequest();
        } else {
            this._meetingpointOpen = false;
            Toasts.confirmationNotSent();
        }
    }

    /**
     * onStationClick - Event handler listens to 'onStationClick' in child comp (google-maps-wrapper)'
     *
     * @param  {evt} Object: Object with station infos
     * @return {void}
     */
    private onStationClick(evt: Object) {
        if (!evt || this._station) {
            this.onAccept(false);
        }
        if (evt && evt['station']) {
            this.subscribe(evt['station']);
        }
    }

    /**
     * onClosestStation - Event handler listens to 'onClosestStation' in child comp (google-maps-wrapper)'
     *
     * @param  {evt} Station: instance
     * @return {void}
     */
    private onClosestStation(station: Station) {
        if (this._station && station && (station.id !== this._station.id || station.dist > this._station.dist)) {
            this.onAccept(false);
        }
    }

    /**
     * receiveRequest - listens to message channel for requests
     *
     * @returns {void}
     */
    private receiveRequest() {
        let msgObs = this._redisService.messageObject$.subscribe(
            (data) => {
                // dont get noticed twice from same passenger
                if (!this._passengerId || this._passengerId !== data['id'] || !this._lastMessage || this._lastMessage !== data['message']) {
                    this._lastMessage = data['message'];
                    let message = JSON.parse(data['message']);
                    this._passengerId = data['id'];

                    this._line = message['line'];
                    this._direction = message['direction'];
                    if (!Meteor.isCordova) {
                        this._modalHelper.openAcceptModal();
                    } else {
                        this._notificationService.scheduleRequest(this._line, this._direction);
                        let notiObs = this._notificationService.requests$.subscribe(
                            (noti) => {
                                this._modalHelper.openAcceptModal();
                                notiObs.unsubscribe();
                            },
                            (err) => {
                                Toasts.generalError();
                                notiObs.unsubscribe();
                                console.error(err);
                                this.receiveRequest();
                            }
                        );
                    }
                    msgObs.unsubscribe();
                } else {
                    console.log(`${this._passengerId} wrote twice: ${this._lastMessage}`);
                }
            },
            (error) => {
                Toasts.generalError();
                msgObs.unsubscribe();
                console.error(error);
                this.receiveRequest();
            }
        )
    }

    private unsubscribe(channel: string, manualLogout?: boolean) {
        this._globalHelper.loadingMessage = 'leaving station';
        this._redisService.unsubscribe(channel, (res) => {
            this._globalHelper.loadingMessage = null;
            if (res === 'OK') {
                if (manualLogout) {
                    if (!this._jsonHandler.ticket || !this._jsonHandler.ticketProvided) {
                        this.noTicketProvided();
                    } else {
                        this._notificationService.miscNotification("Unsubscribed", Toasts.goodbye(this._station.name));
                        setTimeout(this._notificationService.clearMisc(), 30000);
                    }
                }
                this._passengerId = this._station = this._lastMessage = undefined;
            } else {
                console.error("unsubscribe error", res);
            }
        });
    }

    /**
     * sendConfirmation - sends confirmation with meetingpoint + identifier in a private channel to specific passender
     *
     * @param  {string} mpid semicolon seperated meetingpoint and identifier
     * @returns {void}
     */
    public sendConfirmation(mpid: string) {
        this._meetingpointOpen = false;
        this._globalHelper.loadingMessage = 'sending confirmation';
        this._redisService.sendTo(this._passengerId, mpid, this._station.id, (res) => {
            this._globalHelper.loadingMessage = null;
            if (res === 'OK') {
                this.unsubscribe(this._station.id);
                Toasts.karmaThanks();
                Toasts.confirmationSent();
                this._jsonHandler.karmaPoints += 5;
                Toasts.karmaAdded();
                this._jsonHandler.saveFile();
            } else {
                this.receiveRequest();
                Toasts.passengerNotPresent();
                console.error('sendConfirmation error', res);
            }
        });
    }

}
