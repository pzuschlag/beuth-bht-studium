import {GlobalHelperService} from "../global-helper-service/global-helper-service";
import {GoogleMapsHelperService} from "../../components/util/google-maps/services";
import {Injectable, provide} from '@angular/core';
import {Meteor} from 'meteor/meteor';
import {Observable, Subject, BehaviorSubject} from 'rxjs';
import {Station} from "../station-helper/station";
import {StationList} from "../station-helper/stationlist";

declare var geofence: any;
declare var backgroundGeolocation;

// 300000 = 5mins
const INTERVAL: number = 300000;

var openedFromNotification;

// has to be called here => the notification event is called at the very start
// register listener for app opened through notification
if (Meteor.isCordova) {
    console.log("registering CordovaGeofenceService notification click event");
    geofence.onNotificationClicked =
        (notificationData) =>
            openedFromNotification = notificationData;
}

@Injectable()
export class CordovaGeofenceService {

    private _watchedGeofencesObj: {};
    private _watchedGeofencesArr: Object[];

    private _intervalID: number;
    private _lastTimeChecked: number;

    private _openedFromNotification: Subject<Object> = new Subject<Object>();
    private _lastCoord: BehaviorSubject<Object> = new BehaviorSubject<Object>(null);
    private _transitionedGeofences: Subject<Object[]> = new Subject<Object[]>();
    private _closestStation: Subject<Station> = new Subject<Station>();

    constructor(
        private _gmHelper: GoogleMapsHelperService,
        private _globalHelper: GlobalHelperService
    ) {
    }

    /**
     * init - initialize the geofence, update the watched geofences
     *
     * @return {type}
     */
    public init(): void {
        if (Meteor.isCordova && geofence) {
            geofence.initialize().then(
                (d) => console.log("Successful geofence initialization"),
                (error) => console.error("Error geofence", error)
            );
            // trigger openedFromNotification subject
            this.openedFromNotification = openedFromNotification;
            // this.listenForTransitions();
            // this.updateWatchedGeofences();
        }
    }

    /**
     * addOrUpdateFence - add or update the fence with the given id
     *
     * @param {string} id: id of geofence
     * @param {string} name: name of geofence
     * @param {number} lat: lat of geofence
     * @param {number} lng: lng of geofence
     * @param {number} radius: radius of geofence
     * @param {function} callback?: optional callback
     *
     * @return {void}
     */
    public addOrUpdateFence(id: string, name: string, lat: number, lng: number, radius: number, callback?: any): void {
        if (Meteor.isCordova && geofence) {
            // https://github.com/cowbell/cordova-plugin-geofence
            geofence.addOrUpdate({
                id: id, //A unique identifier of geofence
                latitude: lat, //Geo latitude of geofence
                longitude: lng, //Geo longitude of geofence
                radius: radius, //Radius of geofence in meters
                transitionType: 1, //Type of transition 1 - Enter, 2 - Exit, 3 - Both
                notification: {         //Notification object
                    title: name, //Title of notification
                    // smallIcon: "", // Small icon showed in notification area, only res URI
                    // icon: "", // TODO add icons,
                    text: `Welcome at ${name}`, //Text of notification
                    openAppOnClick: true,//is main app activity should be opened after clicking on notification
                    vibration: [500], //Optional vibration pattern - see description
                    data: { id: id, name: name } //Custom object associated with notification
                }
            }).then(
                callback ?
                    callback : (geo) => null,
                callback ?
                    callback : (reason) =>
                        console.error('Adding geofence failed', reason)
                );
            // console.log(`Geofence ${name} successfully added, ${geo}`)
        }
    }


    /**
     * refreshGeofences - if lat lng are given, this calculate the distances to the stations
     *                    and extract the nearest stations (20 ios or 100 android)
     *                    trigger closestStation Subject
     *
     * @param {StationList} stations: id to check
     * @param {number} lat: id to check
     * @param {number} lng: id to check
     *
     * @return {void}
     */
    public refreshGeofences(stations: StationList, lat: number, lng: number): void {
        if (Meteor.isCordova && geofence) {
            let distanceMoved = this.getDistanceFromLatLonInKm(
                lat, lng, this.lastCoord ? this.lastCoord['lat'] : null, this.lastCoord ? this.lastCoord['lng'] : null
            );
            let timeout = this._lastTimeChecked ?
                (this._lastTimeChecked + (INTERVAL / 2)) > new Date().getTime() : false;
            if (!openedFromNotification && lat && lng && !timeout
                && (!distanceMoved || distanceMoved * 1000 >= 100)) {
                // TODO remove and add only necessary stations
                geofence.removeAll().then(
                    () => {
                        let distances = [];
                        stations.map.forEach((station, key, map) => {
                            station.dist = this.getDistanceFromLatLonInKm(lat, lng, station.lat, station.lng) * 1000;
                            distances.push(station);
                        });
                        distances.sort((a, b) => a.dist - b.dist);
                        // 100 allowed in android - in ios 20
                        let closestFew = distances.slice(0, this._globalHelper.isANDROID ? 100 : 20);
                        let fences = [];
                        for (let station of closestFew) {
                            fences.push({
                                id: station.id, //A unique identifier of geofence
                                latitude: station.lat, //Geo latitude of geofence
                                longitude: station.lng, //Geo longitude of geofence
                                radius: station.radius, //Radius of geofence in meters
                                transitionType: 1, //Type of transition 1 - Enter, 2 - Exit, 3 - Both
                                notification: {         //Notification object
                                    title: station.name, //Title of notification
                                    // smallIcon: 'res://icon', // Small icon showed in notification area, only res URI
                                    icon: 'res://notification_large', // TODO add icons,
                                    text: `Welcome at ${station.name}`, //Text of notification
                                    openAppOnClick: true,//is main app activity should be opened after clicking on notification
                                    vibration: [500], //Optional vibration pattern - see description
                                    data: { id: station.id, name: station.name } //Custom object associated with notification
                                }
                            });
                        }
                        if (fences.length > 0) {
                            geofence.addOrUpdate(fences, (res) => {
                                this.closestStation = closestFew[0];
                                this.lastCoord = { lat: lat, lng: lng };
                                // TODO info for check already set geofences..
                                // this.updateWatchedGeofences();
                                this._lastTimeChecked = new Date().getTime();
                                console.log(`Geofences updated`);
                            }, (err) => console.error(err));
                        }
                    },
                    (reason) => {
                        this._lastTimeChecked = new Date().getTime();
                        this.lastCoord = { lat: lat, lng: lng };
                        console.error('Removing geofences failed', reason);
                    }
                );
            } else {
                if (openedFromNotification) {
                    this._gmHelper.refreshCurrentLatLng();
                    openedFromNotification = null;
                }
                console.log(timeout ? `Geofences not updated, timeout` : `Geofences needed no update, only ~${Math.round(distanceMoved * 1000)} meter moved`);
            }
        }
    }


    /**
     * listenForTransitions - listen for geofence transitions
     *
     * @return {type}
     */
    public listenForTransitions() {
        if (Meteor.isCordova && geofence) {
            geofence.onTransitionReceived = (geofences) =>
                this._transitionedGeofences = geofences
        }
    }


    /**
     * updateWatchedGeofences - get all registered geofences and
     *                          map reduce them to object
     *
     * @return {void}
     */
    public updateWatchedGeofences() {
        if (Meteor.isCordova && geofence) {
            geofence.getWatched().then((geofencesJson) => {
                this._watchedGeofencesArr = JSON.parse(geofencesJson);
                this._watchedGeofencesObj = this._watchedGeofencesArr.reduce(
                    (o, v, i, a) => (o[a[i]['id']] = v, o), {}
                );
            });
        }
    }

    /**
     * destroyAll - destroy all registered geofences
     *
     * @return {void}
     */
    public destroyAll(): Promise<any> {
        if (Meteor.isCordova && geofence) {
            return geofence.removeAll();
        }
        return null;
    }

    /**
     * isSubscribedTo - check if id is registered geofence
     *
     * @param {string} id: id to check
     * @return {boolean}: if id is already registered
     */
    public isSubscribedTo(id: string): boolean {
        if (Meteor.isCordova && geofence) {
            return Boolean(this._watchedGeofencesObj[id]);
        }
        return null;
    }



    /**
     * isAuthorized - check wether the user is authorized (in proximity) to the
     *                clicked station, calculate distance..
     *
     * @param  {Station} station: the station to check
     * @param  {function} callback: function
     * @param  {Object} opt?: optional passed parameters
     * @return {type}                  description
     */
    public isAuthorized(station: Station, callback, allowedDistSet?: number) {
        let sub = this._gmHelper.getCoordinates().subscribe(
            (pos) => {
                let allowedDist = allowedDistSet || station.radius;
                let lat = pos.coords.latitude;
                let lng = pos.coords.longitude;
                let distFromStationInMeter =
                    this.getDistanceFromLatLonInKm(
                        station.lat, station.lng, lat, lng
                    ) * 1000;
                callback({
                    dist: Math.round(distFromStationInMeter - allowedDist) || 1,
                    authorized: distFromStationInMeter <= allowedDist
                });
                this.lastCoord = { lat: lat, lng: lng };
                sub.unsubscribe();
            },
            (err) => { callback(err); sub.unsubscribe() }
        )
    }


    /**
     * setupMonitoring - refresh the geofences, set up interval polling and refresh
     *                   of geofences
     *                   uses interval if app in foreground or backgroundGeolocation
     *                   plugin vice versa
     *
     * @param  {StationList} stations: the stations loaded from JSON
     * @param  {boolean} bg: is in background?
     * @param  {number} lat?: optional lat
     * @param  {number} lng?: optional lng
     * @return {void}
     */
    public setupMonitoring(stations: StationList, bg: boolean, lat?: number, lng?: number) {
        if (Meteor.isCordova && geofence && backgroundGeolocation) {
            if (lat && lng) {
                this.refreshGeofences(stations, lat, lng);
            }

            if (bg) {
                if (this._intervalID) {
                    clearInterval(this._intervalID);
                }
                // https://github.com/mauron85/cordova-plugin-background-geolocation
                backgroundGeolocation.configure((pos) => {
                    console.log(pos);
                    this.refreshGeofences(stations, pos.latitude, pos.longitude);
                    backgroundGeolocation.finish();
                }, (err) => console.error(err)
                    , {
                        desiredAccuracy: 10, // Desired accuracy in meters. Possible values [0, 10, 100, 1000]. The lower the number, the more power devoted to GeoLocation resulting in higher accuracy readings. 1000 results in lowest power drain and least accurate readings.
                        stationaryRadius: 40, // Stationary radius in meters. When stopped, the minimum distance the device must move beyond the stationary location for aggressive background-tracking to engage.
                        distanceFilter: 40, // The minimum distance (measured in meters) a device must move horizontally before an update event is generated.
                        notificationTitle: "iRideAlong",
                        notificationText: "Background tasks running",
                        // notificationIconColor: "", TODO add icons
                        notificationIconLarge: 'notification_large',
                        // notificationIconSmall: 'notification_large',
                        interval: INTERVAL, // poll for position every n miliseconds
                        fastestInterval: INTERVAL * 1.5,
                        debug: false, // enable this hear sounds for background-geolocation life-cycle.
                        stopOnTerminate: true, // enable this to clear background location settings when the app terminates
                    }
                );
                backgroundGeolocation.start();
            } else {
                backgroundGeolocation.stop();
                this._intervalID = setInterval(() => {
                    let sub = this._gmHelper.getCoordinates().subscribe(
                        (pos) => {
                            let lat = pos.coords.latitude;
                            let lng = pos.coords.longitude;
                            this.refreshGeofences(stations, lat, lng);
                            this._gmHelper.refreshCurrentLatLng(lat, lng, true);
                        },
                        (err) => console.error(err),
                        () => sub.unsubscribe()
                    )
                }, INTERVAL);
            }
        }
    }


    /**
     * stopMonitoring - stop the background monitoring task and
     *                  remove event listener
     *
     * @return {void}
     */
    public stopMonitoring() {
        if (Meteor.isCordova && geofence) {
            backgroundGeolocation.stop();
            clearInterval(this._intervalID);
        }
    }


    /**
     * getDistanceFromLatLonInKm - get the distance between two points HAVERSINE formula
     *
     * @param  {number} number lat1 first coord lat
     * @param  {number} number lng1 first coord lng
     * @param  {number} number lng2 second coord lat
     * @param  {number} number lng2 second coord lng
     *
     * @return {number} distance in kilometer
     */
    private getDistanceFromLatLonInKm(lat1: number, lng1: number, lat2: number, lng2: number): number {
        if (!lat1 || !lng1 || !lat2 || !lng2) {
            return null;
        }
        var deg2Rad = deg => {
            return deg * Math.PI / 180;
        }

        var r = 6371; // Radius of the earth in km
        var dLat = deg2Rad(lat2 - lat1);
        var dLon = deg2Rad(lng2 - lng1);
        var a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(deg2Rad(lat1)) * Math.cos(deg2Rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return r * c;
    }

    /**
     * set transitionedGeofences - set next transitionedGeofences Subject value
     *
     * @param  {Object} value Object with values of transitionedGeofences
     */
    set transitionedGeofences(value: Object[]) {
        this._transitionedGeofences.next(value);
    }


    /** get transitionedGeofences$ - get the Observable of the transitionedGeofences subject
     *
     *  @return {Observable<Object>} the Observable of the transitionedGeofences subject
     */
    get transitionedGeofences$(): Observable<Object[]> {
        return this._transitionedGeofences.asObservable();
    }

    /**
     * set closestStation - set next closestStation Subject value
     *
     * @param  {Object} value Object with values of closestStation
     */
    set closestStation(value: Station) {
        this._closestStation.next(value);
    }


    /** get closestStation$ - get the Observable of the closestStation subject
     *
     *  @return {Observable<Object>} the Observable of the closestStation subject
     */
    get closestStation$(): Observable<Station> {
        return this._closestStation.asObservable();
    }

    /** get openedFromNotification$ - get the Observable of the openedFromNotification subject
     *
     *  @return {Observable<Object>} the Observable of the openedFromNotification subject
     */
    get openedFromNotification$(): Observable<Object> {
        return this._openedFromNotification.asObservable();
    }

    /**
     * set openedFromNotification - set next openedFromNotification Subject value
     *
     * @param  {Object} value Object with values of openedFromNotification
     */
    set openedFromNotification(value: Object) {
        this._openedFromNotification.next(value);
        this._openedFromNotification.complete();
    }

    /** get lastCoord$ - get the Observable of the lastCoord subject
     *
     *  @return {Observable<Object>} the Observable of the lastCoord subject
     */
    get lastCoord$(): Observable<Object> {
        return this._lastCoord.asObservable();
    }

    /** get openedFromNotification - get the value of the openedFromNotification subject
     *
     *  @return {Object} the value of the openedFromNotification subject
     */
    get lastCoord(): Object {
        return this._lastCoord.getValue();
    }

    /**
     * set lastCoord - set next lastCoord Subject value
     *
     * @param  {Object} value Object with values of lastCoord
     */
    set lastCoord(value: Object) {
        this._lastCoord.next(value);
    }
}
