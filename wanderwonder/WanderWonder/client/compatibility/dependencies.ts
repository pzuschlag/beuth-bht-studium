// This file / these dependencies is/are loaded prior to the main app (components etc.) and are valid throughout the whole (global) scope of the app
import 'reflect-metadata';
import 'zone.js/dist/zone';

import * as $ from 'jquery';
// put variables into global scope
(<any>window).$ = $;
(<any>window).jQuery = $;
console.log("dependencies.ts loaded");
import * as Hammer from 'hammerjs';
(<any>window).Hammer = Hammer;
