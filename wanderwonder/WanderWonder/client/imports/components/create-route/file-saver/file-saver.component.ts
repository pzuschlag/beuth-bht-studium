import {Component, Input} from '@angular/core';
import {MaterializeDirective} from "angular2-materialize";
import {toast} from 'angular2-materialize';
import {DatabaseHelperService} from "../../../services/database-helper/database-helper.service";
import {RouteManagerService} from "../../../services/route-manager/route-manager.service";


@Component({
    selector: 'file-saver',
    templateUrl: 'client/imports/components/create-route/file-saver/file-saver.component.html',
    styleUrls: ['./styles/file-saver.component.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [MaterializeDirective]
})

export class FileSaverComponent {

    private _metadata: Object = {};
    private _gpx: boolean = true;

    constructor(
        private _routeManagerService: RouteManagerService,
        private _databaseHelperService: DatabaseHelperService) {
    }

    private saveRoute() {
        console.log("saveRoute()");
        this._metadata['time'] = new Date();
        let currentRoute = this._routeManagerService.currentRoute;
        currentRoute.name = this._metadata['name'];
        currentRoute.desc = this._metadata['desc'];
        currentRoute.author = this._metadata['author'];
        currentRoute.time = this._metadata['time'];
        if (this._gpx) {
            this._routeManagerService.saveFile(
                this._metadata, false,
                (data) => {
                    toast(data.error || `Route "${data.name}" erfolgreich in "Downloads" gespeichert`, 3000);
                    if (!data.error) {
                        $('#fileSaver').closeModal();
                    }
                }
            );
        } else {
            console.log("saving in database");
            this._databaseHelperService.addRoute(currentRoute, (err, res) => {
                toast(err || res, 3000);
                if (res) {
                    $('#fileSaver').closeModal();
                }
            });
            $('#fileSaver').closeModal();
        }
    }


}
