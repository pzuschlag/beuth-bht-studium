import {Component, Input, SimpleChange, OnChanges} from '@angular/core';
import {MaterializeDirective} from 'angular2-materialize';

@Component({
    selector: 'passenger-confirmation',
    templateUrl: 'client/imports/components/passenger/passenger-confirmation/passenger-confirmation.component.html', // OR html in file:
    styleUrls: ['./styles/passenger-confirmation.component.min.css'],
    directives: [MaterializeDirective]
})
export class PassengerConfirmationComponent {

    @Input() meetingpoint: string; // from main-comp
    @Input() identifier: string; // from main-comp

    constructor() { }

}
