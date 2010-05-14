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

class XMPPBotTest extends FunctionalTest with ShouldMatchersForJUnit
                                                     with Browser
                                                     with play.test.Matchers {
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  @Test def pending {true should not be (false)}
 
  // when a non command sent, send usage message

  // when a /watch command send, and the term to the list

  // when a /unwatch command send, remove term from list

  // when a /pause command sent, mark as silent

  // when a /tweet command sent, tweet the message

  // when a /replies command sent, add replies to the list

  // when a /noreplies command send, remove replies from list

  // when /disabled command sent, silent until any command received

  // when disabled, re-enabled when any command received.
}
