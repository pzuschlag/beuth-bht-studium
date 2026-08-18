/** This middlware-module defines the Filter, Offset, Limit for returns Objects
 *
 * @author wookiees
 * @licence CC BY-SA 4.0
 *
 * @module restapi/middlware
 */

var internalKeys = {id: 'number', timestamp: 'number'};
var allowedKeys = ["id", "timestamp", "title", "src", "length", "description", "playcount", "ranking"];

var middleware = require('express').Router();

/**
 * String Object to collect the Errormessages
 * @type {string}
 */
var errText = "";

/**
 * Set a Filter with allowed Keys for returned Objects on Get Method
 */
middleware.use(function(req, res, next) {
    if (req.method === "GET" && req.query.filter) {

        var filter = req.query.filter.replace(" ", "").split(",");
        var filterErr = "Filter not allowed: ";
        // check if all filters are valid
        filter.forEach(function(item) {
            if (allowedKeys.indexOf(item) === -1) {
                filterErr += item + ", ";
            }
        });

        if (filterErr !== "Filter not allowed: ") {
            errText = filterErr;
            console.log("Filtererror" && errText);
        } else {
            if (!res.locals.items) res.locals.items = {};
            console.log("Filter end" && filter);
            res.locals.items.filter = filter;
        }
    }
    next();
});

/**
 * Set the Limit for returned Videos on GET
 */
middleware.use(function(req, res, next) {
    if (req.method === "GET" && req.query.limit) {
        var lim = parseInt(req.query.limit);
        if (isNaN(lim)) {
            errText += "limit is no number / emtpy ?; ";
        } else if (lim < 1) {
            errText += "limit must be greater than 0; ";
        } else {
            if (!res.locals.items) res.locals.items = {};
            res.locals.items.limit = lim;
        }
    }
    next();
});

/**
 * Set the offset for returned Videos on GET
 */
middleware.use(function(req, res, next) {
    if (req.method === "GET" && req.query.offset) {
        var off = parseInt(req.query.offset);
        if (isNaN(off)) {
            errText += "offset is no number / emtpy ; ";
        } else if (off < 0) {
            errText += "offset must be 0 or greater 0 ; ";
        } else {
            if (!res.locals.items) res.locals.items = {};
            res.locals.items.offset = off;
        }
    }
    next();
});


/**
 * search
 */
/*
middleware.use(function(req, res, next) {
    if (req.method === "GET" && req.query) {
        var searchErr = "Search for this keywords not permitted: ";
        Object.keys(req.query).forEach(function(key) {
//            if (usedKeywords.indexOf(key) === -1) {
                if (allowedKeys.indexOf(key) !== -1 && Object.keys(internalKeys).indexOf(key) === -1) {
                    if (!res.locals.items) res.locals.items = {};
                    if (!res.locals.items.search) res.locals.items.search  = {};
                    res.locals.items.search[key] = req.query[key];
                } else {
                    searchErr += key + ", ";
                }
 //           }
        });
        if (searchErr !== "Search for this keywords not permitted: ") {
            searchErr = searchErr.slice(0, -2) + "; ";
            errText += searchErr;
        }
    }
    next();
});
*/


/**
 * handle all 400 Status Errors
 */
middleware.use(function(req, res, next) {
    if (errText !== "") {
        var err = new Error(errText);
        err.status = 400;
        errText = "";
        next(err);
    } else {
        next();
    }
});


module.exports = middleware;