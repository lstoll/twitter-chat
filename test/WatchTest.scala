import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._
import com.google.appengine.tools.development.testing._
import com.google.appengine.api.datastore._

import models._

class WatchTest extends UnitTest with ShouldMatchersForJUnit {
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  @Before def setUp = {
    helper.setUp
  }

  @After def tearDown = {
    helper.tearDown
  }

  @Test def findOrCreateTest {
    var ds = DatastoreServiceFactory.getDatastoreService
    ds.prepare(new Query("Watch")).countEntities should equal (0)

    var w1 = Watch.findOrCreate("term")
    w1 should not be (null)
    ds.prepare(new Query("Watch")).countEntities should equal (1)

    var w2 = Watch.findOrCreate("term")
    w2 should not be (null)
    ds.prepare(new Query("Watch")).countEntities should equal (1)
  }

  @Test def usersForTermTest {
    var ds = DatastoreServiceFactory.getDatastoreService

    var u1 = new User
    u1.xmppID = "1@u.com"
    u1.insert
    var u2 = new User
    u2.xmppID = "2@u.com"
    u2.insert

    u1.addWatch("term")
    u2.addWatch("term")
        
    ds.prepare(new Query("Watch")).countEntities should equal (1)

    var w = Watch.byTerm("term")
    w.users().size should equal (2)
  }
}
