import {GlobalHelperService} from "../../../services/global-helper-service/global-helper-service";
import {CordovaGeofenceService} from "../../../services/cordova-geofence/cordova-geofence.service";
import {TICKETS, JsonHandlerService} from "../../../services/json-handler/json-handler.service";
import {ModalHelperService} from "../../../services/modal-helper-service/modal-helper.service";
import {Component, OnDestroy, Output, EventEmitter} from '@angular/core';
import {MaterializeDirective} from 'angular2-materialize';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {Meteor} from 'meteor/meteor';

@Component({
    selector: 'provider-settings',
    templateUrl: 'client/imports/components/provider/provider-settings/provider-settings.component.html', // OR html in file:
    styleUrls: ['./styles/provider-settings.component.min.css'],
    directives: [MaterializeDirective],
    pipes: []
})
export class ProviderSettingsComponent implements OnDestroy {

    private _ticketProvided: boolean;
    private _ticket: TICKETS;

    private _tickets: Object[] = [];

    private _subs: Subscription[];

    @Output() onDisabled = new EventEmitter<boolean>();

    constructor(
        private _modalHelper: ModalHelperService,
        private _jsonHandler: JsonHandlerService,
        private _router: Router,
        private _geofence: CordovaGeofenceService,
        private _globHelper: GlobalHelperService
    ) {
        // received from TICKETS class in JsonHandlerService
        // preparation for ngfor loop
        for (let ticket in TICKETS) {
            this._tickets.push({ key: ticket, value: TICKETS[ticket] });
        }
    }

    /**
     * ngOnDestroy - save the file when component is destroyed
     */
    ngOnDestroy() {
        this._jsonHandler.saveFile();
    }


    /**
     * toggleProvided - toggle state of ticketProvided
     *
     * @return {void}
     */
    private toggleProvided() {
        this._jsonHandler.ticketProvided = !this._jsonHandler.ticketProvided;
        if (!this._jsonHandler.ticketProvided) {
            this._globHelper.loadingMessage = 'removing geofences';
            if (Meteor.isCordova) {
                this._geofence.destroyAll().then(
                    (res) => {
                        this.onDisabled.emit(true);
                        this._globHelper.loadingMessage = null;
                    }, (err) => {
                        this.onDisabled.emit(true);
                        this._globHelper.loadingMessage = null;
                    }
                );
            } else {
                this.onDisabled.emit(true);
                this._globHelper.loadingMessage = null;
            }
        }
    }

    /**
     * reset - reset the json file and geofences to initial state navigate to
     *         start screen
     *
     * @return {void}
     */
    private reset() {
        this._modalHelper.closeSettingsModal();
        this._globHelper.loadingMessage = 'resetting app';
        // HACK for modal close bu
        // this._modalHelper.removeOverlay();
        this._jsonHandler.reset();
        if (Meteor.isCordova) {
            this._geofence.destroyAll().then(
                (res) => {
                    this._globHelper.loadingMessage = null;
                    this._router.navigate([`/`]);
                }, (err) => {
                    this._globHelper.loadingMessage = null;
                    this._router.navigate([`/`]);
                }
            );
        } else {
            this._globHelper.loadingMessage = null;
            this._router.navigate([`/`]);
        }
    }

    /**
     * save - save the values to the json file
     *
     * @return {void}
     */
    private save() {
        this._jsonHandler.saveFile();
    }
}
