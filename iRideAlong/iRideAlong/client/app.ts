/**
 * Created by jroehl on 03.05.16.
 */
import 'reflect-metadata';
import 'zone.js/dist/zone';

import {bootstrap} from '@angular/platform-browser-dynamic';
import {provide} from '@angular/core';
import 'angular2-materialize';

import {ROUTER_PROVIDERS} from "@angular/router";
import {APP_BASE_HREF} from '@angular/common';

import {iRideAlong} from "./imports/iridealong";

import {StationHelperService} from "./imports/services/station-helper/station-helper.service";
import {JsonHandlerService} from "./imports/services/json-handler/json-handler.service";
import {RedisPubSubService} from "./imports/services/redis-pubsub/redis-pubsub.service";
import {ModalHelperService} from "./imports/services/modal-helper-service/modal-helper.service";
import {GlobalHelperService} from "./imports/services/global-helper-service/global-helper-service";
import {CordovaGeolocationService} from "./imports/services/cordova-geolocation/cordova-geolocation.service";
import {CordovaGeofenceService} from "./imports/services/cordova-geofence/cordova-geofence.service";
import {GOOGLE_MAPS_SERVICE_PROVIDER} from "./imports/components/util/google-maps/services";
import {NotificationService} from "./imports/services/notification-service/notification-service.service";


bootstrap(iRideAlong, [
    ROUTER_PROVIDERS,
    CordovaGeofenceService,
    CordovaGeolocationService,
    GOOGLE_MAPS_SERVICE_PROVIDER,
    GlobalHelperService,
    ModalHelperService,
    RedisPubSubService,
    StationHelperService,
    NotificationService,
    JsonHandlerService,
    provide(APP_BASE_HREF, { useValue: '/' })
]);
