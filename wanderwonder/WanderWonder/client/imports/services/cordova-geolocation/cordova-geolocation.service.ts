import {Injectable, OnInit} from '@angular/core';
import {Observable} from 'rxjs';

const GEOLOCATION_ERRORS = {
    'errors.location.unsupportedBrowser': 'Browser does not support location services',
    'errors.location.permissionDenied': 'You have rejected access to your location',
    'errors.location.positionUnavailable': 'Unable to determine your location',
    'errors.location.timeout': 'Service timeout has been reached',
    'errors.pressure.unsupportedBrowser': 'Browser does not support pressure services',
    'errors.pressure.permissionDenied': 'You have rejected access to your pressure',
    'errors.pressure.positionUnavailable': 'Unable to determine your pressure',
    'errors.pressure.timeout': 'Service timeout has been reached'
};

declare var navigator: any;

@Injectable()
export class CordovaGeolocationService {

    private _watchid: any;

    constructor() {
        console.log("CordovaGeolocationService constructor");
    }

    public getCoordinates(opts?: Object): Observable<any> {
        return Observable.create(observer => {
            if (navigator && navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        observer.next(position);
                        observer.complete();
                    },
                    (error) => {
                        switch (error.code) {
                            case 1:
                                observer.error(GEOLOCATION_ERRORS['errors.location.permissionDenied']);
                                break;
                            case 2:
                                observer.error(GEOLOCATION_ERRORS['errors.location.positionUnavailable']);
                                break;
                            case 3:
                                observer.error(GEOLOCATION_ERRORS['errors.location.timeout']);
                                break;
                        }
                    },
                    opts || {});

            } else {
                observer.error(GEOLOCATION_ERRORS['errors.location.unsupportedBrowser']);
            }
        });
    }

    public trackCoordinates(opts?: Object): Observable<any> {
        return Observable.create(observer => {
            if (navigator && navigator.geolocation) {
                this._watchid = navigator.geolocation.watchPosition(
                    (position) => {
                        observer.next(position);
                    },
                    (error) => {
                        switch (error.code) {
                            case 1:
                                observer.error(GEOLOCATION_ERRORS['errors.location.permissionDenied']);
                                break;
                            case 2:
                                observer.error(GEOLOCATION_ERRORS['errors.location.positionUnavailable']);
                                break;
                            case 3:
                                observer.error(GEOLOCATION_ERRORS['errors.location.timeout']);
                                break;
                        }
                    },
                    opts || {});

            } else {
                observer.error(GEOLOCATION_ERRORS['errors.location.unsupportedBrowser']);
            }
        });
    }

    public stopTracking() {
        return navigator.geolocation.clearWatch(this._watchid);
    }

    public getBarometricPressure(): Observable<any> {
        return Observable.create(observer => {
            if (navigator && navigator.barometer) {
                navigator.barometer.getCurrentPressure(
                    (pressure) => {
                        observer.next(pressure);
                        observer.complete();
                    },
                    (error) => {
                        switch (error.code) {
                            case 1:
                                observer.error(GEOLOCATION_ERRORS['errors.pressure.permissionDenied']);
                                break;
                            case 2:
                                observer.error(GEOLOCATION_ERRORS['errors.pressure.positionUnavailable']);
                                break;
                            case 3:
                                observer.error(GEOLOCATION_ERRORS['errors.pressure.timeout']);
                                break;
                        }
                    });
            } else {
                observer.error(GEOLOCATION_ERRORS['errors.pressure.unsupportedBrowser']);
            }
        });
    }

}
