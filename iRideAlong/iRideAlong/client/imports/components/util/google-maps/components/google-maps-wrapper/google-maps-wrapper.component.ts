import {JsonHandlerService} from "../../../../../services/json-handler/json-handler.service";
import {GlobalHelperService, Toasts} from "../../../../../services/global-helper-service/global-helper-service";
import {Component, Output, Input, OnChanges, OnInit, OnDestroy, EventEmitter, NgZone, ChangeDetectionStrategy, ChangeDetectorRef} from '@angular/core';
import {CordovaGeofenceService} from "../../../../../services/cordova-geofence/cordova-geofence.service";
import {CordovaGeolocationService} from "../../../../../services/cordova-geolocation/cordova-geolocation.service";
import {GoogleMapsHelperService} from "../../services";
import {GoogleMapsMainComponent} from "../google-maps-main/google-maps-main.component";
import {GoogleMapsMarkerDirective, GoogleMapsCircleDirective} from "../../directives";
import {MapToArrayPipe} from "../../../../../pipes/map-to-array/map-to-array.pipe";
import {Meteor} from 'meteor/meteor';
import {ModalHelperService} from "../../../../../services/modal-helper-service/modal-helper.service";
import {NgStyle} from '@angular/common';
import {Observable, Subscription} from 'rxjs';
import {Station} from "../../../../../services/station-helper/station";
import {StationHelperService} from "../../../../../services/station-helper/station-helper.service";
import {StationList} from "../../../../../services/station-helper/stationlist";

@Component({
    selector: 'google-maps-wrapper',
    templateUrl: 'client/imports/components/util/google-maps/components/google-maps-wrapper/google-maps-wrapper.component.html',
    styleUrls: ['./styles/google-maps-wrapper.component.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [
        GoogleMapsCircleDirective,
        GoogleMapsMarkerDirective,
        GoogleMapsMainComponent
    ],
    providers: [
        CordovaGeofenceService,
        ModalHelperService
    ],
    pipes: [MapToArrayPipe],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class GoogleMapsWrapperComponent implements OnDestroy {

    private _lat: number;
    private _lng: number;
    private _lastLat: number;
    private _lastLng: number;

    private _disDefUI: boolean = true;
    private _zm: number = 16;
    private _zoomControl: boolean = true;

    private _stations: StationList;
    private _filteredStations: StationList = new StationList();

    private _subs: Subscription[] = [];

    private _resume;
    private _pause;

    private _lastStationClickedID: string;

    @Input() isProvider: boolean = true;
    @Output() onStationClick: EventEmitter<{}> = new EventEmitter<{}>();
    @Output() onClosestStation: EventEmitter<Station> = new EventEmitter<Station>();

    constructor(
        private _geofence: CordovaGeofenceService,
        private _gmHelper: GoogleMapsHelperService,
        private _modalHelper: ModalHelperService,
        private _stationHelper: StationHelperService,
        private _cd: ChangeDetectorRef,
        private _globHelper: GlobalHelperService,
        private _jsonHandler: JsonHandlerService,
        private _globHeloer: GlobalHelperService
    ) {
        this._globHelper.loadingMessage = 'Position is determined';
        this.init();
    }

    /**
     * init - init method - called from constructor
     *        init the new map, subscribe to currentlatlng and
     *        gets the new stations
     *
     * @return {type}  description
     */
    private init() {
        this._gmHelper.initNewMap();
        this._subs = [
            this._geofence.lastCoord$.subscribe((coord) => {
                if (coord && coord['lat'] && coord['lng']) {
                    this._lastLat = coord['lat'];
                    this._lastLng = coord['lng'];
                }
            }),
            // subscribes to the current lat|lng
            this._gmHelper.currentLatLng$.subscribe(
                (pos) => {
                    let lat = pos['lat'];
                    let lng = pos['lng'];
                    if (pos['refreshPosition']) {
                        this._geofence.lastCoord = { lat: lat, lng: lng };
                    }
                    if (!this._lat && !this._lng) {
                        this.refreshGeofencesInterval(lat, lng);
                    }
                    this._lat = lat;
                    this._lng = lng;
                    this._globHelper.loadingMessage = null;
                    this._cd.markForCheck(); // marks component for update
                },
                (err) => {
                    console.error(err);
                    // default value (center of berlin)
                    this._lat = 52.507629;
                    this._lng = 13.1449587;
                    this._globHelper.loadingMessage = null;
                    Toasts.locationError();
                }
            ),
            this._geofence.closestStation$.subscribe(
                (station) => this.onClosestStation.emit(station),
                (err) => console.error(err)
            ),
            // subscribes to the stations and subscribes to updates
            this._stationHelper.stations$.subscribe(
                (stations: StationList) => {
                    this._stations = stations;
                    this._cd.markForCheck(); // marks component for update
                },
                (err) => console.error(err)
            )
        ]
        // otherwise its not possible to change zoom (without gestures)
        if (Meteor.isCordova) {
            this._zoomControl = false;
        }
    }


    /**
     * refreshGeofencesInterval - set up interval monitoring of location and
     *                            resume / pause event listeners
     *
     * @return {void}  description
     */
    private refreshGeofencesInterval(lat: number, lng: number) {
        if (Meteor.isCordova) {
            this._geofence.setupMonitoring(
                this._stations, false,
                lat, lng
            );

            let self = this;
            this._resume = () => {
                self._geofence.setupMonitoring(
                    this._stations, false
                );
            };
            this._pause = () => {
                self._geofence.setupMonitoring(
                    this._stations, true
                )
            };
            document.addEventListener("resume", self._resume, false);
            document.addEventListener("pause", self._pause, false);
        }
    }

    /**
     * onBoundsChange - bounds:change on map event (from child component)
     *                  add all circles that are in bounds to array, for display
     *                  in map
     *
     * @param  {google.maps.LatLngBounds} bounds the new bounds of the map
     * @return {void}
     */
    private onBoundsChange(opt: Object) {
        let bounds: google.maps.LatLngBounds = opt['bounds'];
        let zoom: number = opt['zoom'];
        if (zoom && zoom < 14) {
            if (this._filteredStations.size > 0) {
                Toasts.zoomInMore();
            }
            this._filteredStations = new StationList();

        } else if (this._stations && bounds && this._lat && this._lng && zoom && zoom >= 14) {
            this._stations.map.forEach((station, key, map) => {
                this._gmHelper.isInBounds(bounds, station.lat, station.lng).then(inBounds => {
                    if (inBounds) {
                        this._filteredStations.addStation(station);
                    }
                });
            });
        }
    }

    /**
     * openDestination - click on circle event (from child component)
     *                   check multiple infos about station, send out error toasts,
     *                   emit if valid onStationClick event
     *
     * @param  {Station} station the clicked station
     * @return {void}
     */
    private openDestination(station: Station) {
        if (this.isProvider && this._lastStationClickedID && station.id === this._lastStationClickedID) {
            this.onStationClick.emit(null);
            this._lastStationClickedID = undefined;
        } else {
            // console.log("openDestination", station);
            if (!station.active && !this.isProvider) {
                Toasts.noProviders(station.name);
                this.onStationClick.emit(null);
                this._lastStationClickedID = undefined;
            } else if (this.isProvider && !this._jsonHandler.ticketProvided) {
                Toasts.noTicket();
                this._modalHelper.openSettingsModal();
            } else {
                this._geofence.isAuthorized(station, (res) => {
                    if (res['authorized']) {
                        res['station'] = station;
                    } else if (res['dist']) {
                        Toasts.distTooHigh(res['dist'], station.name);
                    } else {
                        Toasts.locationError();
                    }
                    this.onStationClick.emit(res['authorized'] ? res : null);
                    this._lastStationClickedID = res['authorized'] ? station.id : undefined;
                });
                // XXX only for devel purposes -> change allowedDist for meter testradius
            }
        }
    }

    /**
     * ngOnDestroy - unsubscribes from the observables stored in an array
     *               at component destruction
     */
    ngOnDestroy() {
        for (let sub of this._subs) {
            sub.unsubscribe();
        }
        this._geofence.stopMonitoring();
        let self = this;
        if (this._pause && this._resume) {
            document.removeEventListener("pause", self._pause);
            document.removeEventListener("resume", self._resume);
        }
    }
}
