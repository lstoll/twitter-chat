/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.SendResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.xmpp.XMPPService;

/**
 *
 * @author lstoll
 */
public class TestXMPPService implements XMPPService {
    public static Message lastMessage;
    public static boolean nextAvailable = true;
    public static SendResponse.Status nextSendResult = SendResponse.Status.SUCCESS;
    public static String lastRequestJID;
    public static boolean lastRequestInvitation = false;

    public Presence getPresence(JID jid) {
        reset();
        return new Presence(nextAvailable);
    }

    public Presence getPresence(JID jid, JID jid1) {
        return getPresence(jid);
    }

    public void sendInvitation(JID jid) {
        reset();
        lastRequestInvitation = true;
        lastRequestJID = jid.getId();
    }

    public void sendInvitation(JID jid, JID jid1) {
        sendInvitation(jid);
    }

    public SendResponse sendMessage(Message msg) {
        reset();
        lastMessage = msg;
        lastRequestJID = msg.getRecipientJids()[0].getId(); // TODO = handle multiple case
        SendResponse sr = new SendResponse();
        sr.addStatus(msg.getRecipientJids()[0], nextSendResult);
        return sr;
    }

    public Message parseMessage(HttpServletRequest hsr) throws IOException {
        throw new UnsupportedOperationException("We don't use that here.");
    }

    private void reset() {
        lastMessage = null;
        nextAvailable = true;
        nextSendResult = SendResponse.Status.SUCCESS;
        lastRequestJID = null;
        lastRequestInvitation = false;
    }

}
