import {CordovaGeolocationService} from "../../../services/cordova-geolocation/cordova-geolocation.service";
import {Component, Output, EventEmitter} from '@angular/core';
import {Control, FORM_DIRECTIVES} from '@angular/common';
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {toast, updateTextFields} from 'angular2-materialize';
import {RouteManagerService} from "../../../services/route-manager/route-manager.service";

declare var $: any;

@Component({
    selector: 'create-route-input',
    templateUrl: 'client/imports/components/create-route/create-route-input/create-route-input.component.html', // OR html in file:
    styleUrls: ['./styles/create-route-input.component.min.css'],
    directives: [
        FORM_DIRECTIVES
    ]

})
export class CreateRouteInputComponent {

    private location: Object;
    private address_info: Object;
    private address_complete: Object;
    private reg: RegExp = /\-?(90|[0-8]?[0-9]\.[0-9]{0,20})([\, ])*\-?(180|(1[0-7][0-9]|[0-9]{0,2})\.[0-9]{0,20})*/;
    private outputs: Object;

    constructor(
        private _gmHelperService: GoogleMapsHelperService,
        private _routeManagerService: RouteManagerService) {

        let ipts: Array<string> = ['start', 'end', 'waypoint'];

        this.location = {
            start: new Control(),
            end: new Control(),
            waypoint: new Control()
        };
        this.address_info = {};
        this.address_complete = {};

        for (let key in this.location) {
            this.setupSearch(key);
        }
    }

    private setupSearch(field: string) {
        this.location[field]
            .valueChanges
            .debounceTime(400)
            .distinctUntilChanged()
            .flatMap(term => this._gmHelperService.getGeocode(term))
            .subscribe(
            data => this.checkData(data, field),
            error => console.error(error)
            );
    }

    private isLatLng(str: string) {
        return this.reg.test(str);
    }

    private checkData(data: Object, field: string) {
        let lat = data['lat'];
        let lng = data['lng'];
        if (lat && lng && data['formattedAddress']) {
            this.address_info[field] = this.isLatLng(this.location[field].value) ?
                data['formattedAddress'] : `lat ${lat} lng ${lng}`;
            this.address_complete[field] = data;
        } else if (lat && lng) {
            this.address_info[field] = `Marker | Lat ${lat} | Lng ${lng}`;
            this.address_complete[field] = data;
        } else {
            this.address_complete[field] = null;
            this.address_info[field] = 'kein Ergebnis';
        }
    }

    private getLocation(field: string) {
        this.address_info[field] = 'ermittele Standort..';
        let sub0 = this._gmHelperService.getCoordinates().subscribe(
            position => {
                let latlng = `${position.coords.latitude}, ${position.coords.longitude}`;
                this.location[field].updateValue(latlng);
                updateTextFields();
                let sub = this._gmHelperService.getGeocode(latlng).subscribe(
                    data => this.checkData(data, field),
                    error => {
                        console.error(error);
                        this.checkData({}, field)
                    },
                    () => sub.unsubscribe()
                );
            },
            error => toast(error),
            () => sub0.unsubscribe()
        );
    }

    private _set(field: string) {
        this._routeManagerService.addPoint(field, this.address_complete[field].lat, this.address_complete[field].lng);
        this.address_complete[field] = null;
        this.location[field].updateValue(null);
        this.address_info[field] = '';
        updateTextFields();
    }
}
