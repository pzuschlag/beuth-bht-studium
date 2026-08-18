// This file / these dependencies is/are loaded prior to the main app (components etc.) and are valid throughout the whole (global) scope of the app
declare var require: any;
import {Meteor} from 'meteor/meteor';

console.log(`"iRideAlong" running: ${Meteor.absoluteUrl()}`);

import 'reflect-metadata';
import 'zone.js/dist/zone';


// hotfix for https://github.com/socketio/socket.io-client/issues/961
let Response = require('meteor-node-stubs/node_modules/http-browserify/lib/response');
if (!Response.prototype.setEncoding) {
    Response.prototype.setEncoding = (encoding) => {
        // do nothing
    }
}

import * as $ from 'jquery';
// put variables into global scope
(<any>window).$ = $;
(<any>window).jQuery = $;
console.log("dependencies.ts loaded");
