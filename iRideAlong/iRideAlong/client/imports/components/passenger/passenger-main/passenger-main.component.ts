import {Station} from "../../../services/station-helper/station";
import {Component} from '@angular/core';
import {CordovaGeofenceService} from "../../../services/cordova-geofence/cordova-geofence.service";
import {CordovaGeolocationService} from "../../../services/cordova-geolocation/cordova-geolocation.service";
import {GlobalHelperService, Toasts} from "../../../services/global-helper-service/global-helper-service";
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {GoogleMapsWrapperComponent} from "../../util/google-maps/components";
import {Meteor} from 'meteor/meteor';
import {ModalHelperService} from "../../../services/modal-helper-service/modal-helper.service";
import {NotificationService} from "../../../services/notification-service/notification-service.service";
import {PassengerConfirmationComponent} from "../passenger-confirmation/passenger-confirmation.component";
import {PassengerDestinationComponent} from "../passenger-destination/passenger-destination.component";
import {PassengerSearchComponent} from "../passenger-search/passenger-search.component";
import {RedisPubSubService} from "../../../services/redis-pubsub/redis-pubsub.service";
import {StationHelperService} from "../../../services/station-helper/station-helper.service";
import {TYPE, JsonHandlerService} from "../../../services/json-handler/json-handler.service";
import {Subscription} from 'rxjs';

@Component({
    selector: 'passenger-main',
    templateUrl: 'client/imports/components/passenger/passenger-main/passenger-main.component.html', // OR html in file:
    styleUrls: ['./styles/passenger-main.component.min.css'],
    directives: [
        PassengerDestinationComponent,
        PassengerConfirmationComponent,
        PassengerSearchComponent,
        PassengerSearchComponent,
        GoogleMapsWrapperComponent
    ]
})
export class PassengerMainComponent {

    // outgoing:
    private _station: Station;

    // incomming:
    private _meetingpoint: string;   // passed to child component: confirmation
    private _identifier: string;     // passed to child component: confirmation

    private _sub: Subscription;

    private _destinationOpen: boolean = false;

    private _lastRequest: number;

    private _awaitingConfirmation: boolean;

    constructor(
        private _geofence: CordovaGeofenceService,
        private _redisService: RedisPubSubService,
        private _notificationService: NotificationService,
        private _modalHelper: ModalHelperService,
        private _jsonHandler: JsonHandlerService,
        private _stationHelper: StationHelperService,
        private _globalHelper: GlobalHelperService
    ) {
        this._jsonHandler.type = TYPE.passenger;
        this._jsonHandler.saveFile();
        // handle if openend from notification click
        this._globalHelper.openedFromNotification(
            this._geofence, this._stationHelper, (station) => this.subscribe(station), true
        );
        this._sub =
            this._stationHelper.unsubscribedStations$.subscribe((channels) => {
                for (let channel of channels) {
                    if (!this._awaitingConfirmation && this._station && channel === this._station.id) {
                        Toasts.autoLogout(this._station.name);
                        this.onDecline(false);
                        this.unsubscribe();
                    }
                }
            });
    }

    ngOnInit() {
        this._geofence.init();
    }

    /**
     * subscribe - sub & set channel, and start timer for unsub after 5 min
     *
     * @param  {string} channel the channel to be subscribed to
     * @return {void}
     */
    private subscribe(station: Station) {
        this._globalHelper.loadingMessage = 'entering station';
        this._station = station;
        this._redisService.subscribe(station.id, (res) => this._globalHelper.loadingMessage = null);
    }

    /**
     * ngOnDestroy - lifecycle hook shortly prior to destruction of comp
     *               handle unsubscriptions
     *
     * @return {void}
     */
    ngOnDestroy() {
        this.unsubscribe();
        this._sub.unsubscribe();
        this._notificationService.clearAll();
    }

    /**
     * unsubscribe - from channel and set channel undefined
     *
     * @return {type}  description
     */
    private unsubscribe() {
        if (this._station) {
            this._globalHelper.loadingMessage = 'leaving station';
            this._redisService.unsubscribe(this._station.id, (res) => this._globalHelper.loadingMessage = null);
            this._station = undefined;
        }
    }

    /**
     * onSend - Event handler listens to 'onSend' event in child comp (destination)
     *
     * @param  {string} lindir: string semicolon seperated line and direction
     * @returns {void}
     */
    private onSend(lindir: string) {
        this.subscribe(this._station);
        this.publishRequest(lindir);
        this._destinationOpen = false;
    }

    private onDecline(val: boolean) {
        this._lastRequest = undefined;
        this._destinationOpen = false;
    }

    /**
     * onStationClick -Event handler listens to 'onStationClick' in child comp (google-maps-wrapper)'
     *
     * @param  {evt} Object: Object with station infos
     * @return {void}
     */
    private onStationClick(evt: Object) {
        // allow one request per 30 sec
        if (!this._lastRequest || this._lastRequest + 30000 < new Date().getTime()) {
            if (this._station) {
                this.unsubscribe();
            }
            if (evt && evt['station']) {
                this._station = evt['station'];
                this._destinationOpen = true;
                this._lastRequest = new Date().getTime();
            }
        } else {
            Toasts.tooManyRequests();
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
            this.unsubscribe();
        }
    }

    /**
     * public - sends request with line + direction (from destination-comp) to channel
     *
     * @param  {type} lindir: string semicolon seperated line and direction
     * @returns {void}
     */
    public publishRequest(lindir: string) {
        this._globalHelper.loadingMessage = 'sending request';
        this._redisService.publish(this._station.id, lindir, (res) => {
            this._globalHelper.loadingMessage = null;
            if (res === 'OK') {
                Toasts.messageSent();
                this.getConfirmation();
            } else {
                console.error("publishRequest error", res);
                Toasts.messageNotSent();
            }
        });
    }

    /**
     * public - listens to channel for confirmation
     *
     * @returns {void}
     */
    private getConfirmation() {
        this._awaitingConfirmation = true;
        let msgObs = this._redisService.privateMessageObject$.subscribe(
            (data) => {
                let message = JSON.parse(data['message']);
                this._meetingpoint = message['meetingpoint'];
                this._identifier = message['identifier'];
                if (!Meteor.isCordova) {
                    this._modalHelper.openConfirmedModal()
                    this.unsubscribe();
                } else {
                    this._notificationService.scheduleConfirmation(this._meetingpoint, this._identifier);
                    let notiObs = this._notificationService.confirmations$.subscribe(
                        (noti) => {
                            this._modalHelper.openConfirmedModal();
                            notiObs.unsubscribe();
                            this._awaitingConfirmation = false;
                        },
                        (error) => {
                            Toasts.generalError();
                            notiObs.unsubscribe();
                            console.error(error);
                            this._awaitingConfirmation = false;
                        }
                    );
                }
                this.unsubscribe();
                msgObs.unsubscribe();
            },
            (error) => {
                Toasts.generalError();
                msgObs.unsubscribe();
                console.error(error);
            }
        );
    }


}
