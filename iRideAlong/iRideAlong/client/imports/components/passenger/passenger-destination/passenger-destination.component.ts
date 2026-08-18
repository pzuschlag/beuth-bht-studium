import {Component, Input, Output, EventEmitter} from '@angular/core';
import {MaterializeDirective} from 'angular2-materialize';
import {Toasts} from "../../../services/global-helper-service/global-helper-service";
import {FormBuilder, Validators} from '@angular/common';


@Component({
    selector: 'passenger-destination',
    templateUrl: 'client/imports/components/passenger/passenger-destination/passenger-destination.component.html', // OR html in file:
    styleUrls: ['./styles/passenger-destination.component.min.css'],
    directives: [MaterializeDirective]
})
export class PassengerDestinationComponent {

    @Output() onSend = new EventEmitter<string>();
    @Output() onDecline = new EventEmitter<boolean>();

    private _dirForm: any;
    private _lineEntered: boolean = false;

    private _keypress: any;
    private _backbutton: any;

    private _height: number = 0;

    constructor(private _fb: FormBuilder) {
        this._dirForm = _fb.group({
            line: ["",
                Validators.compose(
                    [Validators.required, Validators.minLength(1), Validators.maxLength(5)]
                )
            ],
            direction: ["",
                Validators.compose(
                    [Validators.required, Validators.minLength(1), Validators.maxLength(30)]
                )
            ]
        });
    }


    ngOnInit() {
        let self = this;

        this._keypress = (e) => self.enterPress(e);
        this._backbutton = () => self.back();

        document.addEventListener("keypress", self._keypress, false);
        document.addEventListener("backbutton", self._backbutton, false);
        setTimeout(() => {
            this._height = 5.7;
            this.focus("line");
        }, 10);
    }

    private enterPress(e) {
        if (e.which === 13) {
            this.sendRequest();
        }
    }

    private back() {
        console.log("back button clicked");
        if (!this._lineEntered) {
            this.onDecline.emit(true)
        } else {
            this._lineEntered = false;
            setTimeout(() => {
                this.focus("line");
            }, 100)
        }
    }

    private focus(id: string) {
        let el = document.getElementById(id);
        el ? el.focus() : console.error("not fast enough for focusing");
    }

    /**
     * sendRequest - passes string with line and direction to event listener
     *
     * @param  {string} line: string      form input
     * @param  {string} direction: string form input
     * @returns {void}
     */
    public sendRequest() {
        this._lineEntered = this._dirForm.controls.line.valid;

        if (this._dirForm.controls.direction.valid && this._dirForm.controls.line.valid) {
            this.onSend.emit(JSON.stringify(this._dirForm.value));  // send data to event handler in order to call mathod in main comp
        } else if (this._lineEntered) {
            setTimeout(() => {
                this.focus("direction");
            }, 100)
        }
    }

    ngOnDestroy() {
        setTimeout(() => this._height = 0, 10);
        let self = this;
        if (self._keypress && self._backbutton) {
            document.removeEventListener("keypress", self._keypress);
            document.removeEventListener("backbutton", self._backbutton);
        }
    }
}
