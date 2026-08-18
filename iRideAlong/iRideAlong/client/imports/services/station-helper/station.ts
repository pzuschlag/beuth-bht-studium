export class Station {

    private _id: string;
    private _name: string;
    private _lat: number;
    private _lng: number;
    private _active: boolean;
    private _radius: number;
    private _dist: number;

    /**
     * constructor - description
     *
     * @param  {Object} value - Object with entries: id, name, lat, lng, active
     * @return {Station} Station object
     */
    constructor(value: Object) {
        this._id = value['id'];
        this._name = value['name'];
        this._lat = this.convertToNumber(value['lat']);
        this._lng = this.convertToNumber(value['lng']);
        this.active = this.convertToBoolean(value['active'] || '0');
        this._radius = this.convertToNumber(value['radius'] || 50);
    }

    /**
     * get id - get the id string
     *
     * @return {string} the id
     */
    get id(): string {
        return this._id;
    }
    /**
     * get lat - get the lat number
     *
     * @return {number} the latitude
     */
    get lat(): number {
        return this._lat;
    }
    /**
     * get lng - get the lng number
     *
     * @return {number} the longitude
     */
    get lng(): number {
        return this._lng;
    }
    /**
     * get radius - get the radius number
     *
     * @return {number} the radius
     */
    get radius(): number {
        return this._radius;
    }
    /**
     * get name - get the name string
     *
     * @return {string} the name
     */
    get name(): string {
        return this._name;
    }
    /**
     * get active - get the active boolean
     *
     * @return {boolean} the active value
     */
    get active(): boolean {
        return this._active;
    }
    /**
     * set active - set the active value
     *
     * @param  {boolean} active - value of the active prop
     */
    set active(active: boolean) {
        this._active = active;
    }

    /**
     * get dist - get the dist number
     *
     * @return {boolean} the dist value
     */
    get dist(): number {
        return this._dist;
    }
    /**
     * set dist - set the dist value
     *
     * @param  {number} dist - value of the dist prop
     */
    set dist(dist: number) {
        this._dist = dist;
    }

    /**
     * private convertToNumber - convert string | number to number
     *
     * @param  {string | number} value - the value to convert
     * @return {number} the converted number or NaN
     */
    private convertToNumber(value: string | number): number {
        if (typeof value === 'string') {
            return parseFloat(value);
        } else if (typeof value === 'number') {
            return <number>value;
        }
        return NaN;
    }

    /**
     * private convertToBoolean - convert string | number to boolean
     *
     * @param  {string | number} value - the value to convert
     * @return {boolean} the converted boolean or null
     */
    private convertToBoolean(value: string | number): boolean {
        if (typeof value === 'string') {
            return Boolean(parseInt(value));
        } else if (typeof value === 'number') {
            return Boolean(<number>value);
        }
        return null;
    }
}
