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
// for timeline, http://chirpstream.twitter.com/2b/user.json
var twitter = http.createClient(80, "stream.twitter.com");
var request = twitter.request("POST", "/1/statuses/filter.json",
                              {'host': 'stream.twitter.com', 
                               'Authorization': auth,
                               'Content-Type': 'application/x-www-form-urlencoded'});

var trailingData = '';

request.addListener('response', function (response) {
  sys.puts('STATUS: ' + response.statusCode);
  response.setBodyEncoding("utf8");
  response.addListener("data", function (chunk) {
    var tweets = chunk.split("\r\n");
    for(var i = 0; i < tweets.length; i++){
      var tweet = chunk + tweets[i];
      if (tweet.length == 0) continue;
      try {
        var result = JSON.parse(tweet);
        trailingData = "";
        sys.puts(sys.inspect(result));
      } catch(e) {
        trailingData = tweet;
      }
    }
  });
});
request.write("track=Bieber");
request.end();
