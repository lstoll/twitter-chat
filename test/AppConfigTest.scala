import play.test._

import org.junit._
import org.scalatest.junit._
import org.scalatest._
import org.scalatest.matchers._

import utils.AppConfig

class AppConfigTest extends UnitTest with ShouldMatchersForJUnit {

  @Before def setUp = {
    AppConfig.testMode = true
  }

  @After def tearDown = {
    AppConfig.testMode = false
  }

  @Test def testReadProperty {
    AppConfig.getProp("test.prop") should equal ("testvalue")
  }
}
