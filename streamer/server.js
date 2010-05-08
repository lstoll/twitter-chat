var sys = require('sys'),
    http = require('http'),
    base64 = require('./vendor/base64'); 

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
var request = twitter.request("POST", "/1/statuses/filter.json",
                              {'host': 'stream.twitter.com', 
                               'Authorization': auth,
                               'Content-Type': 'application/x-www-form-urlencoded'});


request.addListener('response', function (response) {
  sys.puts('STATUS: ' + response.statusCode);
  response.setBodyEncoding("utf8");
  response.addListener("data", function (chunk) {
    // Send response to all connected clients
    sys.puts('--------------------------------------------');
    sys.puts(chunk);
  });
});
request.write("track=Bieber");
request.end();
