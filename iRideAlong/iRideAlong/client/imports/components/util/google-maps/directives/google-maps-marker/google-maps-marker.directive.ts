import {Directive, Input, Output, EventEmitter, NgZone, OnDestroy, OnInit} from '@angular/core';
import {GoogleMapsHelperService, GoogleMapsWrapperService} from "../../services";
import * as fontawesome from 'fontawesome-markers';

@Directive({
    selector: 'google-maps-marker',
    providers: []
})
export class GoogleMapsMarkerDirective implements OnDestroy, OnInit {

    private _longitude: number = 0;
    private _latitude: number = 0;
    private _iconUrl: string;
    private _title: string;
    private _label: string;
    private _draggable: boolean = false;
    private _clickable: boolean = true;
    private _id: string;
    private _icon: Object;

    private _field: string;

    private _marker: google.maps.Marker;

    @Output() markerClick: EventEmitter<any> = new EventEmitter<any>();
    @Output() dragDone: EventEmitter<any> = new EventEmitter<any>();
    @Output() dragging: EventEmitter<any> = new EventEmitter<any>();

    constructor(
        private _googleMapsWrapper: GoogleMapsWrapperService,
        private _googleMapsHelper: GoogleMapsHelperService,
        private _zone: NgZone
    ) {
    }

    ngOnInit() {
        this._googleMapsWrapper.createMarker(this.options).then((marker: google.maps.Marker) => {
            this._marker = marker
            this.addEventListeners();
        });
    }

    ngOnDestroy() {
        this._marker.setMap(null);
    }

    @Input()
    set title(value: string) {
        this._title = value;
        if (this._marker) {
            this._marker.setTitle(value);
        }
    };

    get title(): string {
        return this._title;
    }

    @Input()
    set field(index: string) {
        this._field = index;
    }

    get field(): string {
        return this._field;
    }

    @Input()
    set id(id: string) {
        this._id = id;
    }

    get id(): string {
        return this._id;
    }

    @Input()
    set label(value: string) {
        this._label = value;
        if (this._marker) {
            this._marker.setLabel(value);
        }
    };

    get label(): string {
        return this._label;
    }

    @Input()
    set draggable(value: boolean) {
        this._draggable = value;
        if (this._marker) {
            this._marker.setDraggable(value);
        }
    };

    get draggable(): boolean {
        return this._draggable;
    }

    @Input()
    set clickable(value: boolean) {
        this._clickable = value;
        if (this._marker) {
            this._marker.setClickable(value);
        }
    };

    get clickable(): boolean {
        return this._clickable;
    }

    @Input()
    set iconUrl(value: string) {
        this._iconUrl = value;
        this.updateIcon();
    }

    get iconUrl(): string {
        return this._iconUrl;
    }

    @Input()
    set icon(value: Object) {
        this._icon = {
            path: fontawesome[value['name']],
            scale: value['scale'] || 0.5,
            strokeWeight: value['strokeWeight'] || 0.8,
            strokeColor: value['strokeColor'] || 'white',
            strokeOpacity: value['strokeOpacity'] || 1,
            fillColor: value['fillColor'] || '#F44336',
            fillOpacity: value['fillOpacity'] || 1,
        };
        this.updateIcon();
    }

    get icon(): Object {
        return this._icon;
    }

    private updateIcon() {
        if (this._marker) {
            this._marker.setIcon(this.icon || this.iconUrl);
        }
    }

    @Input()
    set longitude(value: number) {
        this._longitude = this._googleMapsHelper.convertToNumber(value, null);
        this.updatePosition();
    }

    get longitude(): number {
        return this._longitude;
    }

    @Input()
    set latitude(value: number) {
        this._latitude = this._googleMapsHelper.convertToNumber(value, null);
        this.updatePosition();
    }

    get latitude(): number {
        return this._latitude;
    }

    private updatePosition() {
        if (this._marker) {
            this._marker.setPosition({
                lat: this.latitude,
                lng: this.longitude
            });
        }
    }

    get options(): Object {
        return {
            clickable: this.clickable,
            draggable: this.draggable,
            icon: this.icon || this.iconUrl,
            label: this.label,
            position: { lat: this.latitude, lng: this.longitude },
            title: this.title
        }
    }

    private updateOptions(value: Object) {
        if (this._marker) {
            this._marker.setOptions(<google.maps.MarkerOptions>this.options);
        }
    }

    private addEventListeners() {
        let events: any[] = [
            { name: 'click', emitter: this.markerClick },
            { name: 'dragend', emitter: this.dragDone },
            { name: 'dragging', emitter: this.dragging },
        ];
        events.forEach((e: Event) => {
            this._marker.addListener(e['name'], (event: google.maps.MouseEvent) => this._zone.run(() => {
                e['emitter'].next({
                    self: this, coords: {
                        screenX: event['Ob'] ? event['Ob'].screenX : null,
                        screenY: event['Ob'] ? event['Ob'].screenY : null,
                        lat: event.latLng.lat(),
                        lng: event.latLng.lng()
                    }
                })
            }));
        });
    }
}
