/** This module defines the routes for videos using the store.js as db memory
 *
 * @author Johannes Konert
 * edited by Charline Waldrich
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
var logger = require('debug')('me2u4:videos');
var store = require('../blackbox/store');
var middleware = require('../restapi/middleware.js');

var videos = express.Router();

// if you like, you can use this for task 1.b:
var requiredKeys = {title: 'string', src: 'string', length: 'number'};
var optionalKeys = {description: 'string', playcount: 'number', ranking: 'number'};
var internalKeys = {id: 'number', timestamp: 'number'};

<<<<<<< HEAD
// routes **********************
videos.route('/')

    .get(function(req, res, next) {

        var videos = store.select('videos');

        if (!videos){
            res.status(204).json(videos);
        } else {

          var noError = true;

          // Offset and Limit
          if (req.query.offset || req.query.limit){

            // Offset
            if(req.query.offset !== undefined){
              if( !(Number.isNaN(req.query.offset)) ){
                var offset = parseInt(req.query.offset);

                if((offset >= 0) && (offset < videos.length)){
                  videos = videos.slice(offset);
                } else {
                  noError = false;
                  var error = new Error("Offset has to be possitive and equal or beyond the length of list!");
                  error.status = 400;
                  next(error);
                }
              } else {
                noError = false;
                var error = new Error("Offset is not a Number!");
                error.status = 400;
                next(error);
              }
            }

            // Limit
            if(req.query.limit !== undefined){
              if( !(Number.isNaN(req.query.limit)) ){
                var limit = parseInt(req.query.limit);

                if(limit > 0){
                  videos = videos.slice(0, limit);
                } else {
                  noError = false;
                  var error = new Error("Limit has to be positiv!");
                  error.status = 400;
                  next(error);
                }
              } else {
                noError = false;
                var error = new Error("Limit is not a Number!");
                error.status = 400;
                next(error);
              }
            }
          }
        if(noError){
          res.status(200).json(videos);
        }
      }
    })

    .post(function(req, res, next) {

        var errors = validate(req.body, "Post");

        if (errors.length > 0){
            var error = new Error(errors.join(" AND "));
            error.status = 400;
            next(error);
        } else {
            var obj = setObject(req.body);
            var id = store.insert('videos', obj);
            res.status(201).json(store.select('videos', id));
        }
    })

    // NOT ALLOWED ROUTE METHODS
    .put(function(req,res,next){
        var error = new Error("This method is not allowed on this path. Try the 'POST' method.");
        error.status = 405;
        next(error);
    })

    .patch(function(req,res,next){
        var error = new Error("This method is not allowed on this path. If you want to change attributes on a specific object, enter the ID and try the put method.");
        error.status = 405;
        next(error);
    })

    .delete(function(req,res,next){
        var error = new Error("This method is not allowed on this path. You can only delete specific videos and not the hole collection.");
=======
var allowedKeys = ["id", "timestamp", "title", "src", "length", "description", "playcount", "ranking"];
var errText = "";

// middlewares
videos.use(middleware);

// routes
videos.route('/')

    // gets all videos saved
    .get(function(req, res, next) {

        var videos = store.select("videos");

        if (videos === undefined) {
            res.status(204).end();
            return;
        }

        if (res.locals.items) {
            var filter = res.locals.items.filter;
            var limit = res.locals.items.limit;
            var offset = res.locals.items.offset;
            var search = res.locals.items.search;
            if (filter) {
                videos.forEach(function(video) {
                    clearNotAllowed(video, filter);
                });
            }


            if (limit || offset) {
                offset = offset || 0;
                if (offset >= videos.length) {

                    var err = new Error("offset higher than database length");
                    err.status = 400;

                    next(err);

                    return;
                }
                limit = limit || videos.length;
                videos = videos.slice(offset, limit + offset);
            }
        }
        res.status(200).json(videos).end();
    })

    // posts a new video and generates ID
    .post(function (req, res, next) {

        var errors = validate(req.body, "Post");

        if (errors.length > 0) {
            var error = new Error(errors.join(" AND "));
            error.status = 400;
            next(error);
        } else {
            var obj = setObject(req.body);
            var id = store.insert('videos', obj);
            res.status(201).json(store.select('videos', id)).end();
        }
    })

    // NOT ALLOWED ROUTE METHODS

    .put(function (req, res, next) {
        var error = new Error("This method is not allowed on this path. Try the 'POST' method.");
        error.status = 405;
        next(error);
    })

    .patch(function (req, res, next) {
        var error = new Error("This method is not allowed on this path. If you want to change attributes on a specific object, enter the ID and try the put method.");
        error.status = 405;
        next(error);
    })

    .delete(function (req, res, next) {
        var error = new Error("This method is not allowed on this path. You can only delete specific videos and not the hole collection.");
        error.status = 405;
        next(error);
    });


videos.route('/:id')

    // gets a specific video by its ID
    .get(function (req, res, next) {

        var valid = isNumber(req.params.id);
        var video = store.select("videos", req.params.id);

        if (valid) {
            next(valid);
        }
        else if (video === undefined) {
            var error = new Error("There has not been a video with this specific ID.");
            error.status = 204;
            next(error);
        }
        else {if (res.locals.items && res.locals.items.filter) {
            clearNotAllowed(video, res.locals.items.filter);
        }
            res.status(200).json(video).end();
        }
    })

    // replaces a already existing video with a new video object but the same ID
    .put(function (req, res, next) {

        var unvalidID = isNumber(req.params.id);

        if (unvalidID) {
            next(unvalidID);
        }
        else {
            var newObject = setObject(req.body);
            try {
                store.replace('videos', req.params.id, newObject);
                res.status(200).json(store.select('videos', req.params.id)).end();
            } catch (e) {
                var error = new Error("The ID you have given is not valid.");
                error.status = 404;
                next(error)
            }
        }
    })

    // deletes a specific video
    .delete(function (req, res, next) {

        var unvalidID = isNumber(req.params.id);

        if (unvalidID) {
            next(unvalidID);
        }
        else {
            try {
                store.remove('videos', req.params.id);
                res.status(204);
                next();
            } catch (e) {
                e.status = 404;
                next(e);
            }
        }

    })

    // NOT ALLOWED ROUTE METHODS

    .post(function (req, res, next) {
        var error = new Error("If you want to enter a new video, try the 'POST' method without entering an ID in the Path.");
        error.status = 405;
        next(error);
    })

    .patch(function (req, res, next) {
        var error = new Error("If you want to change a video element, try the 'PUT' method.");
>>>>>>> master
        error.status = 405;
        next(error);
    });

/**
 * Checks if the ID of the reqest is a valid number
 * @param id
 * @returns {boolean} true, if id is a number ; false, if id is not a number
 */
function isNumber(id) {
    var reqID = Number(id);

    if (Number.isNaN(reqID)) {
        var error = new Error("The ID you send in the request has to be of numeric value.");
        error.status = 406;
        return error;
    }
}

/**
 * Function to check the request body for errors.
 *
 * @param body of the request
 * @returns error if the a attribute set is the wrong type or value
 */
function validate(body, method) {

    var errors = [];

    if (body.id && method === "Post") {
        errors.push("The ID is set automatically. You cannot set it.");
    } else if (typeof body.id != "number" && !method) {
        errors.push("The ID of your object has to be of numeric value.");
    }

    if (!body.title || typeof body.title != "string") {
        errors.push("You have to set a title and it has to be words out of letters!");
    }

    if (body.description && typeof body.description != "string") {
        errors.push("If you want to add a description, it should contain words out of letters.");
    }

    if (!body.src || typeof body.src != "string") {
        errors.push("You have to set the source for the video. It should be the path where to find it.");
    }

    if (!body.length || typeof body.length != "number" || body.length < 0) {
        errors.push("The length of your video is required. It should be greater than 0 sec.");
    }

    if (body.timestamp) {
        errors.push("The timestamp is set automatically. You cannot set it.");
    }

    if (body.playcount && (typeof body.playcount != "number" || body.playcount < 0)) {
        errors.push("The playcount should be 0 or higher. If you do not set it, it will be set as 0.");
    }

    if (body.ranking && (typeof body.ranking != "number" || body.ranking < 0 )) {
        errors.push("The ranking should be 0 or higher. If you do not set it, it will be set as 0.");
    }

    return errors;
}

/**
 *
 * Function to set a new video object.
 *
 * @param body of the request in order to build an new video object.
 * @returns {{  title: *,
 *              description: (string|*),
 *              src: (string|*|string|string|string|string),
 *              length: *,
 *              timestamp: (*|string|Number),
 *              playcount: (number|*),
 *              ranking: (number|*)}} JSON attributes for the new object in store.js
 */
function setObject(body) {

    body.description = body.description || "";
    body.playcount = body.playcount || 0;
    body.ranking = body.ranking || 0;
    body.timestamp = Date.now();

    return {
        title: body.title,
        description: body.description,
        src: body.src,
        length: body.length,
        timestamp: body.timestamp,
        playcount: body.playcount,
        ranking: body.ranking
    }
}

videos.route('/:id')
    .get(function(req, res, next){

        var valid = isNumber(req.params.id);

        if (valid) {
            next(valid);
        } else if (store.select('videos', req.params.id) === undefined) {
            var error = new Error ("There has not been a video with this specific ID.");
            error.status = 404;
            next(error);
        } else {

          var video = store.select('videos', req.params.id)

          // Filter
          if (req.query.filter){
              var filter = req.query.filter.split(",");
              var videoAtts = [];
              var validAtt = true;

              // Get all attributes
              for (att in video){
                videoAtts.push(att);
              }

              // Validate filter attributes
              for (var i = 0; i < filter.length ; i++) {
                if (!(videoAtts.includes(filter[i]))){
                  validAtt = false;
                  var error = new Error("Filter parameter '"+ filter[i] +"' not exists.");
                  error.status = 400;
                  next(error);
                }
              }

              //Remove attributes
              if(validAtt){
                for (att in video){
                  if (!(filter.includes(att))){
                    delete video[att];
                  }
                }
                res.status(200).json(video);
              }
            } else {
              res.status(200).json(video);
            }
        }
    })

    .put(function(req, res, next) {

        var valid = isNumber(req.params.id);

        if (valid){
            next(valid);
        } else {
            var newObject = setObject(req.body);
            try {
                store.replace('videos', req.params.id, newObject);
                res.status(200).json(store.select('videos', req.params.id));
            } catch (e) {
                var error = new Error("The ID you have given is not valid.");
                error.status = 404;
                next(error);
            }
        }
    })

    .delete(function(req, res, next){

        var err = isNumber(req.params.id);

        if (err) {
            next(err);
        } else {
            try {
                store.remove('videos', req.params.id);
                res.status(204);
                next();
            } catch (e){
                e.status = 404;
                next(e);
            }
        }
    })

    // NOT ALLOWED ROUTE METHODS
    .post(function(req, res, next){
        var error = new Error("If you want to enter a new video, try the 'POST' method without entering an ID in the Path.");
        error.status = 405;
        next(error);
    })

    .patch(function(req, res, next){
        var error = new Error("If you want to change a video element, try the 'PUT' method.");
        error.status = 405;
        next(error);
    });


/**
 * Checks if the ID of the reqest is a valid number
 * @param id
 * @returns {boolean} true, if id is a number ; false, if id is not a number
 */
function isNumber(id){
    var reqID = Number(id);

    if (Number.isNaN(reqID)){
        var error = new Error("The ID you send in the request has to be of numeric value.");
        error.status = 406;
        return error;
    }
}

/**
 * Function to check the request body for errors.
 *
 * @param body of the request
 * @returns error if the a attribute set is the wrong type or value
 */
function validate(body, method){

    var errors = [];

    if (body.id && method === "Post" ){
        errors.push("The ID is set automatically. You cannot set it.");
    } else if (typeof body.id != "number" && !method) {
        errors.push("The ID of your object has to be of numeric value.");
    }

    if (!body.title || typeof body.title != "string"){
        errors.push("You have to set a title and it has to be words out of letters!");
    }

    if (body.description && typeof body.description != "string"){
        errors.push("If you want to add a description, it should contain words out of letters.");
    }

    if (!body.src || typeof body.src != "string"){
        errors.push("You have to set the source for the video. It should be the path where to find it.");
    }

    if (!body.length || typeof body.length != "number" || body.length < 0){
        errors.push("The length of your video is required. It should be greater than 0 sec.");
    }

    if (body.timestamp){
        errors.push("The timestamp is set automatically. You cannot set it.");
    }

    if (body.playcount && (typeof body.playcount != "number" || body.playcount < 0)){
        errors.push("The playcount should be 0 or higher. If you do not set it, it will be set as 0.");
    }

    if (body.ranking && (typeof body.ranking != "number" || body.ranking < 0 )){
        errors.push("The ranking should be 0 or higher. If you do not set it, it will be set as 0.");
    }

    return errors;
}

/**
 *
 * Function to set a new video object.
 *
 * @param body of the request in order to build an new video object.
 * @returns {{  title: *,
 *              description: (string|*),
 *              src: (string|*|string|string|string|string),
 *              length: *,
 *              timestamp: (*|string|Number),
 *              playcount: (number|*),
 *              ranking: (number|*)}} JSON attributes for the new object in store.js
 */
function setObject(body){

    body.description = body.description || "";
    body.playcount = body.playcount || 0;
    body.ranking = body.ranking || 0;
    body.timestamp = Date.now();

    return {
        title : body.title,
        description : body.description,
        src : body.src,
        length : body.length,
        timestamp : body.timestamp,
        playcount : body.playcount,
        ranking : body.ranking
    }
}


// this middleware function can be used, if you like (or remove it)
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

// if filters are given this function deletes the unwanted keys of the objects
var clearNotAllowed = function (obj, filter) {
    console.log('clearnotallowed, ');

    var allowed = filter || allowedKeys;

    console.log(allowed);
    console.log(obj);

    Object.keys(obj).forEach(function (key) {
        if (allowed.indexOf(key) === -1) delete obj[key];
    });
    return obj;
};

module.exports = videos;
