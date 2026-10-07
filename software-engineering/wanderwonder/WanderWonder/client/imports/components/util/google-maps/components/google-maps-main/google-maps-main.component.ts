import {Component, ElementRef, Input, Output, EventEmitter} from '@angular/core';
import {GoogleMapsHelperService, GoogleMapsWrapperService} from "../../services";

@Component({
    selector: 'google-maps',
    template: '<div id="maps-wrapper"></div>', // OR html in file:
    styles: ['#maps-wrapper {height: 100%;width: inherit;}'],
    providers: []
})

/**
 * Main google maps component - inserts the google-maps in the div wrapper
 * inherits the width of the parent component and takes 100% height.
 *
 */
export class GoogleMapsMainComponent {

    private _longitude: number = 52.507629;
    private _latitude: number = 13.1449572;
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

    // Output events which can be subscribed to in the google-maps-wrapper component
    @Output() mapClick: EventEmitter<MouseEvent>
    = new EventEmitter<MouseEvent>();
    @Output() mapRightClick: EventEmitter<MouseEvent>
    = new EventEmitter<MouseEvent>();
    @Output() mapDblClick: EventEmitter<MouseEvent>
    = new EventEmitter<MouseEvent>();
    @Output() centerChange: EventEmitter<google.maps.LatLngLiteral>
    = new EventEmitter<google.maps.LatLngLiteral>();
    @Output() zoomChanged: EventEmitter<number>
    = new EventEmitter<number>();

    constructor(
        private _elem: ElementRef,
        private _googleMapsWrapper: GoogleMapsWrapperService,
        private _googleMapsHelper: GoogleMapsHelperService
    ) {
    }

    /**
     * ngOnInit - is called when DOM is ready
     *            creates the map in native elements first child with the
     *            supplied options, adds the event listeners
     */
    ngOnInit() {
        this._googleMapsWrapper.createMap(
            this._elem.nativeElement.firstChild, this.options);
        this.handleMapCenterChange();
        this.handleMapZoomChange();
        this.handleMapMouseEvents();
    }


    /**
     * ngOnDestroy - is called when the component is destroyed
     *               clears the listeners on map for memory management
     */
    ngOnDestroy() {
        console.log("maps main destroyed");
        this._googleMapsWrapper.clearListener();
    }


    /**
     * set longitude - Input() Setter method, converts the passed parameter to
     *                 a number sets as longitude and updates the center of the
     *                 map
     *
     * @param  { number | string } value: the new value for longitude
     */
    @Input()
    set longitude(value: number | string) {
        this._longitude = this._googleMapsHelper.convertToNumber(value, null);
        this.updateCenter();
    }

    /**
     * get longitude - Getter method
     *
     * @return {number | string}  longitude
     */
    get longitude(): number | string {
        return this._longitude;
    }

    /**
     * set latitude - Input() Setter method, converts the passed parameter to
     *                a number sets as latitude and updates the center of the
     *                map
     *
     * @param  { number | string } value: the new value for latitude
     */
    @Input()
    set latitude(value: number | string) {
        this._latitude = this._googleMapsHelper.convertToNumber(value, null);
        this.updateCenter();
    }

    /**
     * get latitude - Getter method
     *
     * @return {number | string} latitude
     */
    get latitude(): number | string {
        return this._latitude;
    }

    /**
     * set zoom - Input() Setter method, converts the passed parameter to a
     *            number, validates it, sets as zoom and updates the zoom of
     *            the map
     *
     * @param {number | string } value: the new value for zoom
     */
    @Input()
    set zoom(value: number | string) {
        this._zoom = this._googleMapsHelper.convertToNumber(value, null);
        if (typeof this._zoom === 'number') {
            this._googleMapsWrapper.setZoom(this._zoom);
        }
    }

    /**
     * get zoom - Getter method
     *
     * @return {number | string}  zoom
     */
    get zoom(): number | string {
        return this._zoom;
    }

    /**
     * set mapTypeId - Input() Setter method, sets the passed parameter as
     *                 value and updates the map with the new mapType
     *
     * @param {string} value: the new value for mapTypeId
     */
    @Input()
    set mapTypeId(value: string) {
        this._mapTypeId = value;
        this._googleMapsWrapper.setMapTypeId(value);
    }

    /**
     * get mapTypeId - Getter method
     *
     * @return {string} mapTypeId
     */
    get mapTypeId(): string {
        return this._mapTypeId;
    }

    /**
     * set tilt - Input() Setter method, sets the passed parameter as value
     *            and updates the map with the new tilt
     *
     * @param {number} value: the new value for tilt
     */
    @Input()
    set tilt(value: number) {
        this._tilt = value;
        this._googleMapsWrapper.setTilt(value);
    }

    /**
     * get tilt - Getter method
     *
     * @return {number}  tilt
     */
    get tilt(): number {
        return this._tilt;
    }

    /**
     * set heading - Input() Setter method, sets the passed parameter as value
     *               and updates the map with the new heading
     *
     * @param {number} value: the new value for heading
     */
    @Input()
    set heading(value: number) {
        this._heading = value;
        this._googleMapsWrapper.setHeading(value);
    }

    /**
     * get heading - Getter method
     *
     * @return {number}  heading
     */
    get heading(): number {
        return this._heading;
    }

    /**
     * set disableDoubleClickZoom - Input() Setter method, sets the passed
     *                              parameter as value and updates the map with
     *                              the new options
     *
     * @param {boolean} value: the new value for disableDoubleClickZoom
     */
    @Input()
    set disableDoubleClickZoom(value: boolean) {
        this._disableDoubleClickZoom = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get disableDoubleClickZoom - Getter method
     *
     * @return {boolean}  disableDoubleClickZoom
     */
    get disableDoubleClickZoom(): boolean {
        return this._disableDoubleClickZoom;
    }

    /**
     * set disableDefaultUI - Input() Setter method, sets the passed parameter
     *                        as value and updates the map with the new options
     *
     * @param {boolean} value: the new value for disableDefaultUI
     */
    @Input()
    set disableDefaultUI(value: boolean) {
        this._disableDefaultUI = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get disableDefaultUI - Getter method
     *
     * @return {boolean}  disableDefaultUI
     */
    get disableDefaultUI(): boolean {
        return this._disableDefaultUI;
    }

    /**
     * set scrollwheel - Input() Setter method, sets the passed parameter
     *                   as value and updates the map with the new options
     *
     * @param {boolean} value: the new value for scrollwheel
     */
    @Input()
    set scrollwheel(value: boolean) {
        this._scrollwheel = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get scrollwheel - Getter method
     *
     * @return {boolean}  scrollwheel
     */
    get scrollwheel(): boolean {
        return this._scrollwheel;
    }

    /**
     * set draggableCursor - Input() Setter method, sets the passed parameter
     *                       as value and updates the map with the new options
     *
     * @param {string} value: the new value for draggableCursor
     */
    @Input()
    set draggableCursor(value: string) {
        this._draggableCursor = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get draggableCursor - Getter method
     *
     * @return {string}  draggableCursor
     */
    get draggableCursor(): string {
        return this._draggableCursor;
    }

    /**
     * set draggingCursor - Input() Setter method, sets the passed parameter
     *                      as value and updates the map with the new options
     *
     * @param {string} value: the new value for draggingCursor
     */
    @Input()
    set draggingCursor(value: string) {
        this._draggingCursor = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get draggingCursor - Getter method
     *
     * @return {string}  draggingCursor
     */
    get draggingCursor(): string {
        return this._draggingCursor;
    }

    /**
     * set keyboardShortcuts - Input() Setter method, sets the passed parameter
     *                         as value and updates the map with the new options
     *
     * @param {boolean} value: the new value for keyboardShortcuts
     */
    @Input()
    set keyboardShortcuts(value: boolean) {
        this._keyboardShortcuts = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get keyboardShortcuts - Getter method
     *
     * @return {boolean}  keyboardShortcuts
     */
    get keyboardShortcuts(): boolean {
        return this._keyboardShortcuts;
    }

    /**
     * set zoomControl - Input() Setter method, sets the passed parameter
     *                   as value and updates the map with the new options
     *
     * @param {boolean} value: the new value for zoomControl
     */
    @Input()
    set zoomControl(value: boolean) {
        this._zoomControl = value;
        this._googleMapsWrapper.setMapOptions(this.options);
    }

    /**
     * get zoomControl - Getter method
     *
     * @return {boolean}  zoomControl
     */
    get zoomControl(): boolean {
        return this._zoomControl;
    }

    /**
     * get options - Getter method, returns all map options in one Object
     *
     * @return {Object}  options
     */
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


    /**
     * private updateCenter - uses ternary operator to validate lat lng
     *                        and sets the new center of the map
     */
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

    /**
     * private handleMapCenterChange - subscribes to center_changed map event
     *                                 and emits the new lat lng to the
     *                                 centerChange Output() EventEmitter
     */
    private handleMapCenterChange() {
        this._googleMapsWrapper.subscribeToMapEvent<void>(
            'center_changed').subscribe(() => {
                this._googleMapsWrapper.getCenter().then(
                    (center: google.maps.LatLng) => {
                        this._latitude = center.lat();
                        this._longitude = center.lng();
                        this.centerChange.emit(
                            <google.maps.LatLngLiteral>{
                                lat: this._latitude, lng: this._longitude
                            }
                        );
                    });
            });
    }

    /**
     * private handleMapZoomChange - subscribes to zoom_changed map event
     *                               and emits the new zoom to the zoomChanged
     *                               Output() EventEmitter
     */
    private handleMapZoomChange() {
        this._googleMapsWrapper.subscribeToMapEvent<void>(
          'zoom_changed').subscribe(() => {
            this._googleMapsWrapper.getZoom().then((z: number) => {
                this._zoom = z;
                this.zoomChanged.emit(z);
            });

        });
    }

    /**
     * private handleMapMouseEvents - subscribes to click and rightclick map
     *                                event and emits 'this', the event pixel
     *                                and lat|lng coords the mapClick or
     *                                mapRightClick Output() EventEmitter
     */
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
                            x: event['pixel'].x,
                            y: event['pixel'].y,
                            lat: event.latLng.lat(),
                            lng: event.latLng.lng()
                        }
                    });
                });
        });
    }
}
