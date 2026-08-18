/**
 * Created by jroehl on 03.05.16.
 */
import {bootstrap} from 'angular2-meteor-auto-bootstrap';
import {provide} from '@angular/core';

import {ROUTER_PROVIDERS} from "@angular/router";
import {APP_BASE_HREF} from '@angular/common';
import {WanderWonder} from './imports/wanderwonder';
import {ANGULAR2_GOOGLE_MAPS_PROVIDERS} from 'angular2-google-maps/core';
import {HammerTimeService} from "./imports/services/hammer-time/hammer-time.service";
import {CordovaFilehandlerService} from "./imports/services/cordova-filehandler/cordova-filehandler.service";
import {CordovaGeolocationService} from "./imports/services/cordova-geolocation/cordova-geolocation.service";
import {GOOGLE_MAPS_SERVICE_PROVIDER} from "./imports/components/util/google-maps/services";
import {RouteManagerService} from "./imports/services/route-manager/route-manager.service";
import {GlobalHelperService} from "./imports/services/global-helper-service/global-helper-service.service";
import {DatabaseHelperService} from "./imports/services/database-helper/database-helper.service";
import {CordovaCameraService} from "./imports/services/cordova-camera-service/cordova-camera.service";
import 'angular2-materialize';

bootstrap(WanderWonder, [
    ROUTER_PROVIDERS,
    GlobalHelperService,
    CordovaGeolocationService,
    CordovaFilehandlerService,
    GOOGLE_MAPS_SERVICE_PROVIDER,
    RouteManagerService,
    HammerTimeService,
    DatabaseHelperService,
    CordovaCameraService,
    provide(APP_BASE_HREF, { useValue: '/' })
]);
