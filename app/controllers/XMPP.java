package controllers;

import play.mvc.*;
import play.Logger;

public class XMPP extends Controller {
    public static void receiveMessage() {
        // Parse the message details
        String to = params.get("to");
        String from = params.get("from");
        String body = params.get("body");

        // LOG the request details
        Logger.debug("XMPP Message sent to " + to + " by " + from + " is " + body );
        ok();
    }
}