import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.HashMap;
import java.util.Map;
import models.User;
import org.junit.*;
import play.mvc.Http;
import play.test.*;
import play.mvc.Http.*;
import utils.TestXMPPService;
import utils.XMPPSend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lstoll
 */
public class RegistrationProcessTest extends FunctionalTest {
    
    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        XMPPSend.testSetUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
        XMPPSend.testTearDown();
    }

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }

    // Test that a user submitting a email address results in an XMPP invitiation
    @Test
    public void testJidSubmissionInviteAndCreate() {
        Map files = new HashMap();
        Map<String, String> params = new HashMap();
        params.put("jid", "l@lds.li");
        Response resp = POST("/Register/submitJid", params, files);

        assertTrue(TestXMPPService.lastRequestInvitation);

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(1, ds.prepare(new Query("User")).countEntities());
    }

    // Test that a user gets a welcome XMPP page with a link to the oauth page
    @Test
    public void testNewUserWelcomeLinkXMPP() {
        User u = new User();
        u.setXmppID("l@lds.li");
        u.insert();

        GET("/Cron/oauthCheck");

        assertEquals("l@lds.li", TestXMPPService.lastRequestJID);
        // TODO - update with the expected ID!
        assertTrue(TestXMPPService.lastMessage.getBody().contains("Registration/oauth"));
    }

    // Test that when OAUTH returns, user is sent to the usage page
}
