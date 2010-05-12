import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import org.junit.*;
import play.test.*;
import models.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.Date;

public class UserTest extends UnitTest {

    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void createAndGetUserByXMPPID() {
        User u = new User();
        u.setXmppID("l@lds.li");
        u.insert();

        // now get the user
        User getUser = User.byXmppId("l@lds.li");
        assertNotNull(getUser);
        assertEquals(getUser.getXmppID(), u.getXmppID());

        // Makes sure we aren't leaking - will fail on subsequent runs
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(1, ds.prepare(new Query("User")).countEntities());
    }

    @Test
    public void userShouldNotBeRegisteredWihoutXMPPIDandOauth() {
        User u = new User();
        u.setXmppID("l@lds.li");
        u.insert();

        assertFalse(u.isActive());

        u.setTwitterToken("aa");
        u.setTwitterTokenSecret("xx");

        u.update();

        assertTrue(u.isActive());
    }

    @Test
    public void needsOauthReminder() {
        // if isn't active and hasn't been prompted in the last hour, re-prompt
        User u = new User();
        u.setXmppID("l@lds.li");
        u.insert();

        // we need to be reminded
        assertEquals(1, User.needsReminder().size());

        // set their last remind time to now.
        u.setLastReminded(new Date());
        u.update();

        // should no longer need reminder
        assertEquals(0, User.needsReminder().size());
    }

    @Test
    public void addRemoveWatchToExistingUser() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(0, ds.prepare(new Query("Watch")).countEntities());

        User u = new User();
        u.setXmppID("l@lds.li");

        u.insert();

        u.addWatch("testterm");

        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());
        assertEquals("testterm", Watch.all(Watch.class).get().getTerm());

        u.removeWatch("testterm");

        assertEquals(0, ds.prepare(new Query("Watch")).countEntities());
    }

    @Test
    public void addRemoveWatchExisting() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(0, ds.prepare(new Query("Watch")).countEntities());

        User u1 = new User();
        u1.setXmppID("1@u.com");
        u1.insert();
        User u2 = new User();
        u2.setXmppID("2@u.com");
        u2.insert();

        u1.addWatch("term");
        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());

        u2.addWatch("term");
        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());

        u2.removeWatch("term");
        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());

        u1.removeWatch("term");
        assertEquals(0, ds.prepare(new Query("Watch")).countEntities());
    }

}
