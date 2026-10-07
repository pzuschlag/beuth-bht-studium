import * as redis from 'redis';
import {CLIENTOPT, TYPE} from '../constants';

export class DBHelper {

    private _dbHelper: redis.RedisClient;
    private _prevSubs: string[];

    constructor() {
        this._dbHelper = redis.createClient(CLIENTOPT);
        (<any>this._dbHelper).on("connect", () => console.log("RedisClient \"dbHelper\” connected"));
        (<any>this._dbHelper).on("error", (err) => console.error("RedisClient \"dbHelper\” err: " + err));
    }

    public updateStatus(subscriptions, callback) {
        let newSubs = this.activeStations(subscriptions);
        let subs = {};
        if (this._prevSubs) {
            for (let sub of this._prevSubs) {
                if (!this.isInArray(sub, newSubs)) {
                    this.setActive(sub, false);
                    subs[sub] = "0";
                }
            }
        }
        for (let sub of newSubs) {
            if (!this._prevSubs || !this.isInArray(sub, this._prevSubs)) {
                this.setActive(sub, true);
                subs[sub] = "1";
            }
        }
        if (!this.isEmpty(subs)) {
            callback(subs);
        }
        this._prevSubs = newSubs;
    }

    private activeStations(obj: {}): string[] {
        // TODO is there a more efficient way?
        let activeStations: string[] = [];
        for (let key in obj) {
            let arr: any[] = obj[key];
            for (let i = 0; i < arr.length; i++) {
                if (TYPE[arr[i].type] === TYPE[1] && activeStations.indexOf(key) === -1) {
                    activeStations.push(key);
                }
            }
        }
        return activeStations;
    }

    private isEmpty(obj: {}) {
        for (var x in obj) { return false; }
        return true;
    }

    private isInArray(value, array) {
        return array.indexOf(value) > -1;
    }

    private set(key: string, field: string, value: any) {
        this._dbHelper.hset(key, field, value);
    }

    public setActive(key: string, val: boolean) {
        let sVal = val ? "1" : "0";
        this.set(key, 'active', sVal);
    }

    public getAllStationStatus(callback) {
        let stations = {};
        this._dbHelper.keys("STAT_*", (err, res) => {
            if (err) {
                callback(err);
            } else {
                let stationcount = res.length;
                for (let i = 0; i < stationcount; i++) {
                    let station = res[i];
                    this._dbHelper.hget(station, 'active', (err, obj) => {
                        if (err) {
                            callback(err);
                        } else {
                            stations[station] = obj;
                            if ((i + 1) >= stationcount) {
                                callback(null, stations);
                            }
                        }
                    });
                }
            }
        });
    }

    public getAll(callback) {
        let stations = {};
        this._dbHelper.keys("STAT_*", (err, res) => {
            if (err) {
                callback(err);
            } else {
                let stationcount = res.length;
                for (let i = 0; i < stationcount; i++) {
                    let station = res[i];
                    this._dbHelper.hgetall(station, (err, obj) => {
                        if (err) {
                            callback(err);
                        } else {
                            stations[station] = obj;
                            if ((i + 1) >= stationcount) {
                                callback(null, stations);
                            }
                        }
                    });
                }
            }
        })
    }
}
