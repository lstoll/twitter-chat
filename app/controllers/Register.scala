package controllers

import play.mvc.Controller
import play.data.validation._

import models._
import utils._

object Register extends Controller {

  def submitJid(@Email @Required jid:String) {
    if (Validation.hasErrors()) {
      flash.error("JID Missing or invalid format")
      Application.index
    }
    // Add them to the DB
    var u = new User
    u.xmppID = jid
    u.insert
    // invite
    XMPPSend.inviteUser(jid)
    
    // Render welcome page
    render()
  }
}
