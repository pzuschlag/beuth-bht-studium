import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Meteor} from 'meteor/meteor';
import {DatabaseHelperService} from "../database-helper/database-helper.service";

declare var navigator: any;

/**
 *   Global helper service to store methods which can be used throughout the app..
 */
Injectable()
export class GlobalHelperService {

    constructor() {
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

}
