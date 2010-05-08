var sys = require('sys');
var http = require('http');
var base64 = require('./vendor/base64'); 

// Command line args 
var USERNAME = process.ARGV[2];
var PASSWORD = process.ARGV[3];
var SERVER = process.ARGV[4];
      
if (!USERNAME || !PASSWORD || !SERVER)
  return sys.puts("Usage: node server.js <twitter_username> <twitter_password> " + 
                  "<master server>");

var auth = base64.encode(USERNAME + ':' + PASSWORD);

// Connection to Twitter's streaming API
var twitter = http.createClient(80, "stream.twitter.com");
var request = twitter.request("GET", "/1/statuses/filter.json?track=Bieber",
                              {'host': 'stream.twitter.com', 
                               'Authorization': auth });


request.addListener('response', function (response) {
sys.puts('STATUS: ' + response.statusCode);
  response.setBodyEncoding("utf8");
  response.addListener("data", function (chunk) {
                        // Send response to all connected clients
                         sys.puts(chunk);
  });
});
request.end();
