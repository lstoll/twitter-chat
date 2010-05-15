import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._
import com.google.appengine.tools.development.testing._
import com.google.appengine.api.datastore._

import models._
import utils._

class UtilsTest extends UnitTest with ShouldMatchersForJUnit {

  @Before def setUp = {
    AppConfig.testMode = true
  }

  @After def tearDown = {
    AppConfig.testMode = false
  }

  @Test def saltedHashedStringTest() {
    val hstr = "10595565031accb918cc90affd566c28cfa271be"
    utils.Hashing.hashSaltString("TESTSTRING") should equal (hstr)
  }
}
