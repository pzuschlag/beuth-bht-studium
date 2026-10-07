import * as redis from 'redis';
import {CLIENTOPT} from '../constants';
import {RedisPubSub} from './redis-pubsub';

export class ClientApi {

    private _pub: redis.RedisClient;
    private _sub: redis.RedisClient;;
    private _redisPubSub;

    constructor() {

        this._pub = redis.createClient(CLIENTOPT);
        this._sub = redis.createClient(CLIENTOPT);

        this._redisPubSub = new RedisPubSub.Main(this._pub, this._sub);

        (<any>this._pub).on("connect", () => console.log("RedisClient \"pub\” connected"));
        (<any>this._pub).on("error", (err) => console.error("RedisClient \"pub\” err: " + err));

        (<any>this._sub).on("connect", () => console.log("RedisClient \"sub\” connected"));
        (<any>this._sub).on("error", (err) => console.error("RedisClient \"sub\” err: " + err));

    }

    public createClient(socket: SocketIO.Socket): RedisPubSub.Client {
        return this._redisPubSub.createClient(socket);
    }

    public isClientSubscribedTo(id: string, channel: string): boolean {
        return this._redisPubSub.isClientSubscribedTo(id, channel);
    }

}
