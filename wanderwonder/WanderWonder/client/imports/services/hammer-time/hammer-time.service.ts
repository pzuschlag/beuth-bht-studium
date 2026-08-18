import {Injectable} from '@angular/core';

declare var Hammer:any;

@Injectable()
export class HammerTimeService {

    private _mc: any;

    constructor() {
      this._mc = new Hammer(document.getElementsByTagName['html'][0]);
    }

    get mc():any {
      return this._mc;
    }
}
