import {Toasts} from "../global-helper-service/global-helper-service";
import {JsonHandlerService} from "../json-handler/json-handler.service";
import {TYPE} from "../json-handler/json-handler.service";
import {StationHelperService} from "../station-helper/station-helper.service";
import {Injectable, OnInit, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subject, Observable, Subscription} from "rxjs";
import * as io from 'socket.io-client';
import {Meteor} from 'meteor/meteor';

declare var process: any;

const PORT = (<any>window).socketPort || 3030;

@Injectable()
export class RedisPubSubService {
    // http://redis.js.org/
    private _messageObject: Subject<Object> = new Subject<Object>();
    private _privateMessageObject: Subject<Object> = new Subject<Object>();

    private _socket: SocketIOClient.Socket;

    private _obs: Observable<any>;

    private _id: string;
    private _type: TYPE;

    /**
     * constructor - create the RedisPubSubserver, connect to socket, setup listeners
     *
     * @param  {StationHelperService} private _stationHelperService: StationHelperService
     *                                Instance of singleton stationHelper
     * @return {RedisPubSubService}
     */
    constructor(private _stationHelper: StationHelperService, private _jsonHandler: JsonHandlerService) {
        let sub = this._jsonHandler.type$.subscribe((type: TYPE) => {
            if (this._type === undefined && type !== undefined) {
                this._type = type;
                this.setupSocketIO();
            } else if (type === undefined) {
                this._type = type;
                if (this._socket) {
                    let temp = this._socket;
                    this._socket = undefined;
                    temp.disconnect();
                }
            }
        })
    }

    private setupSocketIO() {
        // XXX only for devel
        let url;
        // url = `http://localhost:${PORT}`;
        this._socket = io(url || `http://54.93.73.9:${PORT}`, { query: `type=${this._type}` });
        this.onGlobalNotifications();

        // get id from initial message
        this._socket.on('initial', (message) => {
            this._id = message.data;
            console.log(`CONNECTED ${this._id} : ${TYPE[this._type]}`);
        });

        // update the initial stations status from server data
        this._socket.on('station-status', (message) =>
            this._stationHelper.initialStatus = message.data);

        this.onSubUpdate();
        this.onConnect();
        this.onMessage();
        this.onPrivateMessage();
    }


    /** get messageObject$ - get the Observable of the messageObject subject
     *
     *  @return {Observable<Object>} the Observable of the messageObject subject
     */
    get messageObject$(): Observable<Object> {
        return this._messageObject.asObservable();
    }

    /**
     * set messageObject - set next messageObject Subject value
     *
     * @param  {Object} value Object with values of messageObject
     */
    set messageObject(value: Object) {
        this._messageObject.next(value);
    }

    /** get messageObject$ - get the Observable of the messageObject subject
     *
     *  @return {Observable<Object>} the Observable of the messageObject subject
     */
    get privateMessageObject$(): Observable<Object> {
        return this._privateMessageObject.asObservable();
    }

    /**
     * set messageObject - set next messageObject Subject value
     *
     * @param  {Object} value Object with values of messageObject
     */
    set privateMessageObject(value: Object) {
        this._privateMessageObject.next(value);
    }

    /**
     * private - description
     *
     * @return {type}  description
     */
    private onGlobalNotifications() {
        // Global events are bound against socket
        this._socket.on('connect_failed', () =>
            Toasts.connectionError()
        );
        this._socket.on('disconnect', () => {
            if (this._socket) {
                Toasts.disconnect();
                this.setupSocketIO();
            }
        });
    }


    /**
     * private onSubUpdate - listener for subscription updates
     */
    private onSubUpdate() {
        this._socket.on('subscriptions', (message) => {
            if (message.data) {
                this._stationHelper.status = message.data;
            }
        });
    }

    /**
     * private onConnect - listener for connection confirmation
     */
    private onConnect() {
        this._socket.on('connect', () =>
            console.log("Socket Connected")
        );
    }

    /**
     * private onMessage - listener for message event
     */
    private onMessage() {
        this._socket.on('message', (message) =>
            this.messageObject = message.data);
    }

    /**
     * private onMessage - listener for message event
     */
    private onPrivateMessage() {
        this._socket.on('private-message', (message) =>
            this.privateMessageObject = message.data);
    }

    /**
     * public subscribe - emit subscriptions event√
     *
     * @param  {string} channel - the channel to be subscribed
     */
    public subscribe(channel: string, callback) {
        channel ?
            this._socket.emit('subscribe', channel, callback, callback) :
            callback('channel undefined');
    }

    /**
     * public subscribe - emit unsubscriptions event
     *
     * @param  {string} channel - the channel to be unsubscribed
     */
    public unsubscribe(channel: string, callback) {
        channel ?
            this._socket.emit('unsubscribe', channel, callback, callback) :
            callback('channel undefined');
    }

    /**
     * public publish - emit publish event
     *
     * @param  {string} channel - the channel to published to
     * @param  {string} message - the message to send
     */
    public publish(channel: string, message: string, callback) {
        channel && message ?
            this._socket.emit('publish', { channel: channel, message: message }, callback, callback) :
            callback('channel and/or message undefined', channel, message);
    }

    /**
     * public publish - emit send-to event
     *
     * @param  {string} id - the id to send to
     * @param  {string} message - the message to send
     */
    public sendTo(id: string, message: string, channel: string, callback) {
        id && channel && message ?
            this._socket.emit('send-to', { id: id, message: message, channel: channel }, callback, callback) :
            callback('id, channel and/or message undefined', id, channel, message);
    }

}
