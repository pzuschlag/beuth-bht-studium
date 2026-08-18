import {Injectable, OnInit} from '@angular/core';
import {Observable, BehaviorSubject, Subscription} from 'rxjs';
import {GoogleMapsHelperService} from "../../components/util/google-maps/services";
import {CordovaGeolocationService} from "../cordova-geolocation/cordova-geolocation.service";
import {CordovaFilehandlerService} from "../cordova-filehandler/cordova-filehandler.service";
import {geo} from '../../../classes/geo';
import {Routes, Points} from '../../../../imports/api/geo';
import {MeteorComponent} from 'angular2-meteor';
import {Meteor} from 'meteor/meteor';


@Injectable()
export class DatabaseHelperService extends MeteorComponent {

    private _routes: any;
    private _points: any;

    constructor() {
        super();
        this.subscribe('routes', () => {
        }, true);
        this.subscribe('points', () => {
        }, true);
    }

    get routes(): any[] {
        return Routes.find().fetch();
    }

    get points(): any[] {
        return Points.find().fetch();;
    }

    addRoute(route: geo.Route, callback) {
        let ids: string[] = [];
        for (let point of route.waypoints) {
            this.addPoint(point, (err, id) => {
                if (err) {
                    console.error(err);
                    callback(`Fehler beim hinzufügen der Punkte ${err}`);
                } else {
                    point.id = id;
                    ids.push(id);
                }
                if (ids.length >= route.waypoints.length) {
                    route.pointIds = ids;
                    Meteor.call(
                        'addRoute',
                        route, ids,
                        (err, id) => {
                            if (err) {
                                console.error(err);
                                callback(`Fehler beim hinzufügen der Route ${err}`);
                            } else {
                                callback(null, `Route "${route.name}" wurde unter der id "${id}" der Datenbank hinzugefügt`);
                                route.id = id;
                            }
                        });
                }
            });
        }
    }

    addPoint(point: geo.Point, callback?) {
        Meteor.call(
            'addPoint', point,
            callback ? callback : (err, id) => {
                if (err) {
                    console.error(err);
                    // TODO
                } else {
                    point.id = id;
                }
            });
    }

    findRoute(query: string): Observable<Object[]> {
        return Observable.create(observer => {
            Meteor.call(
                'findRoute', query,
                (err, res) => {
                    if (err) {
                        observer.error(err);
                    } else {
                        observer.next(res);
                        observer.complete();
                    }
                });
        });

    }

    getPoint(id: string): any {
        return Points.find({ id: id });
    }

    public idExists(id: string): boolean {
        return (this.findRoute(id) || this.getPoint(id));
    }

}
