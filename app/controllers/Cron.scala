package controllers

import play.mvc.Controller
import scala.collection.JavaConversions._

import com.google.appengine.api.datastore._

import models._
import utils._

object Cron extends Controller {
  def oauthCheck {
    // find all users pending rego
    User.query.needsReminder.foreach { u =>
      var msg = "Hi! To valdate your ID and link your twitter account, please " +
      "click this link: " + AppConfig.getProp("sitebase") + "/Register/oauth" +
      "?jid=" + u.xmppID + "&key=" + Hashing.hashSaltString(u.xmppID)
      XMPPSend.sendMessage(u.xmppID, msg, false)
    }
      
    // message each one
    ok()
  }
}
