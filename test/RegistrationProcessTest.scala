import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._
import com.google.appengine.tools.development.testing._
import com.google.appengine.api.datastore._
import java.util.{HashMap, Map}
import play.mvc.Http._
import scala.collection.JavaConversions._

import models._
import utils.TestXMPPService
import utils.XMPPSend

class RegistrationProcessTest extends FunctionalTest with ShouldMatchersForJUnit
                                                     with Browser
                                                     with play.test.Matchers {
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  var playGAEDevEnv:com.google.apphosting.api.ApiProxy.Environment  = null
  
  @Before def setUp {
    helper.setUp
    //play.modules.gae.GAEPlugin.
    play.Play.plugins.foreach { p =>
      if (p.isInstanceOf[play.modules.gae.GAEPlugin]) {
        var pin = p.asInstanceOf[play.modules.gae.GAEPlugin]
        playGAEDevEnv = pin.devEnvironment
        pin.devEnvironment = null
      }
    }
    XMPPSend.testSetUp

  }
  
  @After def tearDown {
    helper.tearDown
    play.Play.plugins.foreach { p =>
      if (p.isInstanceOf[play.modules.gae.GAEPlugin]) {
        var pin = p.asInstanceOf[play.modules.gae.GAEPlugin]
        pin.devEnvironment = playGAEDevEnv
      }
    }
    XMPPSend.testTearDown
  }
  
  @Test def testThatIndexPageWorks {
    var response = GET("/")
    response.shouldBeOk
    response contentTypeShouldBe "text/html"
    response charsetShouldBe "utf-8"
  }
  
  // Test that a user submitting a email address results in an XMPP invitiation
  @Test def testJidSubmissionInviteAndCreate {
    var ds = DatastoreServiceFactory.getDatastoreService
    ds.prepare(new Query("User")).countEntities should equal (0)
    
    var files:Map[String, java.io.File] = new HashMap[String, java.io.File]
    var params:Map[String, String] = new HashMap[String, String]
    params.put("jid", "l@lds.li")
    var resp = POST("/Register/submitJid", params, files)
    
    TestXMPPService.lastRequestInvitation should be (true)
    
    ds.prepare(new Query("User")).countEntities should equal (1)
    TestXMPPService.reset
  }
  
  // Test that a user gets a welcome XMPP page with a link to the oauth page
  @Test def testNewUserWelcomeLinkXMPP {
    var ds = DatastoreServiceFactory.getDatastoreService
    ds.prepare(new Query("User")).countEntities should equal (0)
    
    var u = new User
    u.xmppID = "l@lds.li"
    u.save

    User.query.needsReminder.countAll should equal(1)

    var response = GET("/Cron/oauthCheck")
    response shouldBeOk
    
    ds.prepare(new Query("User")).countEntities should equal (1)
    
    TestXMPPService.lastRequestJID should equal ("l@lds.li")
    TestXMPPService.lastMessage should not equal (null)
    // TODO - match URL
    TestXMPPService.lastMessage.getBody().contains("twitter") should be (true)
  }
  
  // Test that when OAUTH returns, user is sent to the usage page
}
