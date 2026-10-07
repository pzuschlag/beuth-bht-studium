import {DatabaseHelperService} from "../../../services/database-helper/database-helper.service";
import {RouteManagerService} from "../../../services/route-manager/route-manager.service";
import {Component, Input} from '@angular/core';
import {Control, FORM_DIRECTIVES} from '@angular/common';
import {toast} from 'angular2-materialize';

@Component({
    selector: 'search',
    templateUrl: 'client/imports/components/util/search/search.component.html',
    styleUrls: ['./styles/search.component.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [FORM_DIRECTIVES],
})
export class SearchComponent {

    private _results: Object[];
    private _search: Control = new Control();

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

    private setRoute(i: number) {
        this._routeManagerService.loadRoute(
            (error) => {
                toast(error || 'Route erfolgreich geladen', 3000);
                this._search.updateValue('');
            },
            this._results[i]);
    }
}
