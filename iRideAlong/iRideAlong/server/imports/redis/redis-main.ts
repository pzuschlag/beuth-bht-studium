import {Meteor} from 'meteor/meteor';
import {ClientApi} from '../classes/client-api';
import {DBHelper} from '../classes/db-helper';
import * as socket_io from 'socket.io';
import * as http from 'http';

declare var process: any;

const PORT = parseInt(process.env.SOCKET_PORT) || 3030;

Meteor.startup(function() {

    console.log("Starting redis-main server ");

    console.log(`RedisMain server in ${process.env.NODE_ENV} mode`);
    console.log(`URL is ${Meteor.absoluteUrl()}`);

    let clientApi = new ClientApi();
    let dbHelper = new DBHelper();

    let server = http.createServer();
    let io = socket_io(server);

    io.sockets.on('connection', (socket: SocketIO.Socket) => {

        let client = clientApi.createClient(socket);

        socket.emit('initial', { type: 'initial', data: socket.id });

        dbHelper.getAllStationStatus((err, res) => {
            if (err) {
                console.error('getAllStationStatus error', err);
            }
            socket.emit('station-status',
                { type: 'station-status', data: res, err: err }
            );
        });

        socketListener(socket, client);
        clientListener(socket, client);

    });

    // Start server
    try {
        server.listen(PORT);
    } catch (e) {
        console.error('SERVER start error', e);
    }

    let socketListener = (socket: SocketIO.Socket, client) => {

        socket.on('disconnect', () => {
            client.end(
                (res) => dbHelper.updateStatus(res, broadcast))
        });

        socket.on('subscribe', (channelName: string, errFn, fnSuc) => {
            client.subscribe(channelName,
                (res) => {
                    if (res && !errFn) {
                        fnSuc('OK');
                        dbHelper.updateStatus(res, broadcast);
                    } else {
                        errFn;
                    }
                });
        });

        socket.on('unsubscribe', (channelName: string, errFn, fnSuc) => {
            client.unsubscribe(channelName,
                (res) => {
                    if (res && !errFn) {
                        fnSuc('OK');
                        dbHelper.updateStatus(res, broadcast);
                    } else {
                        errFn;
                    }
                });
        });

        socket.on('publish', (data, errFn, fnSuc) => {
            client.publish(data.channel, data.message, (err) => {
                if (!err && !errFn) {
                    fnSuc('OK');
                } else {
                    fnSuc(err);
                    errFn;
                }
            })
        });

        socket.on('send-to', (data, errFn, fnSuc) => {
            let id = data.id;
            if (clientApi.isClientSubscribedTo(id, data.channel) && !errFn) {
                console.log(`PRIVATEMESSAGE ${socket.id}: ${data.message} => ${id}`);
                sendTo(id, 'private-message', 'private', data.message, 'message');
                fnSuc('OK');
            } else {
                console.error(`PRIVATEMESSAGE ${socket.id}: ${data.message} => ${id} | NOTSUBSCRIBED`);
                fnSuc(`Client ${id} is not subscribed to ${data.channel}`);
                errFn;
            }
            // send to single id / private message
        });
    };

    let sendTo = (id: string, socketName: string, channel: string, message: string, type?: string) => {
        (<any>io.to(id)).emit(socketName, {
            type: type || socketName,
            data: {
                channel: channel,
                message: message
            }
        })
    }

    let broadcast = (subs) => {
        console.log("BROADCAST");
        console.log(subs);
        // broadcast to all socket
        (<any>io.sockets).emit('subscriptions',
            { type: 'subscriptions', data: subs }
        );
    }

    let clientListener = (socket: SocketIO.Socket, client) => {
        client.onMessage((channel, message, id) => {
            socket.emit('message', {
                type: 'message',
                data: {
                    channel: channel,
                    message: message,
                    id: id
                }
            });
        });
    }

});
