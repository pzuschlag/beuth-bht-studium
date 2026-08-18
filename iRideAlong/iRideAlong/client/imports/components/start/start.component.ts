import {GlobalHelperService} from "../../services/global-helper-service/global-helper-service";
import {ModalHelperService} from "../../services/modal-helper-service/modal-helper.service";
import {Toasts} from "../../services/global-helper-service/global-helper-service";
import {CordovaGeolocationService} from "../../services/cordova-geolocation/cordova-geolocation.service";
import {StationHelperService} from "../../services/station-helper/station-helper.service";
import {TYPE, JsonHandlerService} from "../../services/json-handler/json-handler.service";
import {Component, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {ROUTER_DIRECTIVES} from '@angular/router';

@Component({
    selector: 'start',
    templateUrl: 'client/imports/components/start/start.component.html', // OR html in file:
    styleUrls: ['./styles/start.component.min.css'],
    directives: [ROUTER_DIRECTIVES]
})
export class StartComponent implements OnDestroy {

    private _loading: boolean = true;
    private _sub: any;

    constructor(
        private _router: Router,
        private _jsonHandler: JsonHandlerService,
        private _locationHelper: CordovaGeolocationService,
        private _modalHelper: ModalHelperService,
        private _globalHelper: GlobalHelperService
    ) {
        this._globalHelper.loadingMessage = ['initializing', 'please be patient'];
        this._sub = this._jsonHandler.type$.subscribe(
            (type: TYPE) => {
                this._loading = Boolean(TYPE[type]);
                if (this._loading) {
                    this._router.navigate([`/${TYPE[type]}`]);
                } else {
                    // trigger permission request
                    let sub = this._locationHelper.getCoordinates().subscribe(
                        (res) => null,
                        (err) => Toasts.locationError(),
                        () => sub.unsubscribe()
                    );
                    this._globalHelper.loadingMessage = null;
                }
            }
        );
    }

    ngOnInit() {
        document.addEventListener("backbutton",
            () => this._modalHelper.closeAll(), false);
    }

    ngOnDestroy() {
        this._sub.unsubscribe();
    }
}
