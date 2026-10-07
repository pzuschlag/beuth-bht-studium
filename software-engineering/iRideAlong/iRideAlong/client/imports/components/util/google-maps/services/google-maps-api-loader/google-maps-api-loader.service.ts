import {Injectable} from '@angular/core';

// const url = 'https://apis.google.com/js/client.js?onload=__onGoogleLoaded';
const URL = 'https://maps.googleapis.com/maps/api/js?v=3&callback=__onGoogleLoaded';

@Injectable()
export class GoogleMapsApiLoaderService {

    private loadAPI: Promise<any>;

    constructor() {
        this.loadAPI = new Promise((resolve) => {
            window['__onGoogleLoaded'] = () => {
                console.log('google api loaded');
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

    loadGoogleGeocoder() {
        return this.loadAPI.then((google) => {
            return new google.maps.Geocoder();
        });
    }

    loadGoogleLatLng(lat: number, lng: number) {
        return this.loadAPI.then((google) => {
            return new google.maps.LatLng({ lat: lat, lng: lng });
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
            google.maps.event.trigger(map, 'resize');
        });
    }

    //generate script-tag and append it to the head to load the map
    loadScript() {
        let node = document.createElement('script');
        node.src = URL;
        node.type = 'text/javascript';
        document.getElementsByTagName('head')[0].appendChild(node);

    }
}
