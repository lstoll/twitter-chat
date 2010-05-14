import java.util.HashMap
import java.util.Map
import play.mvc.Http
import play.test.FunctionalTest

trait TestUtils {

    /**
     * Simulates an incoming XMPP Request
     * @param from sender of message
     * @param msg message text to send
     * @return Http.Response from the request
     */
    def doXmppRequest(from:String, msg:String):Http.Response = {
        var files:Map[String, java.io.File] = new HashMap[String, java.io.File]
        var params:Map[String, String] = new HashMap[String, String]
        params.put("to", "twitter-chat@appspot.com")
        params.put("from", from)
        params.put("body", msg)

        FunctionalTest.POST("/_ah/xmpp/message/chat/", params, files);
    }
}
