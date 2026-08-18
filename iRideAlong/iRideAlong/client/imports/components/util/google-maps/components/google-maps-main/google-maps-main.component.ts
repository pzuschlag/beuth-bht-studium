import {Component, ElementRef, Input, Output, EventEmitter} from '@angular/core';
import {GoogleMapsHelperService, GoogleMapsWrapperService} from "../../services";

@Component({
    selector: 'google-maps',
    template: '<div id="maps-wrapper"></div>', // OR html in file:
    styles: ['#maps-wrapper {height: 100%;width: inherit;}'],
    providers: []
})
export class GoogleMapsMainComponent {

    private _longitude: number = 0;
    private _latitude: number = 0;
    private _zoom: number = 8;
    private _mapTypeId: string;
    private _tilt: number = 0;
    private _heading: number = 0;
    private _disableDoubleClickZoom: boolean = false;
    private _disableDefaultUI: boolean = false;
    private _scrollwheel: boolean = true;
    private _draggableCursor: string;
    private _draggingCursor: string;
    private _keyboardShortcuts: boolean = true;
    private _zoomControl: boolean = true;


    @Output() mapClick: EventEmitter<MouseEvent> = new EventEmitter<MouseEvent>();
    @Output() mapRightClick: EventEmitter<MouseEvent> = new EventEmitter<MouseEvent>();
    @Output() mapDblClick: EventEmitter<MouseEvent> = new EventEmitter<MouseEvent>();
    @Output() centerChange: EventEmitter<google.maps.LatLngLiteral> = new EventEmitter<google.maps.LatLngLiteral>();
    @Output() boundsChange: EventEmitter<Object> = new EventEmitter<Object>();
    @Output() zoomChanged: EventEmitter<number> = new EventEmitter<number>();

    constructor(
        private _elem: ElementRef,
        private _googleMapsWrapper: GoogleMapsWrapperService,
        private _googleMapsHelper: GoogleMapsHelperService
    ) {
    }

    ngOnInit() {
        this._googleMapsWrapper.createMap(
            this._elem.nativeElement.firstChild, this.options);
        this.handleMapCenterChange();
        this.handleMapZoomChange();
        this.handleMapMouseEvents();
        this.handleMapBoundsChange();
    }

    ngOnDestroy() {
        this._googleMapsWrapper.clearListener();
    }

    @Input()
    set longitude(value: number | string) {
        if (value) {
            this._longitude = this._googleMapsHelper.convertToNumber(value, null);
            this.updateCenter();
        }
    }

    get longitude(): number | string {
        return this._longitude;
    }

    @Input()
    set latitude(value: number | string) {
        if (value) {
            this._latitude = this._googleMapsHelper.convertToNumber(value, null);
            this.updateCenter();
        }
    }

    get latitude(): number | string {
        return this._latitude;
    }

    @Input()
    set zoom(value: number | string) {
        this._zoom = this._googleMapsHelper.convertToNumber(value, null);
        if (typeof this._zoom === 'number') {
            this._googleMapsWrapper.setZoom(this._zoom);
        }
    }

    get zoom(): number | string {
        return this._zoom;
    }

    @Input()
    set mapTypeId(value: string) {
        this._mapTypeId = value;
        this._googleMapsWrapper.setMapTypeId(value);
    }

    get mapTypeId(): string {
        return this._mapTypeId;
    }

    @Input()
    set tilt(value: number) {
        this._tilt = value;
        this._googleMapsWrapper.setTilt(value);
    }

    get tilt(): number {
        return this._tilt;
    }

    @Input()
    set heading(value: number) {
        this._heading = value;
        this._googleMapsWrapper.setHeading(value);
    }

    get heading(): number {
        return this._heading;
    }

    @Input()
    set disableDoubleClickZoom(value: boolean) {
        this._disableDoubleClickZoom = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get disableDoubleClickZoom(): boolean {
        return this._disableDoubleClickZoom;
    }

    @Input()
    set disableDefaultUI(value: boolean) {
        this._zoomControl = !value;
        this._disableDefaultUI = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get disableDefaultUI(): boolean {
        return this._disableDefaultUI;
    }

    @Input()
    set scrollwheel(value: boolean) {
        this._scrollwheel = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get scrollwheel(): boolean {
        return this._scrollwheel;
    }

    @Input()
    set draggableCursor(value: string) {
        this._draggableCursor = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get draggableCursor(): string {
        return this._draggableCursor;
    }

    @Input()
    set draggingCursor(value: string) {
        this._draggingCursor = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get draggingCursor(): string {
        return this._draggingCursor;
    }

    @Input()
    set keyboardShortcuts(value: boolean) {
        this._keyboardShortcuts = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get keyboardShortcuts(): boolean {
        return this._keyboardShortcuts;
    }

    @Input()
    set zoomControl(value: boolean) {
        this._zoomControl = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    get zoomControl(): boolean {
        return this._zoomControl;
    }

    get options(): Object {
        return {
            center: { lat: <number>this.latitude, lng: <number>this.longitude },
            zoom: <number>this.zoom,
            disableDefaultUI: this.disableDefaultUI,
            disableDoubleClickZoom: this.disableDoubleClickZoom,
            scrollwheel: this.scrollwheel,
            draggableCursor: this.draggableCursor,
            draggingCursor: this.draggingCursor,
            keyboardShortcuts: this.keyboardShortcuts,
            zoomControl: this.zoomControl,
            tilt: this.tilt
        }
    }
    private updateCenter() {
        typeof this._latitude !== 'number' || typeof this._longitude !== 'number' ?
            null :
            this._googleMapsWrapper.setCenter(
                <google.maps.LatLngLiteral>{
                    lat: this._latitude,
                    lng: this._longitude
                }
            );
    }

    private handleMapCenterChange() {
        this._googleMapsWrapper.subscribeToMapEvent<void>('center_changed').subscribe(() => {
            this._googleMapsWrapper.getCenter().then((center: google.maps.LatLng) => {
                this._latitude = center.lat();
                this._longitude = center.lng();
                this.centerChange.emit(<google.maps.LatLngLiteral>{ lat: this._latitude, lng: this._longitude });
            });
        });
    }

    private handleMapBoundsChange() {
        this._googleMapsWrapper.subscribeToMapEvent<void>('bounds_changed').subscribe(() => {
            this._googleMapsWrapper.getBounds().then((bounds: google.maps.LatLngBounds) => {
                this.boundsChange.emit({ bounds: bounds, zoom: this.zoom });
            });
        });
    }

    private handleMapZoomChange() {
        this._googleMapsWrapper.subscribeToMapEvent<void>('zoom_changed').subscribe(() => {
            this._googleMapsWrapper.getZoom().then((z: number) => {
                this._zoom = z;
                this.zoomChanged.emit(z);
            });

        });
    }

    private handleMapMouseEvents() {
        let events: any[] = [
            { name: 'click', emitter: this.mapClick },
            { name: 'rightclick', emitter: this.mapRightClick },
        ];
        events.forEach((e: Event) => {
            this._googleMapsWrapper.subscribeToMapEvent(e['name']).subscribe(
                (event: google.maps.MouseEvent) => {
                    e['emitter'].emit({
                        self: this, coords: {
                            lat: event.latLng.lat(),
                            lng: event.latLng.lng()
                        }
                    });
                });
        });
    }
}
