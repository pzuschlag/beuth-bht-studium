import {MaterializeDirective} from "angular2-materialize";
import {Component, Output, EventEmitter} from '@angular/core';
import {FormBuilder, Validators} from '@angular/common';

@Component({
    selector: 'provider-meetingpoint',
    templateUrl: 'client/imports/components/provider/provider-meetingpoint/provider-meetingpoint.component.html', // OR html in file:
    styleUrls: ['./styles/provider-meetingpoint.component.min.css'],
    directives: [MaterializeDirective]
})
export class ProviderMeetingpointComponent {

    @Output() onSend = new EventEmitter<string>();
    @Output() onDecline = new EventEmitter<boolean>();

    private _mpForm: any;
    private _mpEntered: boolean = false;

    private _keypress: any;
    private _backbutton: any;

    private _height: number = 0;
    private _validators: any = Validators.compose(
        [Validators.required, Validators.minLength(1), Validators.maxLength(120)]
    );

    constructor(private _fb: FormBuilder) {
        this._mpForm = _fb.group({
            meetingpoint: ["", this._validators],
            identifier: ["", this._validators]
        });
    }


    ngOnInit() {
        let self = this;

        this._keypress = (e) => self.enterPress(e);
        this._backbutton = (e) => self.back();

        document.addEventListener("keypress", self._keypress, false);
        document.addEventListener("backbutton", self._backbutton, false);
        setTimeout(() => {
            this._height = 5.7;
            this.focus("meetingpoint");
        }, 10);
    }

    private enterPress(e) {
        if (e.which === 13) {
            this.sendConfirmation();
        }
    }

    private back() {
        if (!this._mpEntered) {
            this.onDecline.emit(true)
        } else {
            this._mpEntered = false;
            this.focus("meetingpoint");
        }
    }

    private focus(id: string) {
        let el = document.getElementById(id);
        el ? el.focus() : console.error("not fast enough for focusing");
    }


    ngOnDestroy() {
        setTimeout(() => this._height = 0, 10);
        let self = this;
        if (self._keypress && self._backbutton) {
            document.removeEventListener("keypress", self._keypress);
            document.removeEventListener("backbutton", self._backbutton);
        }
    }

    /**
     * sendConfirmation - make message from form input and pass to event listener
     *
     * @returns {void}
     */
    public sendConfirmation() {
        this._mpEntered = this._mpForm.controls.meetingpoint.valid;

        if (this._mpForm.controls.identifier.valid && this._mpForm.controls.meetingpoint.valid) {
            this.onSend.emit(JSON.stringify(this._mpForm.value));  // send data to event handler in order to call mathod in main comp
        } else if (this._mpEntered) {
            setTimeout(() => {
                this.focus("identifier");
            }, 100)
        }
    }

}
