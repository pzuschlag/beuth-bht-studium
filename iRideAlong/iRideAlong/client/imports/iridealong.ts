import {CordovaGeofenceService} from "./services/cordova-geofence/cordova-geofence.service";
// import {RedisTestComponent} from "./components/poc/redis-test/redis-test.component";
import {Component, ViewEncapsulation, OnInit} from '@angular/core';
import {Routes, ROUTER_DIRECTIVES, Router} from '@angular/router';
import {StartComponent} from "./components/start/start.component";
import {ProviderMainComponent} from "./components/provider/provider-main/provider-main.component";
import {PassengerMainComponent} from "./components/passenger/passenger-main/passenger-main.component";
import {PreloaderComponent} from "./components/util/preloader/preloader.component";
import {Meteor} from 'meteor/meteor';

// declare variables for typescript compiler
// from already imported modules
// (eg. jquery installed via meteor)
// declare var $: any;

@Component({
    selector: 'i-ride-along',
    templateUrl: 'client/imports/iridealong.html',
    styleUrls: ['./styles/iridealong.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [ROUTER_DIRECTIVES, PreloaderComponent]
    // encapsulation: ViewEncapsulation.None
})

@Routes([
    { path: '/', component: StartComponent }, // useAsDefault: true
    { path: '/provider', component: ProviderMainComponent },
    { path: '/passenger', component: PassengerMainComponent }
])

export class iRideAlong implements OnInit {

    constructor(private _router: Router) {
    }

    ngOnInit() {
        $('main').height($(window).height());
        // $(window).bind('orientationchange', (e) => {
        //     setTimeout(() => {
        //         $('main').height($(window).height());
        //     }, 250);
        //     console.log(e, 'orientationchange');
        // });
    }

}
