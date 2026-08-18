import {Component, Input} from '@angular/core';
import {MaterializeDirective} from "angular2-materialize";
import {Control, FORM_DIRECTIVES} from '@angular/common';
import {toast} from 'angular2-materialize';
import {DatabaseHelperService} from "../../../services/database-helper/database-helper.service";
import {RouteManagerService} from "../../../services/route-manager/route-manager.service";
import {geo} from '../../../../classes/geo';

declare var $: any;

@Component({
    selector: 'file-loader',
    templateUrl: 'client/imports/components/create-route/file-loader/file-loader.component.html',
    styleUrls: ['./styles/file-loader.component.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [MaterializeDirective, FORM_DIRECTIVES]
})
export class FileLoaderComponent {

    private _search: Control = new Control();

    private _results: Object[];

    constructor(
        private _routeManagerService: RouteManagerService,
        private _databaseHelperService: DatabaseHelperService) {
        this._search
            .valueChanges
            .debounceTime(400)
            .distinctUntilChanged()
            .flatMap(term => this._databaseHelperService.findRoute(term))
            .subscribe(
            data => this._results = data,
            error => console.error(error)
            );
    }

    private loadFile() {
        this._routeManagerService.loadRoute(
            (error) => {
                toast(error || 'Route erfolgreich geladen', 3000);
                $('#fileLoader').closeModal();
            });
    }

    private setRoute(i: number) {
        this._routeManagerService.loadRoute(
            (error) => {
                toast(error || 'Route erfolgreich geladen', 3000);
                $('#fileLoader').closeModal();
            },
            this._results[i]);
    }
}
