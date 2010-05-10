import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
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
}