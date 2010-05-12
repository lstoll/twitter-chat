import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import models.User;
import models.Watch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lstoll
 */
public class WatchTest extends UnitTest {

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
    public void findOrCreateTest() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(0, ds.prepare(new Query("Watch")).countEntities());

        Watch w1 = Watch.findOrCreate("term");
        assertNotNull(w1);
        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());

        Watch w2 = Watch.findOrCreate("term");
        assertNotNull(w2);
        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());

    }

    @Test
    public void usersForTermTest() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        User u1 = new User();
        u1.setXmppID("1@u.com");
        u1.insert();
        User u2 = new User();
        u2.setXmppID("2@u.com");
        u2.insert();

        u1.addWatch("term");
        u2.addWatch("term");
        
        assertEquals(1, ds.prepare(new Query("Watch")).countEntities());

        Watch w = Watch.byTerm("term");
        assertEquals(2, w.users().size());
    }
}