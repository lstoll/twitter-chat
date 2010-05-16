import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._
import com.google.appengine.tools.development.testing._
import com.google.appengine.api.datastore._

import models._

class UserTest extends UnitTest with ShouldMatchersForJUnit {
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  @Before def setUp = {
    helper.setUp
  }

  @After def tearDown = {
    helper.tearDown
  }

  @Test def createAndGetUserByXMPPID() {
    var u = new User()
    u.xmppID = "l@lds.li"
    u.save

    // now get the user
    var getUser = User.query.byXmppId("l@lds.li")
    
    getUser should not be (null)
    getUser.xmppID should equal (u.xmppID)

    // Makes sure we aren't leaking - will fail on subsequent runs
    var ds = DatastoreServiceFactory.getDatastoreService
    ds.prepare(new Query("User")).countEntities should equal (1)
  }


  @Test def userShouldNotBeRegisteredWihoutXMPPIDandOauth {
    var u = new User()
    u.xmppID = "l@lds.li"
    u.save

    u.isActive should be (false)

    u.twitterToken = "aa"
    u.twitterTokenSecret = "xx"

    u.save

    u.isActive should be (true)
  }

  @Test def needsOauthReminder {
    // if isn't active and hasn't been prompted in the last hour, re-prompt
    var u = new User()
    u.xmppID = "l@lds.li"
    u.save

    // we need to be reminded
    User.query.needsReminder().countAll should equal (1)

    // set their last remind time to now.
    u.lastReminded = new java.util.Date()
    u.save

    // should no longer need reminder
    User.query.needsReminder().countAll should equal (0)
  }

  /*@Test def addRemoveWatchToExistingUser {
    var ds = DatastoreServiceFactory.getDatastoreService
    ds.prepare(new Query("Watch")).countEntities should equal (0)

    var u = new User()
    u.xmppID = "l@lds.li"

    u.insert

    u.addWatch("testterm")

    ds.prepare(new Query("Watch")).countEntities should equal (1)
    Watch.all.get.getTerm should equal ("testterm")

    u.removeWatch("testterm")

    ds.prepare(new Query("Watch")).countEntities should equal (0)
  }

  @Test def addRemoveWatchExisting {
    var ds = DatastoreServiceFactory.getDatastoreService
    ds.prepare(new Query("Watch")).countEntities should equal (0)

    var u1 = new User()
    u1.xmppID = "1@u.com"
    u1.insert
    var u2 = new User()
    u2.xmppID = "2@u.com"
    u2.insert

    u1.addWatch("term")
    ds.prepare(new Query("Watch")).countEntities should equal (1)

    u2.addWatch("term")
    ds.prepare(new Query("Watch")).countEntities should equal (1)

    u2.removeWatch("term")
    ds.prepare(new Query("Watch")).countEntities should equal (1)

    u1.removeWatch("term");
    ds.prepare(new Query("Watch")).countEntities should equal (0)
  }*/
}
