import {Component, Output, Input, OnChanges, OnInit, OnDestroy, EventEmitter, ApplicationRef, NgZone} from '@angular/core';
import {NgStyle} from '@angular/common';
import {Observable, Subscription} from 'rxjs';
import {CordovaGeolocationService} from "../../../../../services/cordova-geolocation/cordova-geolocation.service";
import {GoogleMapsHelperService} from "../../services";
import {GoogleMapsPolylineDirective, GoogleMapsMarkerDirective} from "../../directives";
import {GoogleMapsMainComponent} from "../google-maps-main/google-maps-main.component";
import {RouteManagerService} from "../../../../../services/route-manager/route-manager.service";

import {Meteor} from 'meteor/meteor';

@Component({
    // moduleId: module.id,
    selector: 'google-maps-wrapper',
    templateUrl: 'client/imports/components/util/google-maps/components/google-maps-wrapper/google-maps-wrapper.component.html',
    styleUrls: ['./styles/google-maps-wrapper.component.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [
        GoogleMapsMainComponent,
        GoogleMapsMarkerDirective,
        GoogleMapsPolylineDirective
    ]
})
/**
 * Google maps wrapper component - wraps the Main map component and the used
 *                                 directives, handles and distributes the
 *                                 events emitted from children, subscribes to
 *                                 the geolocationService, routeManager and
 *                                 helperService
 */
export class GoogleMapsWrapperComponent implements OnDestroy {

    private lat: number = 52.520645;
    private lng: number = 13.409779;
    private disDefUI: boolean = true;
    private zm: number = 17;
    private radius: number = 120;
    private _inputWindow: Object;
    private div: any;
    private _path: any[];
    private _waypoints: any[];
    private _markedForRemoval: any;
    private _mapTypeId: string = 'TERRAIN';

    private _subs: Subscription[] = [];
    private _deactivateControlFunctions: boolean;

    constructor(
        private _gmHelperService: GoogleMapsHelperService,
        private _routeManagerService: RouteManagerService,
        private _applicationRef: ApplicationRef,
        private _ngZone: NgZone
    ) {
        // !! initialises the new map
        this._gmHelperService.initNewMap();

        this._subs = [
            // subscribes to the current lat|lng
            this._gmHelperService.currentLatLng$.subscribe(
                (pos) => {
                    this.lat = pos['lat'];
                    this.lng = pos['lng'];
                },
                (err) => console.error(err)
            ),
            // subscribes to the current route and subscribes to updates
            this._routeManagerService.currentRoute$.subscribe(
                (route) => {
                    console.log(route.waypoints);
                    this._path = [].concat(route.waypoints);
                    this._ngZone.run(() => console.log("ngZone run"));
                    // this._applicationRef.tick();
                },
                (err) => console.error(err)
            ),
            // subscribes to the deactivateControlFunctions boolean to check wether
            // the control functions should be enabled
            this._gmHelperService.deactivateControlFunctions$.subscribe(
                (bool) => this._deactivateControlFunctions = bool,
                (err) => console.error(err)
            )
        ]
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
     * private openInputWindow - if control is enabled it opens the input window,
     *                           or if open it closes it
     *                           if a marker was selected previously the marker
     *                           is unselected and restored to the original icon
     *
     * @param  {any} evt mouseevent with coords and point object
     */
    private openInputWindow(evt: any) {
        if (!this._deactivateControlFunctions) {
            if (this._markedForRemoval) {
                this._markedForRemoval.self.point.icn = null;
                this._routeManagerService.set(
                    this._markedForRemoval.self.point,
                    this._markedForRemoval.self.point.lat,
                    this._markedForRemoval.self.point.lng
                );
                this._markedForRemoval = null;
            }
            if (this._inputWindow) {
                this._inputWindow = null;
            } else {
                this._inputWindow = {
                    lat: evt.coords.lat,
                    lng: evt.coords.lng,
                    x: evt.coords.x,
                    y: evt.coords.y + (document.body.offsetHeight * 0.4)
                }
            }
        }
    }

    /**
     * private openInputWindow - if control is enabled it marks the marker for
     *                           removal and sets a new icon
     *                           if the marker was selected previously the marker
     *                           is deleted
     *
     * @param  {any} evt mouseevent with coords and point object
     */
    private remove(evt: any) {
        if (!this._deactivateControlFunctions) {
            if (this._markedForRemoval) {
                this._routeManagerService.removePoint(this._markedForRemoval);
                this._markedForRemoval = null;
            } else {
                evt.self.point.icn = 'TIMES';
                this._routeManagerService.set(
                    evt.self.point, evt.self.point.lat, evt.self.point.lng);
                this._markedForRemoval = evt;
            }
        }
    }

    /**
     * private addPoint - if control is enabled it marks the marker for
     *                           removal and sets a new icon
     *                           if the marker was selected previously the marker
     *                           is deleted
     *
     * @param  {string} evt mouseevent with coords and point object
     * @param  {number} evt mouseevent with coords and point object
     * @param  {number} evt mouseevent with coords and point object
     */
    private addPoint(field: string, lat: number, lng: number) {
        if (!this._deactivateControlFunctions) {
            this._inputWindow = null;
            this._routeManagerService.addPoint(field, lat, lng);
        }
    }

    private set(evt: any) {
        if (!this._deactivateControlFunctions) {
            this._routeManagerService.set(
                evt.self.point, evt.coords.lat, evt.coords.lng);
        }
    }

}
