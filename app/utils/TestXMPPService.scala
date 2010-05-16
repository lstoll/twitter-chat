package utils

import com.google.appengine.api.xmpp.JID
import com.google.appengine.api.xmpp.Message
import com.google.appengine.api.xmpp.Presence
import com.google.appengine.api.xmpp.SendResponse
import java.io.IOException
import javax.servlet.http.HttpServletRequest

import com.google.appengine.api.xmpp.XMPPService

class TestXMPPService extends XMPPService {
    
  
  override def getPresence(jid:JID):Presence = {
    TestXMPPService.reset
    new Presence(TestXMPPService.nextAvailable);
  }
  
  override def getPresence(jid:JID, jid1:JID):Presence = {
    getPresence(jid)
  }
  
  override def sendInvitation(jid:JID) {
    TestXMPPService.reset
    TestXMPPService.lastRequestInvitation = true
    TestXMPPService.lastRequestJID = jid.getId()
  }
  
  override def sendInvitation(jid:JID, jid1:JID) {
    sendInvitation(jid)
  }
  
  override def sendMessage(msg:Message):SendResponse = {
    TestXMPPService.reset
    TestXMPPService.lastMessage = msg
    // TODO = handle multiple case
    TestXMPPService.lastRequestJID = msg.getRecipientJids()(0).getId()
    var sr = new SendResponse
    sr.addStatus(msg.getRecipientJids()(0), TestXMPPService.nextSendResult)
    sr
  }
  
  override def parseMessage(hsr:HttpServletRequest):Message = {
    throw new UnsupportedOperationException("We don't use that here.");
  }
  
  
}

object TestXMPPService {
  var lastMessage:Message = null
  var nextAvailable = true
  var nextSendResult:SendResponse.Status = SendResponse.Status.SUCCESS
  var lastRequestJID:String = null
  var lastRequestInvitation = false
  
  def reset = {
    TestXMPPService.lastMessage = null
    TestXMPPService.nextAvailable = true
    TestXMPPService.nextSendResult = SendResponse.Status.SUCCESS
    TestXMPPService.lastRequestJID = null
    TestXMPPService.lastRequestInvitation = false
  }
}
