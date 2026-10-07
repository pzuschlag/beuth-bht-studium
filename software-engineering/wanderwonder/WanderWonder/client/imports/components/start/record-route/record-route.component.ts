import {RouteManagerService} from "../../../services/route-manager/route-manager.service";
import {Component, Input} from '@angular/core';
import {MaterializeDirective} from "angular2-materialize";
import {toast} from 'angular2-materialize';

@Component({
    selector: 'record-route',
    templateUrl: 'client/imports/components/start/record-route/record-route.component.html',
    styleUrls: ['./styles/record-route.component.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [MaterializeDirective]
})
export class RecordRouteComponent {

    private _recording: boolean = false;
    private _interval: number = 30;

    constructor(private _routeManagerService: RouteManagerService) {
    }

    private startTracking() {
        this._recording = true;
        this._routeManagerService.startTracking(this._interval || 30);
    }

    private stopTracking() {
        this._recording = false;
        this._routeManagerService.stopTracking();
    }

}
