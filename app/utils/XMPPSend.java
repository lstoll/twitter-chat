/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class XMPPSend {
    private static XMPPService testService;

    public static void testSetUp() {
        testService = new TestXMPPService();
    }

    public static void testTearDown() {
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
    public static boolean sendMessage(String to, String body, boolean buffer) {
        JID jid = new JID(to);
        Message msg = new MessageBuilder()
            .withRecipientJids(jid)
            .withBody(body)
            .build();

        boolean messageSent = false;
        XMPPService xmpp = getCurrentXMPPService();
        if (xmpp.getPresence(jid).isAvailable()) {
            SendResponse status = xmpp.sendMessage(msg);
            messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
        }
        else {
            // TODO - add the message to a buffer queue
        }
        return messageSent;
    }

    private static XMPPService getCurrentXMPPService() {
        return testService != null ? testService : XMPPServiceFactory.getXMPPService();
    }
}
