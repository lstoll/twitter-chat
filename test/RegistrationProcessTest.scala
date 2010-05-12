import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._
import com.google.appengine.tools.development.testing._
import com.google.appengine.api.datastore._
import java.util.{HashMap, Map}
import play.mvc.Http._

import utils.TestXMPPService
import utils.XMPPSend
import models._

class RegistrationProcessTest extends FunctionalTest with ShouldMatchersForJUnit
                                                     with Browser
                                                     with play.test.Matchers {
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  @Before def setUp {
        helper.setUp
        XMPPSend.testSetUp
    }

    @After def tearDown {
        helper.tearDown
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
      var files:Map[String, java.io.File] = new HashMap[String, java.io.File]
      var params:Map[String, String] = new HashMap[String, String]
      params.put("jid", "l@lds.li")
      var resp = POST("/Register/submitJid", params, files);

      TestXMPPService.lastRequestInvitation should be (true)

      var ds = DatastoreServiceFactory.getDatastoreService
      ds.prepare(new Query("User")).countEntities should equal (1)
    }

    // Test that a user gets a welcome XMPP page with a link to the oauth page
    @Test def testNewUserWelcomeLinkXMPP {
      var u = new User();
      u.xmppID = "l@lds.li"
      u.insert

      GET("/Cron/oauthCheck")

      TestXMPPService.lastRequestJID should equal ("l@lds.li")
      // TODO - update with the expected ID!
      TestXMPPService.lastMessage.getBody().contains("Registration/oauth") should
              be (true)
    }

    // Test that when OAUTH returns, user is sent to the usage page
}
