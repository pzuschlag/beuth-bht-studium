import {Directive, Input, Output, EventEmitter, NgZone} from '@angular/core';
import {GoogleMapsHelperService, GoogleMapsWrapperService} from "../../services";

@Directive({
    selector: 'google-maps-circle',
    providers: []
})
export class GoogleMapsCircleDirective {

    private addedToStorage: boolean = false;

    private _longitude: number = 0;
    private _latitude: number = 0;
    private _strokeColor: string = '#00ee88';
    private _strokeOpacity: number = 1;
    private _strokeWeight: number = 2;
    private _fillColor: string = '#00ffb3';
    private _fillOpacity: number = 0.2;
    private _radius: number = 10;
    private _draggable: boolean = false;
    private _editable: boolean = false;
    private _id: string;
    private _clickable: boolean = false;

    private _circle: google.maps.Circle;

    @Output() circleClick: EventEmitter<google.maps.Circle> =
    new EventEmitter<google.maps.Circle>();
    @Output() dragDone: EventEmitter<google.maps.MouseEvent> =
    new EventEmitter<google.maps.MouseEvent>();

    constructor(
        private _googleMapsWrapper: GoogleMapsWrapperService,
        private _googleMapsHelper: GoogleMapsHelperService,
        private _zone: NgZone
    ) {
    }

    ngOnInit() {
        this._googleMapsWrapper.createCircle(this.options).then((circle: google.maps.Circle) => {
            console.log(circle);
            this._circle = circle
            this.addEventListeners();
        });
    }

    ngOnDestroy() {
        this._circle.setMap(null);
    }

    @Input()
    set id(id: string) {
        this._id = id;
    }

    get id(): string {
        return this._id;
    }

    @Input()
    set longitude(value: number) {
        this._longitude = this._googleMapsHelper.convertToNumber(value, null);
        this.updateCenter();
    }

    get longitude(): number {
        return this._longitude;
    }

    @Input()
    set latitude(value: number) {
        this._latitude = this._googleMapsHelper.convertToNumber(value, null);
        this.updateCenter();
    }

    get latitude(): number {
        return this._latitude;
    }

    private updateCenter() {
        if (this._circle) {
            this._circle.setCenter(
                {
                    lat: this.latitude,
                    lng: this.longitude
                }
            )
        }
    }

    @Input()
    set draggable(value: boolean) {
        this._draggable = value;
        if (this._circle) {
            this._circle.setDraggable(value);
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
        if (this._circle) {
            this._circle.setEditable(value);
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
    set fillOpacity(value: number | string) {
        this._fillOpacity = this._googleMapsHelper.convertToNumber(
            value, null);
        this.updateOptions();
    }

    get fillOpacity(): number | string {
        return this._fillOpacity;
    }

    @Input()
    set radius(value: number) {
        this._radius = this._googleMapsHelper.convertToNumber(value, null);
        if (this._circle) {
            this._circle.setRadius(value);
        }
    }

    get radius(): number {
        return this._radius;
    }

    @Input()
    set strokeColor(value: string) {
        this._strokeColor = value;
        this.updateOptions();
    }

    get strokeColor(): string {
        return this._strokeColor;
    }

    @Input()
    set fillColor(value: string) {
        this._fillColor = value;
        this.updateOptions();
    }

    get fillColor(): string {
        return this._fillColor;
    }

    get options(): Object {
        return {
            strokeColor: this.strokeColor,
            strokeOpacity: this.strokeOpacity,
            strokeWeight: this.strokeWeight,
            fillColor: this.fillColor,
            fillOpacity: this.fillOpacity,
            draggable: this.draggable,
            center: { lat: this.latitude, lng: this.longitude },
            radius: this.radius
        }
    }

    private updateOptions() {
        if (this._circle) {
            this._circle.setOptions(<google.maps.CircleOptions>this.options);
        }
    }

    private addEventListeners() {
        let events: any[] = [
            { name: 'click', emitter: this.circleClick },
            { name: 'dragend', emitter: this.dragDone }
        ];
        events.forEach((e: Event) => {
            this._circle.addListener(e['name'], (event: google.maps.MouseEvent) => this._zone.run(() => e['emitter'].next({
                self: this, coords: {
                    x: event['pixel'].x,
                    y: event['pixel'].y,
                    lat: event.latLng.lat(),
                    lng: event.latLng.lng()
                }
            })));
        });
    }
}
