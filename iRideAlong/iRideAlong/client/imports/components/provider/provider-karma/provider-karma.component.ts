import {GlobalHelperService, Toasts} from "../../../services/global-helper-service/global-helper-service";
import {GoogleMapsHelperService} from "../../util/google-maps/services";
import {JsonHandlerService} from "../../../services/json-handler/json-handler.service";
import {MaterializeDirective} from "angular2-materialize";
import {Component, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';

@Component({
    selector: 'provider-karma',
    templateUrl: 'client/imports/components/provider/provider-karma/provider-karma.component.html', // OR html in file:
    styleUrls: ['./styles/provider-karma.component.min.css'],
    directives: [MaterializeDirective]
})
export class ProviderKarmaComponent implements OnDestroy {

    private _karma: number;
    private _sub: Subscription;

    private _color: string;

    private rotate: boolean;

    constructor(
        private _jsonHandler: JsonHandlerService,
        private _gmHelper: GoogleMapsHelperService,
        private _globHelper: GlobalHelperService
    ) {
        // keep karma points up to date
        this._sub = this._jsonHandler.karmaPoints$.subscribe(
            (points) => {
                this.animate();
                setTimeout(() => {
                    if (points >= 50) {
                        this.color = "#FFD700";
                    } else if (points >= 25) {
                        this.color = "#c0c0c0";
                    } else if (points === 0) {
                        this.color = "#e0e0e0";
                    } else {
                        this.color = this.getRandomColor();
                    }
                    this._karma = points;
                }, 500);
                setTimeout(() => this.animate(), 1000);
            }
        );
    }

    private getRandomColor(): string {
        var letters = '0123456789ABCDEF'.split('');
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    /**
     * ngOnDestroy - unsubscribes from the observables stored in sub
     */
    ngOnDestroy() {
        this._sub.unsubscribe();
    }

    get color() {
        return this._color;
    }

    set color(color: string) {
        this._color = color;
    }

    private animate() {
        this.rotate = !this.rotate;
    }

    private centerMap() {
        this._globHelper.loadingMessage = 'determining your location';
        this._gmHelper.refreshCurrentLatLng(null, null, true, (val: boolean) => {
            this._globHelper.loadingMessage = null;
            if (!val) {
                Toasts.locationError();
            }
        });
    }

}
