/** Main app for server to start a small REST API for tweets
 * The included ./blackbox/store.js gives you access to a "database" which contains
 * already tweets with id 101 and 102, as well as users with id 103 and 104.
 * On each restart the db will be reset (it is only in memory).
 * Best start with GET http://localhost:3000/tweets to see the JSON for it
 *
 * TODO: Start the server and play a little with Postman
 * TODO: Look at the Routes-section (starting line 68) and start there to add your code
 *
 * @author Johannes Konert
 * adapted by Charline Waldrich, Philip Zuschlag, Robert Ullmann
 * @licence CC BY-SA 4.0
 *
 *
 */
"use strict";  // tell node.js to be more "strict" in JavaScript parsing (e.g. not allow variables without var before)

// node module imports
var path = require('path');
var express = require('express');
var bodyParser = require('body-parser');

// our own modules imports
var store = require('./blackbox/store.js');

// creating the server application
var app = express();

// Middleware ************************************
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

// logging
app.use(function(req, res, next) {
    console.log('Request of type '+req.method + ' to URL ' + req.originalUrl);
    next();
});

// API-Version control. We use HTTP Header field Accept-Version instead of URL-part /v1/
app.use(function(req, res, next){
    // expect the Accept-Version header to be NOT set or being 1.0
    var versionWanted = req.get('Accept-Version');
    if (versionWanted !== undefined && versionWanted !== '1.0') {
        // 406 Accept-* header cannot be fulfilled.
        res.status(406).send('Accept-Version cannot be fulfilled').end();
    } else {
        next(); // all OK, call next handler
    }
});

// request type application/json check
app.use(function(req, res, next) {
    if (['POST', 'PUT'].indexOf(req.method) > -1 &&
        !( /application\/json/.test(req.get('Content-Type')) )) {
        // send error code 415: unsupported media type
        res.status(415).send('wrong Content-Type');  // user has SEND the wrong type
    } else if (!req.accepts('json')) {
        // send 406 that response will be application/json and request does not support it by now as answer
        // user has REQUESTED the wrong type
        res.status(406).send('response of application/json only supported, please accept this');
    }
    else {
        next(); // let this request pass through as it is OK
    }
});


// Routes for tweets ***************************************

/**
 * Get all tweets
 */
app.get('/tweets', function(req,res,next) {
    var tweets = store.select('tweets');

    for (var i in tweets) {
        var tweet = tweets[i];
        addHrefTweet(tweet, req);
    }

    var obj = {};
    addHrefTweet(obj, req);
    obj.items = tweets;
    res.json(obj);
});

app.post('/tweets', function(req,res,next) {
    var id = store.insert('tweets', req.body);
    // set code 201 "created" and send the item back
    res.status(201).json(store.select('tweets', id));
});

/**
 * Get tweet with specific ID
 */
app.get('/tweets/:id', function(req,res,next) {
    var tweets = store.select('tweets', req.params.id);
    addHrefTweet(tweets, req);
    res.json(tweets);
});

app.delete('/tweets/:id', function(req,res,next) {
    store.remove('tweets', req.params.id);
    res.status(200).end();
});

app.put('/tweets/:id', function(req,res,next) {
    store.replace('tweets', req.params.id, req.body);
    res.status(200).end();
});

// Routes for users ***************************************

/**
 * Get all Users.
 * If the request contains an expand, it adds a collection to the user object with all tweets posted by the user.
 */
app.get('/users', function(req,res,next) {

    var users = store.select('users');
    var items = [];

    for (var i = 0; i < users.length; i++) {
        var user = users[i];
        addHrefUser(user, req);
        if (req.query.expand === "tweets"){
            addTweets(user, req);
        }
        items.push(user);
    }

    var obj = {};
    addHrefUser(obj, req);
    obj.items = items;
    res.json(obj);
});

app.post('/users', function(req,res,next) {
    var id = store.insert('users', req.body);
    // set code 201 "created" and send the item back
    res.status(201).json(store.select('users', id));
});

/**
 * Get specific User by his ID.
 * If the request contains an expand, it adds a collection with the tweets postet by this user.
 */
app.get('/users/:id', function(req,res,next) {

    var user = store.select('users', req.params.id);

    if (req.query.expand === "tweets"){
        addTweets(user, req);
        res.json(user);
    } else {
        addHrefUser(user, req, null);
        res.json(user);
    }

});

app.delete('/users/:id', function(req,res,next) {
    store.remove('users', req.params.id);
    res.status(200).end();
});

app.put('/users/:id', function(req,res,next) {
    store.replace('users', req.params.id, req.body);
    res.status(200).end();
});

/**
 * Route to specific user and all his tweets posted.
 */
app.get('/users/:id/tweets', function(req, res, next){
    var user = store.select('users', req.params.id);
    addTweets(user, req);
    res.json(user.tweets);
});

/**
 * Route for PATCH
 */
app.patch("/users/:id", function(req, res){
  var user = store.select('users', req.params.id);

  var firstname = req.body.firstname;
  var lastname = req.body.lastname;

  if(firstname !== undefined){
    user.firstname = firstname;
  }

  if(lastname !== undefined){
    user.lastname = lastname;
  }

  store.replace('users', req.params.id, user);
  res.status(200).end();
});

// Functions ***************************************

/**
 * Function to add hrefs for tweets.
 * @param tweets: object to which we add the href
 * @param req: needed to get url informations
 */
function addHrefTweet(tweet, req){
    if (tweet.id === undefined){
        var fullUrl = req.protocol + '://' + req.get('host') + '/tweets';
    }
    else {
        var fullUrl = req.protocol + '://' + req.get('host') + '/tweets/' + tweet.id;
    }

    tweet.href = fullUrl;
}

/**
 * Function to add hrefs for users.
 * @param users: object to which we add the href
 * @param req: needed to get url information
 */
function addHrefUser(user, req){
    if (user.id === undefined){
        var fullUrl = req.protocol + '://' + req.get('host') + '/users';
    }
    else {
        var fullUrl = req.protocol + '://' + req.get('host') + '/users/' + user.id;
    }

    user.href = fullUrl;
}

/**
 * Function to add tweets to user
 * @param user: object for which we search all tweets he posted
 * @param req: needed in order to build the right url as href reference
 */
function addTweets(user, req){

    var arrayTweets = [];
    var tweets = store.select('tweets');

    for (var i = 0; i< tweets.length; i++){
        var creatorHref = tweets[i].creator.href;
          if (creatorHref.includes(user.id)) {
              addHrefTweet(tweets[i], req);
              arrayTweets.push(tweets[i]);
        }
    }

    user.tweets ={
        href: req.protocol + "://" + req.get('host') + '/users/'+ user.id + '/tweets',
        items: arrayTweets
    };
}

// CatchAll for the rest (unfound routes/resources) ********

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers (express recognizes it by 4 parameters!)

// development error handler
// will print stacktrace as JSON response
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        console.log('Internal Error: ', err.stack);
        res.status(err.status || 500);
        res.json({
            error: {
                message: err.message,
                error: err.stack
            }
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.json({
        error: {
            message: err.message,
            error: {}
        }
    });
});


// Start server ****************************
app.listen(3000, function(err) {
    if (err !== undefined) {
        console.log('Error on startup, ',err);
    }
    else {
        console.log('Listening on port 3000');
    }
});
