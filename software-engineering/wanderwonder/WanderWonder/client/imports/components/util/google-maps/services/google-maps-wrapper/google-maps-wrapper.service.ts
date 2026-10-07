import {Injectable, NgZone} from '@angular/core';
import {Observer} from 'rxjs/Observer';
import {Observable} from 'rxjs/Observable';
import {GoogleMapsApiLoaderService} from "../../services";

declare var google: any;

@Injectable()
export class GoogleMapsWrapperService {

    private _map: Promise<google.maps.Map>;
    private _mapResolver: (value?: google.maps.Map) => void;
    private _infoWindow: google.maps.InfoWindow;
    private _listeners: google.maps.MapsEventListener[] = [];

    constructor(private _googleAPI: GoogleMapsApiLoaderService, private _zone: NgZone) {
        console.log("GoogleMapsWrapperService constructor");
    }

    public clearListener() {
        this._googleAPI.clearListener(this._listeners);
    }

    public initMap() {
        this._map = new Promise<google.maps.Map>(
            (resolve: () => void) => this._mapResolver = resolve
        );
    }

    public createMap(element: Element, options: Object = {}) {
        this._googleAPI.loadGoogleMaps(element, options).then((map) => {
            this._mapResolver(map);
        })
    }

    createGooglePoint(x: number, y: number): Promise<google.maps.Point> {
        return this._googleAPI.createGooglePoint(x, y).then((point: google.maps.Point) => {
            return point;
        });
    }

    public zoomToObject(objArr: Object[]) {
        this._googleAPI.getLatLngBounds(objArr).then((bounds: google.maps.LatLngBounds) => {
            this._map.then((map: google.maps.Map) => { map.fitBounds(bounds); });
        });
    }

    public setMapOptions(options: Object) {
        this._map.then((map: google.maps.Map) => { map.setOptions(options); });
    }

    public redraw() {
        this._map.then((map: google.maps.Map) => { this._googleAPI.redraw(map) });
    }

    public getGeocode(address: string): Observable<any> {
        return Observable.create(observer => {
            this._googleAPI.loadGoogleGeocoder().then((geocoder) => {
                geocoder.geocode({ 'address': address }, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        observer.next({
                            formattedAddress: results[0].formatted_address,
                            lat: results[0].geometry.location.lat(),
                            lng: results[0].geometry.location.lng()
                        });
                    } else {
                        status == google.maps.GeocoderStatus.ZERO_RESULTS ?
                            observer.next({ formattedAddress: null, lat: null, lng: null }) :
                            observer.error('geocoder error' + status);
                    }
                    observer.complete();
                });
            });
        });
    }

    public openInfoWindow(contentString: string, latLng: google.maps.LatLng) {
        this._map.then((map: google.maps.Map) => {
            if (this._infoWindow) {
                this._infoWindow.close();
            }
            this._infoWindow = new google.maps.InfoWindow({
                content: contentString
            });
            this._infoWindow.setPosition(latLng);
            this._infoWindow.open(map);
        });
    }

    public createMarker(options: Object):
        Promise<google.maps.Marker> {
        return this._map.then((map: google.maps.Map) => {
            options['map'] = map;
            return new google.maps.Marker(options);
        });
    }

    public createCircle(options: Object):
        Promise<google.maps.Circle> {
        return this._map.then((map: google.maps.Map) => {
            options['map'] = map;
            return new google.maps.Circle(options);
        });
    }

    public createPolyline(options: Object): Promise<google.maps.Polyline> {
        return this._map.then((map: google.maps.Map) => {
            options['map'] = map;
            return new google.maps.Polyline(options);
        });
    }
    public getCenter(): Promise<google.maps.LatLng> {
        return this._map.then((map: google.maps.Map) => map.getCenter());
    }

    public setCenter(latLng: google.maps.LatLngLiteral) {
        this._map.then((map: google.maps.Map) => { map.setCenter(latLng); });
    }

    public getZoom(): Promise<number> {
        return this._map.then((map: google.maps.Map) => map.getZoom());
    }

    public setZoom(zoom: number) {
        this._map.then((map: google.maps.Map) => { map.setZoom(zoom); });
    }

    public setTilt(tilt: number) {
        this._map.then((map: google.maps.Map) => { map.setTilt(tilt); });
    }

    public setHeading(heading: number) {
        this._map.then((map: google.maps.Map) => { map.setHeading(heading); });
    }

    public setMapTypeId(mapTypeId: string) {
        this._googleAPI.getMapType(mapTypeId).then((mapType: google.maps.MapTypeId) => {
            this._map.then((map: google.maps.Map) => {
                console.log(mapType);
                map.setMapTypeId(mapType);
            });
        })
    }

    public subscribeToMapEvent<E>(eventName: string): Observable<E> {
        return Observable.create((observer: Observer<E>) => {
            this._map.then((m: google.maps.Map) => {
                this._listeners.push(m.addListener(eventName, (arg: E) => { this._zone.run(() => observer.next(arg)); }));
            });
        });
    }

    get map(): Promise<google.maps.Map> { return this._map; }

}
