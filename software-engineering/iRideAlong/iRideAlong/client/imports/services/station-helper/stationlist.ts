import {Station} from './station';


/**
 * class StationList - keeps a map of all Station(s)
 */
export class StationList {

    private _map: Map<string, Station> = new Map<string, Station>();

    /**
     * constructor - StationList constructor
     *
     * @return {StationList}  StationList object
     */
    constructor() {
    }

    /**
     * getStation - get station by key
     *
     * @param  {string} key: the key of the map entry
     * @return {Station} the spec. Station
     */
    getStation(key: string): Station {
        return this._map.get(key);
    }

    /**
     * addStation - description
     *
     * @param  {Station} station: the station to add
     */
    addStation(station: Station) {
        this._map.set(station.id, station);
    }

    /**
     * setStatus - set active status
     *
     * @param  {string} key - the key of the entry to change
     * @param  {boolean} value - the new value of the active entry
     */
    setStatus(key: string, value: boolean) {
        let station = this.getStation(key);
        if (station) {
            this.getStation(key).active = value;
        }
    }

    getStatus(key: string) {
        let station = this.getStation(key);
        if (station) {
            return this.getStation(key).active;
        }
        return false;
    }

    /**
     * get map - get the map object
     *
     * @return {Map<string, Station>} the map object
     */
    get map(): Map<string, Station> {
        return this._map;
    }

    get size(): number {
        return this._map.size;
    }

}
