/**
 * A simple HelloWorld-WebServer.
 * It provides static-content, shows the actual system-time and reads a text-file.
 *
 * @author: Wookies
 */
var express = require('express');
var fs = require('fs');

var app = express();
//Variable for memoisation
var memo;

app
  //Public-route, provides static content
  .use('/public', express.static('static'))

  //Provides the current system time formated in text/plain
  .get('/time', function (req, res) {
    res.format( {
      'text/plain' : function(){
        var date = new Date;
        res.send(date.toUTCString());
      }
    });
  })

  //Reads the content out from a text file
  .get('/file.txt', function (req, res) {
    var before = process.hrtime();
    var time
    if (!memo){
      fs.readFile('file.txt', function (err, data) {
        memo = data;
        time = process.hrtime(before);
        res.send('<!DOCTYPE html>' +
                 '<html lang="de">'+
                 '<head><meta charset="UTF-8"></head>' +
                 '<body><h1>' + data + '</h1>' +
                 '<p>Time for reading the file: ' + (time[0] * 1e9 + time[1]) + ' nanoseconds!</p></body>' +
                 '</html>'
               );
      });
    } else {
      time = process.hrtime(before);
      res.send('<!DOCTYPE html>' +
               '<html lang="de">'+
               '<head><meta charset="UTF-8"></head>' +
               '<body><h1>' + memo + '</h1>' +
               '<p>Time for reading the file: ' + (time[0] * 1e9 + time[1]) + ' nanoseconds!</p></body>' +
               '</html>'
             );
    }
  })

  //Default route
  .get('/*', function (req, res) {
    res.send('<!DOCTYPE html>' +
             '<html lang="de">'+
             '<head><meta charset="UTF-8"></head>' +
             '<body><h1>Hello World!</h1></body>' +
             '</html>'
          );
    });

var server = app.listen(3000, function() {
  console.log("Server is ready an listening at http://localhost:3000 - Have Fun :)");
});
