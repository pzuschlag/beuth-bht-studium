export declare namespace geo {
    const WAYPOINT: string;
    const START: string;
    const END: string;
    class Route {
        private _waypoints;
        private _name;
        private _waypointID;
        constructor();
        name: string;
        start: geo.Point;
        end: geo.Point;
        waypoints: geo.Point[];
        geoJSON: any;
        addToRoute(field: string, name: string, lat: number, lng: number, time?: Date, pressure?: number): void;
        set(input: any, lat: number, lng: number): void;
        removeFromRoute(dir: any): void;
        convertToGPX(opts?: Object): any;
        convertToKML(opts?: Object): any;
    }
    class Point {
        private _id;
        private _field;
        private _name;
        private _lat;
        private _lng;
        private _pressure;
        private _customIcn;
        private _time;
        constructor(_id: string, _field: string, _name: string, _lat: number, _lng: number, icn?: string, time?: Date, _pressure?: number);
        id: string;
        pressure: number;
        localTime: string;
        time: Date;
        field: string;
        name: string;
        lat: number;
        lng: number;
        icn: string;
    }
}
