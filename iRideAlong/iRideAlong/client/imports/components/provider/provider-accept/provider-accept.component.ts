import {ModalHelperService} from "../../../services/modal-helper-service/modal-helper.service";
import {MaterializeDirective} from "angular2-materialize";
import {Component, Input, Output, EventEmitter} from '@angular/core';

@Component({
    selector: 'provider-accept',
    templateUrl: 'client/imports/components/provider/provider-accept/provider-accept.component.html', // OR html in file:
    styleUrls: ['./styles/provider-accept.component.min.css'],
    directives: [MaterializeDirective]
})
export class ProviderAcceptComponent {

    @Input() line: string; // from main-comp
    @Input() direction: string; // from main-comp

    @Output() onAccept = new EventEmitter<boolean>();
    @Output() onDecline = new EventEmitter<boolean>();

    constructor(private _modalHelper: ModalHelperService) {
    }

    /**
     * openMeetingpointModal - open next modal
     *
     * @returns {void}
     */
    public openMeetingpointModal() {
        this.onAccept.emit(true);
    }

}
