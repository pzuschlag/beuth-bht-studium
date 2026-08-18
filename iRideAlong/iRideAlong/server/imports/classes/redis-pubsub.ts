import {TYPE} from '../constants';

export module RedisPubSub {

    var len: number;
    var indx;
    var tarr;
    var rPubSubIdCounter: number = 1;
    var clientStorage: RedisPubSub.ClientStorage;
    var globalSubscriptions: Object = {};
    var dbsub;
    var dbpub;

    export class Main {

        // redis.RedisClient
        constructor(_dbsub: any, _dbpub: any) {
            dbsub = _dbsub;
            dbpub = _dbpub;
            clientStorage = new RedisPubSub.ClientStorage();;
            console.log("RedisPubsub constructor");
            dbsub.on("message", this.incomingMessage);
        }

        public createClient(socket: SocketIO.Socket): RedisPubSub.Client {
            if (!clientStorage.idExists(socket.id)) {
                return new RedisPubSub.Client(socket);
            }
            return null;
        }

        public isClientSubscribedTo(id: string, channel: string): boolean {
            let client = clientStorage.getClient(id);
            if (client) {
                return client.isSubscribedTo(channel);
            }
            return false;
        }

        private incomingMessage(rawchannel: string, stringJSON: string) {
            let data = JSON.parse(stringJSON);
            let senderId = data['id'];
            let message = data['message'];
            if (globalSubscriptions[rawchannel]) {
                len = globalSubscriptions[rawchannel].length;
                console.log(`INCOMING ${senderId}: ${message} @${rawchannel}`);
                for (var i = 0; i < len; i++) {
                    let subId = globalSubscriptions[rawchannel][i].id;
                    if (senderId && senderId !== subId) {
                        clientStorage.getClient(subId)._incomingMessage(rawchannel, message, senderId);
                    }
                }
            } else {
                console.error(`INCOMING ${senderId}: ${message} @${rawchannel} | NOLISTENERS`);
            }
        }

    }

    export class Client {

        private _localSubscriptions: Array<string> = [];
        private _id: string;
        private _type: TYPE;

        constructor(private _socket: SocketIO.Socket) {
            this._id = _socket.id;
            this._type = _socket.handshake.query.type;
            console.log(`NEWCLIENT ${this._id}: ${TYPE[this._type]}`);
            clientStorage.addClient(this);
        }

        public get id(): string {
            return this._id;
        }

        public get type(): TYPE {
            return this._type;
        }

        public get socket(): SocketIO.Socket {
            return this._socket;
        }

        public get localSubscriptions(): Array<string> {
            return this._localSubscriptions;
        }

        public _incomingMessage(rawchannel: string, strMessage: string, id: string) { };

        public subscribe(channel: string, callback) {
            console.log(`SUBSCRIPTION ${this._id}: ${TYPE[this._type]} @${channel}`);
            if (!(channel in globalSubscriptions)) {
                globalSubscriptions[channel] = [this];
                dbsub.subscribe(channel);
            } else if (globalSubscriptions[channel].indexOf(this) === -1) {
                globalSubscriptions[channel].push(this);
            }
            if (this._localSubscriptions.indexOf(channel) === -1) {
                this._localSubscriptions.push(channel);
            }

            callback(globalSubscriptions);
        }

        public unsubscribe(channel: string, callback) {
            console.log(`UNSUBSCRIPTION ${this._id}: ${TYPE[this._type]} @${channel}`);
            if (channel in globalSubscriptions) {
                indx = globalSubscriptions[channel].indexOf(this);
                if (indx !== -1) {
                    globalSubscriptions[channel].splice(indx, 1);
                    if (globalSubscriptions[channel].length === 0) {
                        delete globalSubscriptions[channel];
                        dbsub.unsubscribe(channel);
                    }
                }
            }
            indx = this._localSubscriptions.indexOf(channel);
            if (indx !== -1) {
                this._localSubscriptions.splice(indx, 1);
            }
            callback(globalSubscriptions);
        }
        public onMessage(msgFn) {
            this._incomingMessage = msgFn;
        }
        public publish(channel: string, message: string, callback) {
            dbpub.publish(channel, JSON.stringify({ id: this.id, message: message }), (err) => callback(err));
        }
        public end(callback) {
            console.log(`END ${this._id}client closing subscriptions: ${this._localSubscriptions.join(',')}`);
            tarr = this._localSubscriptions.slice(0);
            len = tarr.length;
            for (var i = 0; i < len; i++) {
                this.unsubscribe(tarr[i], (res) => res);
            }
            this._localSubscriptions = [];
            clientStorage.removeClient(this._id);
            callback(globalSubscriptions);
        }

        public isSubscribedTo(channel) {
            return this._localSubscriptions.indexOf(channel) > -1;
        }
    }

    export class ClientStorage {

        private _clients: Map<string, RedisPubSub.Client> = new Map<string, RedisPubSub.Client>();

        public get clients(): Map<string, RedisPubSub.Client> {
            return this._clients;
        }

        public addClient(client: RedisPubSub.Client) {
            this._clients.set(client.id, client);
        }

        public getClient(id: string): RedisPubSub.Client {
            return this._clients.get(id);
        }

        public idExists(id: string): boolean {
            return this._clients[id] !== undefined;
        }

        public removeClient(id: string): boolean {
            return this._clients.delete(id);
        }
    }
}
