import java.util.HashMap;
import java.util.Map;
import play.mvc.Http;
import play.test.FunctionalTest;

public class TestUtils {

    /**
     * Simulates an incoming XMPP Request
     * @param from sender of message
     * @param msg message text to send
     * @return Http.Response from the request
     */
    public static Http.Response doXmppRequest(String from, String msg) {
        Map files = new HashMap();
        Map<String, String> params = new HashMap();
        params.put("to", "twitter-chat@appspot.com");
        params.put("from", from);
        params.put("body", msg);

        return FunctionalTest.POST("/_ah/xmpp/message/chat/", params, files);
    }

}
