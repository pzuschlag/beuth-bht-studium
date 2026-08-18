import * as GeoJSON from 'geojson';
import * as togpx from 'togpx';
import * as tokml from 'tokml';

export namespace geo {

    export const WAYPOINT = 'waypoint';
    export const START = 'start';
    export const END = 'end';

    export class Route {

        private _waypoints: geo.Point[] = [];
        private _name: string;
        private _author: string;
        private _desc: string;
        private _id: string;
        private _pointIds: string[];
        private _time: string;

        constructor(values?: {}) {
            if (values) {
                this._id = values['_id'];
                this._name = values['_name'];
                this._author = values['_author'];
                this._desc = values['_desc'];
                this._pointIds = values['_pointIds'];
                this._time = values['_time'];
                for (let waypoint of values['_waypoints']) {
                    this._waypoints.push(new geo.Point(waypoint));
                }
            }
            GeoJSON.defaults = {
                'Point': ['lat', 'lng'],
                'LineString': 'waypoints',
                include: ['icn', 'name', 'field', 'pressure', 'img', 'time']
            };
        }

        get id(): string {
            return this._id;
        }
        set id(value: string) {
            this._id = value;
        }
        get author(): string {
            return this._author;
        }
        set author(value: string) {
            this._author = value;
        }
        get desc(): string {
            return this._desc;
        }
        set desc(value: string) {
            this._desc = value;
        }
        get time(): string {
            return this._time;
        }
        set time(value: string) {
            this._time = value;
        }
        get pointIds(): string[] {
            return this._pointIds;
        }
        set pointIds(value: string[]) {
            this._pointIds = value;
        }
        set name(value: string) {
            this._name = value;
        }
        get name(): string {
            return this._name;
        }
        set start(point: geo.Point) {
            for (let i = 0; i < this.waypoints.length; i++) {
                if (this.waypoints[i].field === START) {
                    this.waypoints[i] = point;
                    return;
                }
            }
            this.waypoints.unshift(point);
        }
        get start(): geo.Point {
            for (let waypoint of this.waypoints) {
                if (waypoint.field === START) {
                    return waypoint;
                }
            }
            return null;
        }
        set end(point: geo.Point) {
            for (let i = 0; i < this.waypoints.length; i++) {
                if (this.waypoints[i].field === END) {
                    this.waypoints[i] = point;
                    return;
                }
            }
            this.waypoints.push(point);
        }

        get end(): geo.Point {
            for (let waypoint of this.waypoints) {
                if (waypoint.field === END) {
                    return waypoint;
                }
            }
            return null;
        }

        get waypoints(): geo.Point[] {
            return this._waypoints;
        }

        set waypoints(value: geo.Point[]) {
            this._waypoints = value;
        }

        get geoJSON(): any {
            // https://www.npmjs.com/package/geojson
            let line: any[] = [];
            let points: any[] = [];
            for (let point of this.waypoints) {
                line.push([point.lng, point.lat])
                points.push({
                    lat: point.lat,
                    lng: point.lng,
                    name: point.name,
                    field: point.field,
                    icn: point.icn,
                    time: point.time,
                    pressure: point.pressure,
                    img: point.img
                })
            }
            let tmp = line ? {
                waypoints: line,
                name: this.name,
                field: 'waypoints',
                icn: 'waypoints'
            } : [];
            return GeoJSON.parse(points.concat(tmp), {});
        }

        public addToRoute(field: string, name: string, lat: number, lng: number, time?: Date, pressure?: number, img?: string) {
            // let id = field;
            switch (field) {
                case START:
                    this.start = new geo.Point({
                        field: field,
                        name: name,
                        lat: lat,
                        lng: lng,
                        time: time,
                        pressure: pressure,
                        img: img
                    });
                    break;
                case END:
                    this.end = new geo.Point({
                        field: field,
                        name: name,
                        lat: lat,
                        lng: lng,
                        time: time,
                        pressure: pressure,
                        img: img
                    });
                    break;
                case WAYPOINT:
                    this.waypoints.splice(
                        this.end ? this.waypoints.length - 1 : this.waypoints.length,
                        0, new geo.Point({
                            field: field,
                            name: name,
                            lat: lat,
                            lng: lng,
                            time: time,
                            pressure: pressure,
                            img: img
                        }));
                    break;
            }
        }

        public set(input: geo.Point, lat: number, lng: number) {
            for (let i = 0; i < this.waypoints.length; i++) {
                if (this.waypoints[i] === input) {
                    this.waypoints[i] = new geo.Point({
                        id: input.id,
                        field: input.field,
                        name: input.name,
                        lat: lat,
                        lng: lng,
                        time: input.time,
                        pressure: input.pressure,
                        img: input.img,
                        icn: input.icn
                    });
                }
            }
        }

        public removeFromRoute(dir: any) {
            let delIdx = [];
            for (let i = 0; i < this.waypoints.length; i++) {
                let waypoint = this.waypoints[i];
                if (dir.point.lat === waypoint.lat && dir.point.lng === waypoint.lng) {
                    delIdx.push(i);
                }
            }
            for (let i of delIdx) {
                this.waypoints.splice(i, 1);
            }
        }

        public convertToGPX(opts?: Object) {
            // https://github.com/tyrasd/togpx
            return togpx(this.geoJSON, opts || {});
        }

        // public convertToKML(opts?: Object) {
        //     // https://github.com/mapbox/tokml
        //     console.log(tokml(this.geoJSON, opts));
        //     return tokml(this.geoJSON, opts);
        // }
    }

    export class Point {

        private _id: string;
        private _name: string;
        private _field: string;
        private _lat: number;
        private _lng: number;
        private _pressure: number;
        private _time: Date;
        private _icn: string;
        private _img: string;

        constructor(values: {}) {
            this.icn = values['icn'];
            this.time = values['time'] || values['_time'] || new Date();
            this.id = values['id'] || values['_id'];
            this.field = values['field'] || values['_field'];
            this.name = values['name'] || values['_name'];
            this.lat = values['lat'] || values['_lat'];
            this.lng = values['lng'] || values['_lng'];
            this.img = values['img'] || values['_img'];
            this.pressure = values['pressure'] || values['_pressure'];
        }

        get id(): string {
            return this._id;
        }
        set id(value: string) {
            this._id = value;
        }
        get pressure(): number {
            return this._pressure;
        }
        set pressure(value: number) {
            this._pressure = value;
        }
        get localTime(): string {
            return `${this._time.toLocaleDateString()} ${this._time.toLocaleTimeString()}`;
        }
        get time(): Date {
            return this._time;
        }
        set time(value: Date) {
            this._time = value;
        }
        set field(field: string) {
            if (field !== WAYPOINT && field !== START && field !== END) {
                throw new Error(`field has to be "${WAYPOINT}", "${START}" or "${END}" not "${field}"`);
            }
            this._field = field;
        }
        get field(): string {
            return this._field;
        }
        set name(value: string) {
            this._name = value;
        }
        get name(): string {
            return this._name;
        }
        set lat(value: number) {
            this._lat = value;
        }
        get lat(): number {
            return this._lat;
        }
        set lng(value: number) {
            this._lng = value;
        }
        get lng(): number {
            return this._lng;
        }
        set img(value: string) {
            this._img = value;
        }
        get img(): string {
            return this._img;
        }
        get icn(): string {
            if (this._icn) {
                return this._icn;
            } else {
                switch (this.field) {
                    case START:
                        return 'STREET_VIEW';
                    case WAYPOINT:
                        return 'TAG';
                    case END:
                        return 'FLAG_CHECKERED';
                }
            }
        }

        set icn(value: string) {
            this._icn = value;
        }

    }
}
