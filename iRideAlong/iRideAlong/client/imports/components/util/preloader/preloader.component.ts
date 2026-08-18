import {GlobalHelperService} from "../../../services/global-helper-service/global-helper-service";
import {Component, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';

@Component({
    selector: 'preloader',
    templateUrl: 'client/imports/components/util/preloader/preloader.component.html', // OR html in file:
    styleUrls: ['./styles/preloader.component.min.css'],
    host: {
        '[class.active-wrapper]': '_messages',
    }
})
export class PreloaderComponent implements OnDestroy {

    private _messages: string[];
    private _sub: Subscription;

    constructor(
        private _globHelper: GlobalHelperService
    ) {
        this._sub = this._globHelper.loadingMessage$.subscribe(
            (msg: string | string[]) => {
                if (!msg) {
                    this._messages = null;
                } else if (msg instanceof Array) {
                    this._messages = msg;
                } else {
                    this._messages = [msg];
                }
            }
        );
    }

    ngOnDestroy() {
        this._sub.unsubscribe();
    }
}
