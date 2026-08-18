import {CordovaGeofenceService} from "../../../services/cordova-geofence/cordova-geofence.service";
import {JsonHandlerService} from "../../../services/json-handler/json-handler.service";
import {GlobalHelperService, Toasts} from "../../../services/global-helper-service/global-helper-service";
import {MapToArrayPipe} from "../../../pipes/map-to-array/map-to-array.pipe";
import {SearchArrayPipe} from "../../../pipes/search-array/search-array.pipe";
import {StationList} from "../../../services/station-helper/stationlist";
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {StationHelperService} from "../../../services/station-helper/station-helper.service";
import {Component, OnDestroy, EventEmitter, Output} from '@angular/core';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {Meteor} from 'meteor/meteor';

@Component({
    selector: 'passenger-search',
    templateUrl: 'client/imports/components/passenger/passenger-search/passenger-search.component.html', // OR html in file:
    styleUrls: ['./styles/passenger-search.component.min.css'],
    pipes: [MapToArrayPipe, SearchArrayPipe],
    directives: []
})

export class PassengerSearchComponent implements OnDestroy {

    private _subs: Subscription[] = [];
    private _stations: StationList;
    private _query: string = '';

    constructor(
        private _stationHelper: StationHelperService,
        private _gmHelper: GoogleMapsHelperService,
        private _globalHelper: GlobalHelperService,
        private _jsonHandler: JsonHandlerService,
        private _router: Router,
        private _geofence: CordovaGeofenceService,
        private _globHelper: GlobalHelperService
    ) {
        this._subs = [
            // subscribes to the current route and subscribes to updates
            this._stationHelper.stations$.subscribe(
                (stations) => this._stations = stations,
                (err) => console.error(err)
            )
        ]
    }

    private back() {
        this._globHelper.loadingMessage = 'loading';
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
     * setLatLng - sets map to in search selected station
     *
     * @param  {string} id: string station id
     */
    private setLatLng(id: string) {
        let station = this._stations.getStation(id);
        this._gmHelper.refreshCurrentLatLng(station.lat, station.lng, false);
        this._query = '';
    }

    /**
     * ngOnDestroy - unsubscribes from the observables stored in an array
     *               at component destruction
     */
    ngOnDestroy() {
        for (let sub of this._subs) {
            sub.unsubscribe();
        }
    }

    /**
     * currentLocation - sets map back to current location
     *
     */
    private currentLocation() {
        this._globHelper.loadingMessage = 'determining your location';
        this._gmHelper.refreshCurrentLatLng(null, null, true, (val: boolean) => {
            this._globHelper.loadingMessage = null;
            if (!val) {
                Toasts.locationError();
            }
        });
    }

}
