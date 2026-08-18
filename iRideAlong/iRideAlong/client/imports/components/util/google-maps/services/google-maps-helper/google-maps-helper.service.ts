import {GoogleMapsWrapperService} from "../../services";
import {Injectable, OnInit, Inject, forwardRef} from '@angular/core';
import {Observable, BehaviorSubject, Subject, Subscription} from 'rxjs';
import {CordovaGeolocationService} from "../../../../../services/cordova-geolocation/cordova-geolocation.service";


@Injectable()
export class GoogleMapsHelperService {


    private _currentLatLng: Subject<Object> = new Subject<Object>();
    private _currentLatLng$: Observable<Object> = this._currentLatLng.asObservable();
    private _deactivateControlFunctions: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
    private _deactivateControlFunctions$: Observable<boolean> = this._deactivateControlFunctions.asObservable();

    private _gmWrapperService: GoogleMapsWrapperService;

    constructor(
        @Inject(forwardRef(() => GoogleMapsWrapperService)) _gmWrapperService: GoogleMapsWrapperService,
        private _geolocationService: CordovaGeolocationService) {
        this._gmWrapperService = _gmWrapperService;
    }

    public initNewMap() {
        this._gmWrapperService.initMap();
        this.refreshCurrentLatLng();
    }

    public convertToNumber(value: string | number, defaultValue: number): number {
        if (typeof value === 'string') {
            return parseFloat(value);
        } else if (typeof value === 'number') {
            return <number>value;
        }
        return defaultValue;
    }

    public getGeocode(term: string): Observable<any> {
        return this._gmWrapperService.getGeocode(term);
    }

    public redraw() {
        return this._gmWrapperService.redraw();
    }

    public isInBounds(bounds: google.maps.LatLngBounds, lat: number, lng: number): Promise<boolean> {
        return this._gmWrapperService.getLatLng(lat, lng).then((latLng: google.maps.LatLng) => {
            return bounds.contains(latLng);
        })
    }

    public getBounds(): Promise<google.maps.LatLngBounds> {
        return this._gmWrapperService.getBounds().then((bounds: google.maps.LatLngBounds) => {
            return bounds;
        })
    }

    public openInfoWindow(content: string, latLng: google.maps.LatLng) {
        this._gmWrapperService.openInfoWindow(content, latLng);
    }

    public get deactivateControlFunctions(): boolean {
        return this._deactivateControlFunctions.getValue();
    }

    public get deactivateControlFunctions$(): Observable<boolean> {
        return this._deactivateControlFunctions$;
    }

    public set deactivateControlFunctions(value: boolean) {
        this._deactivateControlFunctions.next(value);
    }

    public get currentLatLng$(): Observable<Object> {
        return this._currentLatLng$;
    }

    public refreshCurrentLatLng(lat?: number, lng?: number, refreshPosition?: boolean, callback?) {
        if (lat && lng) {
            this._currentLatLng.next({ lat: lat, lng: lng, refreshPosition: refreshPosition });
        } else {
            let sub =
                this.getCoordinates().subscribe(
                    data => {
                        this._currentLatLng.next({
                            lat: data.coords.latitude,
                            lng: data.coords.longitude,
                            refreshPosition: true
                        });
                        if (callback) {
                            callback(true);
                        }
                    },
                    error => {
                        this._currentLatLng.error(error);
                        if (callback) {
                            callback(false);
                        }
                    },
                    () => sub.unsubscribe()
                );
        }
    }

    public getCoordinates() {
        return this._geolocationService.getCoordinates({ enableHighAccuracy: true });
    }

}
