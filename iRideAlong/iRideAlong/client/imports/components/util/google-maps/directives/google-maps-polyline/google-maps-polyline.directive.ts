import {Directive, Input, Output, EventEmitter, NgZone} from '@angular/core';
import {GoogleMapsHelperService, GoogleMapsWrapperService} from "../../services";

@Directive({
    selector: 'google-maps-polyline',
    providers: []
})
export class GoogleMapsPolylineDirective {

    private addedToStorage: boolean = false;

    private _strokeColor: string = '#00ffb3';
    private _strokeOpacity: number = 1;
    private _strokeWeight: number = 2;
    private _draggable: boolean = false;
    private _clickable: boolean = true;
    private _editable: boolean = false;
    private _path: Array<google.maps.LatLngLiteral> = [];
    private _id: string;

    private _polyline: google.maps.Polyline;

    @Output() lineClick: EventEmitter<google.maps.Marker> =
    new EventEmitter<google.maps.Marker>();
    @Output() dragDone: EventEmitter<google.maps.MouseEvent> =
    new EventEmitter<google.maps.MouseEvent>();

    constructor(
        private _googleMapsWrapper: GoogleMapsWrapperService,
        private _googleMapsHelper: GoogleMapsHelperService,
        private _zone: NgZone
    ) {
    }

    ngOnInit() {
        this._googleMapsWrapper.createPolyline(this.options).then((polyline: google.maps.Polyline) => {
            this._polyline = polyline;
            this.addEventListeners();
        });
    }

    ngOnDestroy() {
        this._polyline.setMap(null);
    }

    @Input()
    set id(value: string) {
        this._id = value;
    }

    get id(): string {
        return this._id;
    }

    @Input()
    set path(latLngArr: Array<google.maps.LatLngLiteral>) {
        this._path = latLngArr;
        if (this._polyline) {
            this._polyline.setPath(latLngArr);
        }
    };

    get path(): Array<google.maps.LatLngLiteral> {
        return this._path;
    }

    @Input()
    set draggable(value: boolean) {
        this._draggable = value;
        if (this._polyline) {
            this._polyline.setDraggable(value);
        }
    };

    get draggable(): boolean {
        return this._draggable;
    }

    @Input()
    set clickable(value: boolean) {
        this._clickable = value;
        this.updateOptions();
    };

    get clickable(): boolean {
        return this._clickable;
    }

    @Input()
    set editable(value: boolean) {
        this._editable = value;
        if (this._polyline) {
            this._polyline.setEditable(value);
        }
    };

    get editable(): boolean {
        return this._editable;
    }

    @Input()
    set strokeOpacity(value: number | string) {
        this._strokeOpacity = this._googleMapsHelper.convertToNumber(
            value, null);
        this.updateOptions();
    }

    get strokeOpacity(): number | string {
        return this._strokeOpacity;
    }

    @Input()
    set strokeWeight(value: number | string) {
        this._strokeWeight = this._googleMapsHelper.convertToNumber(
            value, null);
        this.updateOptions();
    }

    get strokeWeight(): number | string {
        return this._strokeWeight;
    }

    @Input()
    set strokeColor(value: string) {
        this._strokeColor = value;
        this.updateOptions();
    }

    get strokeColor(): string {
        return this._strokeColor;
    }

    get options(): Object {
        return {
            clickable: this.clickable,
            draggable: this.draggable,
            path: this.path,
            strokeColor: this.strokeColor,
            strokeOpacity: this.strokeOpacity,
        }
    }

    private updateOptions() {
        if (this._polyline) {
            this._polyline.setOptions(this.options);
        }
    }

    private addEventListeners() {
        let events: any[] = [
            { name: 'click', emitter: this.lineClick },
            { name: 'dragend', emitter: this.dragDone },
        ];
        events.forEach((e: Event) => {
            this._polyline.addListener(e['name'], (event: google.maps.MouseEvent) => this._zone.run(() => e['emitter'].next({
                self: this, coords: {
                    lat: event.latLng.lat(),
                    lng: event.latLng.lng()
                }
            })));
        });
    }
}
