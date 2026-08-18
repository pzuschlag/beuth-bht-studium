import {StationHelperService} from "../station-helper/station-helper.service";
import {CordovaGeofenceService} from "../cordova-geofence/cordova-geofence.service";
import {Injectable} from '@angular/core';
import {Observable, Subject, BehaviorSubject} from 'rxjs';
import {toast} from 'angular2-materialize';
import {Meteor} from 'meteor/meteor';

declare var navigator: any;

/**
 *   Global helper service to store methods which can be used throughout the app..
 */
Injectable()
export class GlobalHelperService {

    private _loadingMessage: Subject<string | string[]> = new Subject<string | string[]>();

    public set loadingMessage(msg: string | string[]) {
        this._loadingMessage.next(msg); // write karmaPoints to the Subject
    }
    public get loadingMessage$(): Observable<string | string[]> {
        return this._loadingMessage.asObservable();
    }

    /**
     *  Check if device is ios
     *
     *  @return {boolean}  true if device is ios
     */
    get isIOS(): boolean {
        return navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false;
    }

    /**
     *  Check if device is android
     *
     *  @return {boolean}  true if device is android
     */
    get isANDROID(): boolean {
        return navigator.userAgent.toLowerCase().indexOf("android") > -1;
    }

    openedFromNotification(geofence: CordovaGeofenceService, stationHelper: StationHelperService, subscribe, isProvider?: boolean) {
        if (Meteor.isCordova) {
            let sub = geofence.openedFromNotification$.subscribe(
                (res) => {
                    console.log(`GlobalHelperService ${res ? '' : 'not'} openedFromNotification ${res ? 'at ' + res['name'] : ''}`);
                    if (res) {
                        let station = stationHelper.getStation(res['id']);
                        let active = isProvider ? station.active : true;
                        if (station && active) {
                            geofence.isAuthorized(station, (res) => {
                                if (res['authorized']) {
                                    subscribe(station);
                                } else if (res['dist']) {
                                    Toasts.distTooHigh(res['dist'], station.name);
                                } else {
                                    Toasts.locationError();
                                }
                            });
                        }
                    }
                }, (err) => console.error(err), () => sub ? sub.unsubscribe() : console.log("openedFromNotification already unsubscribed")
            );
        }
    }

}

const CONNERR: string = `If the problem persists, check your connection settings and restart the app.`;

export class Toasts {

    static connectionError() {
        this.triggerToast(`We could not establish a connection. ${CONNERR}`);
    }

    static disconnect() {
        this.triggerToast(`You have been disconnected from server. ${CONNERR}`);
    }

    static locationError() {
        this.triggerToast(`Ooops there is something wrong with your location, it is mandatory to provide access to your geolocation`);
    }

    static generalError() {
        this.triggerToast(`Ooops something went wrong, please try again`);
    }

    static distTooHigh(dist: string, station: string) {
        this.triggerToast(`You are ${dist} meters short :) move closer to ${station} to start the fun`);
    }

    static tooManyRequests() {
        this.triggerToast(`Please do not Spam! One request per 30 seconds allowed`);
    }

    static noTicket() {
        this.triggerToast(`You have no ticket, or you did not tell us :) Please set up first!`);
    }

    static zoomInMore() {
        this.triggerToast(`Please zoom in more to see the stations`);
    }

    static greeting(station: string) {
        this.triggerToast(`Hey, nice to see you at ${station}`);
    }

    static subscribeError(station: string) {
        this.triggerToast(`There was a problem logging in to ${station}, please try again`);
    }

    static goodbye(station: string) {
        let msg = `Bye bye, see you soon at ${station}`;
        this.triggerToast(msg);
        return msg;
    }

    static karmaThanks() {
        this.triggerToast(`You are awesome, thanks!`);
    }

    static karmaAdded() {
        this.triggerToast(`+ 5 Karma points received!`);
    }

    static autoLogout(station) {
        this.triggerToast(`Too bad - there are no providers left at ${station}, please try again later`);
    }

    static messageSent() {
        this.triggerToast(`Request send! You will get a message as soon as somebody accepts.`);
    }

    static messageNotSent() {
        this.triggerToast(`Message could not be delivered, please try again`);
    }

    static passengerNotPresent() {
        this.triggerToast(`Your passenger already hitched a ride. Please wait for next request`);
    }

    static confirmationSent() {
        this.triggerToast(`Confirmation sent. Please wait for your ride buddy at your meetingpoint.`);
    }

    static confirmationNotSent() {
        this.triggerToast(`You cancelled the confirmation process. Please log in to the station again.`);
    }

    static noProviders(station: string) {
        this.triggerToast(`Sorry there are currently no nice people at ${station}`);
    }

    static triggerToast(msg: string) {
        toast(msg, 2500);
    }
}
