import {Injectable, OnInit} from '@angular/core';
import {Observable, BehaviorSubject, Subscription} from 'rxjs';
import {geo} from '../../../classes/geo';
import {GoogleMapsHelperService} from "../../components/util/google-maps/services";
import {CordovaGeolocationService} from "../cordova-geolocation/cordova-geolocation.service";
import {CordovaFilehandlerService} from "../cordova-filehandler/cordova-filehandler.service";
import {CordovaCameraService} from "../cordova-camera-service/cordova-camera.service";
import * as tj from 'togeojson';
import * as xmlDOM from 'xmldom';


const WAYPOINT = 'waypoint';
const START = 'start';
const END = 'end';

@Injectable()
export class RouteManagerService {

    private _routes: geo.Route[] = [];

    private _currentRoute: BehaviorSubject<geo.Route> = new BehaviorSubject<geo.Route>(new geo.Route());
    private _currentRoute$: Observable<geo.Route> = this._currentRoute.asObservable();

    private _trackingId: any;
    private _camera: Subscription;

    constructor(
        private _geolocationService: CordovaGeolocationService,
        private _gmHelperService: GoogleMapsHelperService,
        private _filehandlerService: CordovaFilehandlerService,
        private _cameraService: CordovaCameraService) {
        // subscribe to camera and save img to route
        this._cameraService.currentPhoto$.subscribe(
            (data) => {
                console.log("photo saved: ", data);
                this.addPoint(WAYPOINT, data['lat'], data['lng'], data['img']);
            },
            (err) => console.error(err)
        );
    }

    public get currentRoute(): geo.Route {
        return this._currentRoute.getValue();
    }

    public get currentRoute$(): Observable<geo.Route> {
        return this._currentRoute$;
    }

    public set currentRoute(value: geo.Route) {
        this._currentRoute.next(value);
        this._gmHelperService.zoomToObject(value.waypoints);
    }

    public startTracking(secs: number) {
        // TODO implement wait for async func to finish till next interval
        // http://thecodeship.com/web-development/alternative-to-javascript-evil-setinterval/
        this.currentRoute = new geo.Route();
        let sub = this._gmHelperService.getCoordinates().subscribe(
            data => {
                let lat = data.coords.latitude;
                let lng = data.coords.longitude;
                this.addPoint(START, lat, lng);
                this._gmHelperService.currentLatLng = { lat: lat, lng: lng };
            },
            error => console.error(error),
            () => sub.unsubscribe()
        );
        this._trackingId = setInterval(() => {
            let sub = this._gmHelperService.getCoordinates().subscribe(
                data => {
                    let lat = data.coords.latitude;
                    let lng = data.coords.longitude;
                    this.addPoint(WAYPOINT, lat, lng);
                    this._gmHelperService.currentLatLng = { lat: lat, lng: lng };
                },
                error => console.error(error),
                () => sub.unsubscribe()
            );
        }, secs * 1000);
    }

    public stopTracking() {
        clearInterval(this._trackingId);
        let sub = this._gmHelperService.getCoordinates().subscribe(
            data => {
                let lat = data.coords.latitude;
                let lng = data.coords.longitude;
                this.addPoint(END, lat, lng);
                this._gmHelperService.currentLatLng = { lat: lat, lng: lng };
            },
            error => console.error(error),
            () => sub.unsubscribe()
        );
    }

    public addPoint(field: string, lat: number, lng: number, img?: string) {
        let sub = this._gmHelperService.getGeocode(lat + ' ' + lng).subscribe(
            data => {
                let barsub = this._geolocationService.getBarometricPressure().subscribe(
                    (pressure) => {
                        this.currentRoute.addToRoute(
                            field,
                            data['formattedAddress'] ?
                                data['formattedAddress'] :
                                `Marker | Lat ${lat} | Lng ${lng}`,
                            lat, lng, undefined, pressure.val, img
                        );
                    },
                    error => {
                        console.error(error);
                        this.currentRoute.addToRoute(
                            field,
                            data['formattedAddress'] ?
                                data['formattedAddress'] :
                                `Marker | Lat ${lat} | Lng ${lng}`,
                            lat, lng, undefined, undefined, img
                        );
                    },
                    () => barsub.unsubscribe()
                )
            },
            error => {
                let barsub = this._geolocationService.getBarometricPressure().subscribe(
                    (pressure) => {
                        this.currentRoute.addToRoute(
                            field, `Marker | Lat ${lat} | Lng ${lng}`,
                            lat, lng, undefined, pressure.val, img
                        );
                    },
                    error => {
                        console.error(error);
                        this.currentRoute.addToRoute(
                            field, `Marker | Lat ${lat} | Lng ${lng}`,
                            lat, lng, undefined, undefined, img
                        );
                    },
                    () => barsub.unsubscribe()
                )
            },
            () => {
                this._currentRoute.next(this.currentRoute);
                sub.unsubscribe();
            });
    }

    public removePoint(evt: any) {
        this.currentRoute.removeFromRoute(evt.self);
        this._currentRoute.next(this.currentRoute);
    }

    public set(point: any, lat: number, lng: number) {
        this.currentRoute.set(point, lat, lng);
        this._currentRoute.next(this.currentRoute);
    }

    public loadRoute(callback, values?: Object) {
        let currentRoute;
        if (values) {
            this.currentRoute = new geo.Route(values);
            callback(false);
        } else {
            currentRoute = new geo.Route();
            this._filehandlerService.openFile((data) => {
                callback(data.error);
                if (data.error) {
                    return;
                }
                let doc = new DOMParser().parseFromString(data['result'], 'text/xml');
                let route = data['isKML'] ? tj.kml(doc) : tj.gpx(doc);
                let features = route['features'];
                if (features) {
                    for (let i = 0; i < features.length; i++) {
                        let feature = features[i];
                        if (feature['geometry']) {
                            if (feature['geometry']['type'] === 'Point') {
                                let field;
                                if (i === 0) {
                                    field = START;
                                } else if (i < (features.length - 1)) {
                                    field = WAYPOINT;
                                } else {
                                    field = END;
                                }
                                let coords = feature['geometry']['coordinates'],
                                    prop = feature['properties'],
                                    lat = <number>coords[1],
                                    lng = <number>coords[0],
                                    desc = prop['desc'],
                                    indexOfTime = desc.toLowerCase().indexOf("time"),
                                    indexOfPressure = desc.toLowerCase().indexOf("pressure"),
                                    indexOfImage = desc.toLowerCase().indexOf("img"),
                                    time: Date,
                                    pressure: number,
                                    img: string;
                                if (indexOfTime > -1) {
                                    time = new Date(desc.substring(indexOfTime).split('=')[1]);
                                }
                                if (indexOfPressure > -1) {
                                    pressure = desc.substring(indexOfPressure).split('=')[1];
                                }
                                if (indexOfImage > -1) {
                                    img = desc.substring(indexOfImage).split('=')[1];
                                }
                                currentRoute.addToRoute(field, prop['name'] || `Marker | Lat ${lat} | Lng ${lng}`, lat, lng, time, pressure, img);
                            }
                        }
                    }
                }
                this.currentRoute = currentRoute;
            });
        }
    }

    public saveFile(metadata: Object, isKML: boolean, callback) {
        this.currentRoute.name = metadata['name'];
        this.currentRoute.desc = metadata['desc'];
        this.currentRoute.author = metadata['author'];
        this.currentRoute.time = metadata['time'];
        this._filehandlerService.saveFile(this.currentRoute.convertToGPX(metadata), metadata['name'], isKML, callback);
    }

}
