import {Component} from '@angular/core';
import {MaterializeDirective} from "angular2-materialize";

declare var $: JQueryStatic;

@Component({
    selector: 'start-user',
    templateUrl: 'client/imports/components/start/start-user/start-user.component.html', // OR html in file:
    styleUrls: ['./styles/start-user.component.min.css'],
    directives: [
      MaterializeDirective
    ]

})
export class StartUserComponent {

    constructor() {
    }

    ngOnInit() {
    }

}
