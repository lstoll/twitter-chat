package utils;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

/**
 *
 * @author lstoll
 */
object XMPPSend {
  var testService:XMPPService = null
  
  def testSetUp = {
    testService = new TestXMPPService
  }
  
  def testTearDown = {
    testService = null;
  }
  
  /**
  * Delivers a XMPP message to the user. Can buffer the message for later if
  * the user is offline
  * @param to the JID of the recipient
  * @param body The message to send
  * @param buffer true if we should buffer the message if the user is offline
  * @return true if it was delivered, false if it wasn't (or it was buffered)
  */
  def sendMessage(to:String, body:String, buffer:Boolean):Boolean = {
    var jid = new JID(to)
    var msg = new MessageBuilder()
    .withRecipientJids(jid)
    .withBody(body)
    .build()
    
    var messageSent = false
    var xmpp = getCurrentXMPPService
    if (xmpp.getPresence(jid).isAvailable) {
      var status = xmpp.sendMessage(msg)
      messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
    }
    else {
      // TODO - add the message to a buffer queue
    }
    messageSent
  }

  /**
  * Send a XMPP invite to the user
  */
  def inviteUser(jid:String) = {
    getCurrentXMPPService.sendInvitation(new JID(jid))
  }
  
  def getCurrentXMPPService():XMPPService = {
    if (testService != null)
    return testService
    else 
    return XMPPServiceFactory.getXMPPService()
  }
}
