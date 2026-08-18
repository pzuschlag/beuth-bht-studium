import {Injectable} from '@angular/core';
import {Meteor} from 'meteor/meteor';

const URL = Meteor.isCordova ?
    'https://maps.googleapis.com/maps/api/js?v=3&key=AIzaSyDuNhMm2LnWWODHKQ8pIEe-uRcj4n0Oxu4&callback=__onGoogleLoaded' :
    'https://maps.googleapis.com/maps/api/js?v=3&callback=__onGoogleLoaded';

@Injectable()
export class GoogleMapsApiLoaderService {

    private loadAPI: Promise<any>;

    constructor() {
        this.loadAPI = new Promise((resolve) => {
            window['__onGoogleLoaded'] = () => {
                console.log('google api loaded', (<any>window).google);
                resolve((<any>window).google);
            }
            this.loadScript()
        });
    }

    loadGoogleMaps(element: any, options: Object): Promise<google.maps.Map> {
        return this.loadAPI.then((google) => {
            return new google.maps.Map(element, options);
        });
    }

    loadGoogleGeocoder(): Promise<google.maps.Geocoder> {
        return this.loadAPI.then((google) => {
            return new google.maps.Geocoder();
        });
    }

    createGooglePoint(x: number, y: number): Promise<google.maps.Point> {
        return this.loadAPI.then((google) => {
            return new google.maps.Point(x, y);
        });
    }

    getMapType(mapTypeId: string): Promise<google.maps.MapTypeId> {
        return this.loadAPI.then((google) => {
            switch (mapTypeId) {
                case 'TERRAIN':
                    return google.maps.MapTypeId.TERRAIN;
                case 'SATELLITE':
                    return google.maps.MapTypeId.SATELLITE;
                case 'HYBRID':
                    return google.maps.MapTypeId.HYBRID;
                default:
                    return google.maps.MapTypeId.ROADMAP;
            }
        });
    }

    getLatLngBounds(objArr): Promise<google.maps.LatLngBounds> {
        return this.loadAPI.then((google) => {
            var bounds = new google.maps.LatLngBounds();
            for (var n = 0; n < objArr.length; n++) {
                bounds.extend(
                    new google.maps.LatLng(objArr[n].lat, objArr[n].lng)
                );
            }
            return bounds;
        });
    }

    getLatLng(lat: number, lng: number): Promise<google.maps.LatLng> {
        return this.loadAPI.then((google) => {
            return new google.maps.LatLng(lat, lng);
        });
    }

    clearListener(listener: google.maps.MapsEventListener[]) {
        return this.loadAPI.then((google) => {
            for (let i = 0; i < listener.length; i++) {
                google.maps.event.removeListener(listener[i]);
            }
        });
    }

    redraw(map: google.maps.Map) {
        this.loadAPI.then((google) => {
            console.log("redrawing");
            google.maps.event.trigger(map, 'resize');
        });
    }

    loadScript() {
        let node = document.createElement('script');
        node.src = URL;
        node.type = 'text/javascript';
        document.getElementsByTagName('head')[0].appendChild(node);
    }
}
