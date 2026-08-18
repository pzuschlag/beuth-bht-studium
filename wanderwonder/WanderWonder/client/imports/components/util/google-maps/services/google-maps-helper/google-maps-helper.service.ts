import {GoogleMapsWrapperService} from "../../services";
import {Injectable, OnInit, Inject, forwardRef} from '@angular/core';
import {Observable, BehaviorSubject, Subject, Subscription} from 'rxjs';
import {CordovaGeolocationService} from "../../../../../services/cordova-geolocation/cordova-geolocation.service";
import {geo} from '../../../../../../classes/geo';

@Injectable()
export class GoogleMapsHelperService {

    private _currentLocation: Subject<geo.Point> = new Subject<geo.Point>();
    private _currentLocation$: Observable<geo.Point> = this._currentLocation.asObservable();
    private _currentLatLng: Subject<google.maps.LatLngLiteral> = new Subject<google.maps.LatLngLiteral>();
    private _currentLatLng$: Observable<google.maps.LatLngLiteral> = this._currentLatLng.asObservable();
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

    public zoomToObject(objArr: Array<Object>) {
        this._gmWrapperService.zoomToObject(objArr);
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

    public get currentLocation$(): Observable<geo.Point> {
        return this._currentLocation$;
    }

    public set currentLatLng(value: google.maps.LatLngLiteral) {
        this._currentLatLng.next({ lat: value.lat, lng: value.lng })
    }

    public get currentLatLng$(): Observable<Object> {
        return this._currentLatLng$;
    }

    public refreshCurrentLatLng() {
        let sub =
            this._geolocationService.getCoordinates({ enableHighAccuracy: true }).subscribe(
                data => {
                    this.currentLatLng = { lat: data.coords.latitude, lng: data.coords.longitude };
                },
                error => console.error(error),
                () => sub.unsubscribe()
            );
    }

    public getCoordinates() {
        return this._geolocationService.getCoordinates({ enableHighAccuracy: true });
    }

}
