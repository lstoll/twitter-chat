import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import org.junit.*;
import play.test.*;
import models.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

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
    public void createAndGetUserByOpenID() {
        User u = new User();
        u.openID = "http://google.com";
        u.insert();

        // now get the user
        User getUser = User.byOpenID("http://google.com");
        assertNotNull(getUser);
        assertEquals(getUser.openID, u.openID);

        // Makes sure we aren't leaking - will fail on subsequent runs
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(1, ds.prepare(new Query("User")).countEntities());
    }

}
