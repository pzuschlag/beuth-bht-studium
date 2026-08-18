import {RecordRouteComponent} from "../record-route/record-route.component";
import {CordovaCameraService} from "../../../services/cordova-camera-service/cordova-camera.service";
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {CordovaGeolocationService} from "../../../services/cordova-geolocation/cordova-geolocation.service";
import {Component} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router';
import {GoogleMapsWrapperComponent} from "../../util/google-maps/components";
import {SearchComponent} from "../../util/search/search.component";
import {StartUserComponent} from "../start-user/start-user.component";
import {Subscription} from 'rxjs';
import {toast} from "angular2-materialize";

declare var $: JQueryStatic;

@Component({
    selector: 'start-main',
    templateUrl: 'client/imports/components/start/start-main/start-main.component.html', // OR html in file:
    styleUrls: ['./styles/start-main.component.min.css'],
    directives: [
        ROUTER_DIRECTIVES,
        GoogleMapsWrapperComponent,
        SearchComponent,
        StartUserComponent,
        RecordRouteComponent
    ],
    providers: []

})
export class StartMainComponent {

    constructor(
        private _cordGeolocationService: CordovaGeolocationService,
        private _gmHelperService: GoogleMapsHelperService,
        private _cordCameraService: CordovaCameraService) {
        this._gmHelperService.deactivateControlFunctions = true;
    }

    public takePhoto(): void {
        this._cordCameraService.takePhoto();
    }

}
