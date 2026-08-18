/** This module defines the routes for videos using a mongoose model
 *
 * @author Johannes Konert
 * edited by the Wookies
 * @licence CC BY-SA 4.0
 *
 * @module routes/videos
 * @type {Router}
 */

// remember: in modules you have 3 variables given by CommonJS
// 1.) require() function
// 2.) module.exports
// 3.) exports (which is module.exports)

// modules
var express = require('express');
var logger = require('debug')('me2u5:videos');

var videos = express.Router();

// initialize the Wookie database
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/WookiesRules');

var VideoModel = require('../models/video');

// routes for all videos without ID **********************
videos.route('/')


    // gets all videos in database
    .get(function (req, res, next) {

        res.locals.processed = true;

        VideoModel.find({}, filters(req), function (err, items) {

            if (!err) {
                if (items.length > 0) {
                    res.status(200).json(items).end();
                } else {
                    // NO CONTENT > empty db
                    res.status(204).json().end();
                }
            } else {
                err.status = 406;
                err.message += ' in fields: ' + Object.getOwnPropertyNames(err.errors);
                next(err);
            }

        });
    })

    // posts a new video with generated ID
    .post(function (req, res, next) {

        res.locals.processed = true;

        var video = new VideoModel(req.body);

        video.save(function (err) {
            if (!err) {
                res.status(201).json(video).end();
            } else {
                err.status = 406;
                err.message += ' in fields: ' + Object.getOwnPropertyNames(err.errors);
                next(err);
            }
        });
    })

    // All forbidden routes get an error with status 405
    .all(function (req, res, next) {
        if (res.locals.processed) {
            next();
        } else {
            // reply with wrong method code 405
            var err = new Error('this method is not allowed at ' + req.originalUrl);
            err.status = 405;
            next(err);
        }
    });


// routes for videos with ID **********+
videos.route('/:id')

    // gets one specific video out of the database
    .get(function (req, res, next) {

        res.locals.processed = true;

        VideoModel.findById(req.params.id, filters(req), function (err, video) {
            if (!err) {
                res.status(200).json(video).end();
            } else {
                err.status = 406;
                err.message = 'There is no video by the given ID: ' + req.params.id + '.';
                next(err);
            }
        });
    })


    // creates a new video object with already existing ID - replace function
    .put(function (req, res, next) {

        res.locals.processed = true;

        if (req.params.id == req.body._id) {

            var video = {};

            Object.keys(VideoModel.schema.paths).forEach(function (key) {
                if (req.body.hasOwnProperty(key)) {
                    video[key] = req.body[key];
                } else {
                    if (VideoModel.schema.paths[key].options.default !== undefined)
                        video[key] = VideoModel.schema.paths[key].options.default;
                    else
                        video[key] = undefined;
                }
            });

            delete video.__v;
            delete video.timestamp;

            VideoModel.findByIdAndUpdate(req.params.id, video,
                {runValidators: true, new: true},
                function (err, video) {
                    if (err) {
                        next(err);
                    } else {
                        res.status(201).json(video).end();
                    }

                });

        } else {
            var err = new Error('id of PUT resource and send JSON body are not equal ' + req.params.id + " " + req.body._id);
            err.status = 406;
            next(err);
        }
    })

    // deletes one specific video
    .delete(function (req, res, next) {

        res.locals.processed = true;

        VideoModel.findByIdAndRemove(req.params.id, function (err, video) {
            if (!err) {
                res.status(204).end();
            } else {
                err.status = 406;
                err.message = 'The video by the ID ' + req.params.id + ' could not be deleted.';
                next(err);
            }
        });
    })

    // gives the possibility to change one ore more attributes from an already existing video
    .patch(function (req, res, next) {

        res.locals.processed = true;

        delete req.body._id;
        delete req.body.__v;
        delete req.body.timestamp;

        VideoModel.findByIdAndUpdate(req.params.id, req.body, {
            new: true,
            runValidators: true
        }, function (err, video) {
            if (!err) {
                res.status(200).json(video).end();
            } else {
                err.status = 406;
                err.message += '. The video by the ID: ' + req.params.id + ', could not be updated.';
                next(err);
            }
        });
    })


    // all other not wanted routes operations
    .all(function (req, res, next) {
        if (res.locals.processed) {
            next();
        } else {
            // reply with wrong method code 405
            var err = new Error('this method is not allowed at ' + req.originalUrl);
            err.status = 405;
            next(err);
        }
    });


// this middleware function can be used, if you like or remove it
// it looks for object(s) in res.locals.items and if they exist, they are send to the client as json
videos.use(function (req, res, next) {
    // if anything to send has been added to res.locals.items
    if (res.locals.items) {
        // then we send it as json and remove it
        res.json(res.locals.items);
        delete res.locals.items;
    } else {
        // otherwise we set status to no-content
        res.set('Content-Type', 'application/json');
        res.status(204).end(); // no content;
    }
});

/** functions needed for filtering
 *
 * @param req, in order to find out the wanted filters
 * @returns {{}} object containing filters : the filters from the request
 */
function filters(req) {

    var filters = {};

    if (req.query.filter) {
        req.query.filter.split(",").forEach(function (key) {

            var allesOk = false;
            Object.keys(VideoModel.schema.paths).forEach(function (schemaKey) {
                if (key == schemaKey) allesOk = true;
            });

            if (!allesOk) {
                var err = new Error("The given filter " + key + " does not exist.");
                err.status = 400;
                throw err;
            } else {
                filters[key] = true;
            }
        });
    }
    return filters;
}


module.exports = videos;