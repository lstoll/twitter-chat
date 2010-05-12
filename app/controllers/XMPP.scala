package controllers

import play.mvc.Controller
import utils.XMPPSend
import play.Logger

object XMPP extends Controller {
  def receiveMessage {
        // Parse the message details
        var to = params.get("to")
        var from = params.get("from")
        var body = params.get("body")

        // LOG the request details
        Logger.debug("XMPP Message sent to " + to + " by " + from + " is " + body )
        ok()
    }
}