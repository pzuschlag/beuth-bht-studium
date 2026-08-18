import {Component, Inject, forwardRef} from '@angular/core';
import {BehaviorSubject, Subject, Observable, Subscription} from "rxjs";
import {ROUTER_DIRECTIVES} from '@angular/router';
import {FileSaverComponent} from "../file-saver/file-saver.component";
import {FileLoaderComponent} from "../file-loader/file-loader.component";
import {RouteManagerService} from "../../../services/route-manager/route-manager.service";
import {CreateRouteInputComponent} from "../create-route-input/create-route-input.component";
import {CreateRouteWaypointsComponent} from "../create-route-waypoints/create-route-waypoints.component";
import {GoogleMapsWrapperComponent} from "../../util/google-maps/components";
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {toast} from 'angular2-materialize';

@Component({
    selector: 'create-route-main',
    templateUrl: 'client/imports/components/create-route/create-route-main/create-route-main.component.html', // OR html in file:
    styleUrls: ['./styles/create-route-main.component.min.css'],
    directives: [
        ROUTER_DIRECTIVES,
        CreateRouteInputComponent,
        CreateRouteWaypointsComponent,
        GoogleMapsWrapperComponent,
        FileSaverComponent,
        FileLoaderComponent
    ]
})

export class CreateRouteMainComponent {

    private mapView: boolean = true;

    constructor(private _gmHelperService: GoogleMapsHelperService, private _routeManagerService: RouteManagerService) {
        this._gmHelperService.deactivateControlFunctions = false;
    }

    private switchView() {
        this.mapView = !this.mapView;
    }

}
