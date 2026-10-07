import {Injectable, OnInit, OnDestroy} from '@angular/core';
import {BehaviorSubject, Observable, Subscription} from "rxjs";

declare var require: any;
declare var window: any;

@Injectable()
export class JsonHandlerService {

    private _type: BehaviorSubject<TYPE> = new BehaviorSubject<TYPE>(undefined);
    private _ticket: BehaviorSubject<TICKETS> = new BehaviorSubject<TICKETS>(undefined);
    private _ticketProvided: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private _karmaPoints: BehaviorSubject<number> = new BehaviorSubject<number>(0);

    private _initial: boolean = true;

    constructor() {
        let jsonFile = JSON.parse(window.localStorage.getItem("loc_data"));
        console.log("DATA loaded from file", jsonFile);
        if (!jsonFile) {
            this.saveFile();
        } else {
            this.type = TYPE[<string>jsonFile['type']];
            this.ticket = jsonFile['ticket'];
            this.ticketProvided = jsonFile['ticketProvided'] || false;
            this.karmaPoints = jsonFile['karmaPoints'] || 0;
            this.saveFile();
        }
    }

    public set type(type: TYPE) {
        if (type && (type !== TYPE.passenger && type !== TYPE.provider)) {
            throw Error(`Type has to be an enum of TYPE`);
        }
        this._type.next(type); // write type to the Subject
    }

    public get type(): TYPE {
        return this._type.getValue();
    }

    public get typeString(): string {
        return TYPE[this.type];
    }

    public get type$(): Observable<TYPE> {
        return this._type.asObservable();
    }

    public set ticket(value: TICKETS) {
        this._ticket.next(value); // write ticket to the Subject
    }

    public get ticket(): TICKETS {
        return this._ticket.getValue();
    }

    public get ticket$(): Observable<TICKETS> {
        return this._ticket.asObservable();
    }

    public set ticketProvided(value: boolean) {
        this._ticketProvided.next(value); // write ticketProvided to the Subject
    }

    public get ticketProvided(): boolean {
        return this._ticketProvided.getValue();
    }

    public get ticketProvided$(): Observable<boolean> {
        return this._ticketProvided.asObservable();
    }

    public set karmaPoints(points: number) {
        this._karmaPoints.next(points); // write karmaPoints to the Subject
    }

    public get karmaPoints(): number {
        return this._karmaPoints.getValue();
    }

    public get karmaPoints$(): Observable<number> {
        return this._karmaPoints.asObservable();
    }

    public get propertiesJSON() {
        return {
            type: this.typeString,
            ticket: this.ticket,
            ticketProvided: this.ticketProvided,
            karmaPoints: this.karmaPoints
        };
    }

    //________________save the file________________________
    public saveFile(): void {
        window.localStorage.setItem("loc_data", JSON.stringify(this.propertiesJSON));
    }

    public reset() {
        this.type = undefined;
        this.ticket = undefined;
        this.ticketProvided = false;
        this.saveFile();
    }
}

export class TICKETS {
    static UMWELTKARTE: string = 'VBB-Umweltkarte';
    static AUSWEIS: string = 'Schwerbehindertenausweis';
}
export enum TYPE { passenger, provider };
