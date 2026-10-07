import {Meteor} from 'meteor/meteor';
import {GlobalHelperService} from "../global-helper-service/global-helper-service";
import {Injectable} from '@angular/core';
import {Subject, Observable} from 'rxjs';

declare var $: JQueryStatic;
declare var cordova: any;

@Injectable()
export class NotificationService {

    private _requests: Subject<any> = new Subject<any>();
    private _confirmations: Subject<any> = new Subject<any>();

    constructor(private _globalHelpder: GlobalHelperService) {
        if (Meteor.isCordova) {
            this.init();
        }
    }

    /**
     * requests$ - getter for scheduled Requests
     */
    get requests$(): Observable<any> {
        return this._requests.asObservable();
    }

    /**
     * confirmations$  -  getter for scheduled confirmations
     */
    get confirmations$(): Observable<any> {
        return this._confirmations.asObservable();
    }

    /**
    * init - eventlistener for notification events
    */
    private init() {

        cordova.plugins.notification.local.on('schedule', (notification) => {
            console.log("scheduled: " + notification.id);
        });

        cordova.plugins.notification.local.on('trigger', (notification, state) => {
            console.log("triggered: " + notification.id);
            if (state === "foreground") { // if app runs in foreground Observable is updated immedietly
                if (notification.id === 1) {
                    this._requests.next(notification)
                } else if (notification.id === 2) {
                    this._confirmations.next(notification)
                };
                this.clear(notification.id);
            } else if (state === "background") { // if app in background it waits for click event
                if (this.isPresent(notification.id)) {
                    setTimeout(() => {
                        this.clear(notification.id) // notification expires after 10 min
                    }, 600000);
                }
            }
        });

        cordova.plugins.notification.local.on('click', (notification) => {
            console.log("clicked: " + notification.id);
            if (notification.id === 1) {
                this._requests.next(notification)
            } else if (notification.id === 2) {
                this._confirmations.next(notification)
            };
            this.clear(notification.id);
            this.clearAll(); //fallback
        });

        cordova.plugins.notification.local.on('clear', (notification) => {
            console.log("cleared: " + notification.id);
        });
    }

    /**
    * scheduleRequest - schedule request arrived notification (provider)
    *
    * @param {string} lin line
    * @param {string} dir direction
    */
    public scheduleRequest(lin: string, dir: string) {
        if (Meteor.isCordova) {
            cordova.plugins.notification.local.schedule({  //hash map with properties (or an array with hashs) and behaviour of notification
                id: 1,
                title: "Ride requested",
                text: "for " + lin + ", direction: " + dir,
                badge: 1
            });
        }
    }

    /**
    * scheduleConfirmation - chedule request confirmed notification (passenger)
    *
    * @param {string} mp meetinpoint
    * @param {string} id identifier
    */
    public scheduleConfirmation(mp: string, id: string) {
        if (Meteor.isCordova) {
            cordova.plugins.notification.local.schedule({
                id: 2,
                title: "Request confirmed",
                text: "look for " + mp + ", on: " + id,
                badge: 1
            });
        }
    }

    /**
    * miscNotification - schedule any request you want (only for general infos, no badge icon)
    *
    * @param {string} title title of notification
    * @param {string} text info text of notification
    */
    public miscNotification(title: string, text: string) {
        if (Meteor.isCordova) {
            cordova.plugins.notification.local.schedule({
                id: 3,
                title: title,
                text: text
            });
        }
    }

    /**
     * clearMisc - clear Misc notification (if triggered)
     *
     * @param {number} id event-id
     */
    public clearMisc() {
        if (this.isPresent(3)) {
            this.clear(3);
        }
    }

    /**
     * clearAll - clear all triggered notification
     *
     */
    public clearAll() {
        if (Meteor.isCordova) {
            cordova.plugins.notification.local.clearAll(() => {
                console.log("clearAll() called");
            }, this);
        }
    }

    /**
    * clear - clear notification (if triggered)
    *
    * @param {number} id event-id
    */
    private clear(id: number) {
        if (Meteor.isCordova) {
            cordova.plugins.notification.local.clear(id, () => {
                console.log("clear() called");
            });
        }
    }

    /**
    * isPresent - check presence
    *
    * @param {number} id of querying notification
    */
    private isPresent(id: number): boolean {
        if (Meteor.isCordova) {
            cordova.plugins.notification.local.isPresent(id, function(present) {
                console.log("is present? " + present ? 'Yes' : 'No');
                return present;
            });
            return false;
        }
        return null;
    };

}

//Methode zur Nutzung
// server((info) => {
//     this._notiService.scheduleConfirmation(info.mp, info.id);
//     let obs = this._notiService.confirmations$.subscribe((noti) => {
//          call proviver-accept.component-modal;
//     });
//     obs.unsubscribe;
// })
