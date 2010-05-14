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

class EndpointStreamerTest extends FunctionalTest with ShouldMatchersForJUnit
                                                     with Browser
                                                     with play.test.Matchers {
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  @Test def pending {true should not be(false)}
 
  // when a watched tweet comes in, it should be sent to the people watching it

  // When a list of watched terms requested, it should return the terms.

  // When a reply tweet comes in, it should send it to the listening user, if enabled

  // when a list of people watching replies request comes in, it should list those people
}
