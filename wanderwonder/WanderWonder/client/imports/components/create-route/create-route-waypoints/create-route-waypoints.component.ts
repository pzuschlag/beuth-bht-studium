import {TrimDecimalsPipe} from "../../../pipes/trim-decimals/trim-decimals.pipe";
import {RouteManagerService} from "../../../services/route-manager/route-manager.service";
import {Component, Input, OnInit, OnDestroy} from '@angular/core';
import {MaterializeDirective} from 'angular2-materialize';
import {GoogleMapsHelperService} from "../../util/google-maps/services/google-maps-helper/google-maps-helper.service";
import {Subscription} from 'rxjs';

@Component({
    selector: 'create-route-waypoints',
    templateUrl: 'client/imports/components/create-route/create-route-waypoints/create-route-waypoints.component.html', // OR html in file:
    styleUrls: ['./styles/create-route-waypoints.component.min.css'],
    directives: [MaterializeDirective],
    pipes: [TrimDecimalsPipe]
})
export class CreateRouteWaypointsComponent implements OnInit, OnDestroy {

    private route: any;
    private _subs: Subscription[] = [];

    constructor(private _routeManagerService: RouteManagerService) {
        this._subs = [this._routeManagerService.currentRoute$.subscribe(
            (route) => {
                this.route = route;
            },
            (err) => console.error(err)
        )];
    }

    ngOnInit() {
    }

    ngOnDestroy() {
        for (let sub of this._subs) {
            sub.unsubscribe();
        }
    }
}
