import {Meteor} from 'meteor/meteor';
import { Mongo } from 'meteor/mongo';

import {Routes, Points} from '../../../imports/api/geo';

Meteor.startup(function() {
    Routes._ensureIndex({
        _name: "text",
        _author: "text",
        _desc: "text",
    });
});

Meteor.publish('routes', function tasksPublication() {
    return Routes.find();
});
Meteor.publish('points', function tasksPublication() {
    return Points.find();
});

Meteor.methods({
    addPoint: (point) => {
        return Points.insert(point);
    },
    addRoute: (route) => {
        return Routes.insert(route);
    },
    findRoute: (query) => {
        return Routes.find({
            $text: {
                $search: query
            }
        }).fetch();
    }
})
