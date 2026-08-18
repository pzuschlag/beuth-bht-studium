import {Injectable, OnInit, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subject, Observable, Subscription} from "rxjs";
import {Station} from './station';
import {StationList} from './stationlist';

declare var require: any;

@Injectable()
export class StationHelperService {

    private _stationList: StationList = new StationList();
    private _stations: BehaviorSubject<StationList> = new BehaviorSubject<StationList>(null);
    private _unsubscribedStations: Subject<string[]> = new Subject<string[]>();

    /**
     * constructor - StationHelperService constructor, get the stations from a JSON file
     *
     * @return {StationHelperService}  StationHelperService object
     */
    constructor() {
        this.stations = require('./../../../../public/stations/stops_berlin.json');
    }

    /**
     * set stations - convert object to Station and add to stationlist
     *
     * @param {Object} value - with entries: {id:{ name, lat, lng, active }}
     */
    set stations(value: Object) {
        for (let key in value) {
            let station = value[key];
            station['id'] = key;
            this._stationList.addStation(new Station(station));
        }
        this._stations.next(this._stationList);
    }

    /**
     * get lat - get the stations$ Observable
     *
     * @return {Observable<StationList>} the stations$
     */
    get stations$(): Observable<StationList> {
        return this._stations.asObservable();
    }

    /**
     * get lat - get the unsubscribedStations$ Observable
     *
     * @return {Observable<StationList>} the unsubscribedStations$
     */
    get unsubscribedStations$(): Observable<string[]> {
        return this._unsubscribedStations.asObservable();
    }

    /**
     * set status - set the status of the stationlist and trigger refresh of
     *              stations subject
     *
     * @param  {Object} values - the values to set
     */
    set status(values: {}) {
        this.setStatus(values);
    }

    set initialStatus(values: {}) {
        // console.log('initialStatus', values);
        this.setStatus(values, true);
    }

    private setStatus(values: {}, initial?: boolean) {
        let unsubscribedStations: string[] = [];
        for (let key in values) {
            let sVal = values[key] === "1" ? true : false;
            if (!initial || (initial && sVal)) {
                this._stationList.setStatus(key, sVal);
                if (!sVal) {
                    unsubscribedStations.push(key);
                }
            }
        }
        this._stations.next(this._stationList);
        if (!initial && unsubscribedStations.length > 0) {
            this._unsubscribedStations.next(unsubscribedStations);
        }
    }

    public getStation(id: string): Station {
        return this._stationList.getStation(id);
    }

}
