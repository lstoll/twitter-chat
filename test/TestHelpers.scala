import java.util.HashMap
import java.util.Map
import play.mvc.Http
import play.test.FunctionalTest
import com.google.appengine.tools.development.testing._
import scala.collection.JavaConversions._

trait TestHelpers {
  var playGAEDevEnv:com.google.apphosting.api.ApiProxy.Environment  = null
  var helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  /**
  * Sets up a proper GAE test environment
  */
  def gaeSetUp = {
    helper.setUp
    //play.modules.gae.GAEPlugin.
    play.Play.plugins.foreach { p =>
      if (p.isInstanceOf[play.modules.gae.GAEPlugin]) {
        var pin = p.asInstanceOf[play.modules.gae.GAEPlugin]
        playGAEDevEnv = pin.devEnvironment
        pin.devEnvironment = null
      }
    }
    
  }

  /**
  * Tears down the GAE test environment
  */
  def gaeTearDown = {
    helper.tearDown
    play.Play.plugins.foreach { p =>
      if (p.isInstanceOf[play.modules.gae.GAEPlugin]) {
        var pin = p.asInstanceOf[play.modules.gae.GAEPlugin]
        pin.devEnvironment = playGAEDevEnv
      }
    }
  }
  
  /**
  * Simulates an incoming XMPP Request
  * @param from sender of message
  * @param msg message text to send
  * @return Http.Response from the request
  */
  def doXmppRequest(from:String, msg:String):Http.Response = {
    var files:Map[String, java.io.File] = new HashMap[String, java.io.File]
    var params:Map[String, String] = new HashMap[String, String]
    params.put("to", "twitter-chat@appspot.com")
    params.put("from", from)
    params.put("body", msg)
    
    FunctionalTest.POST("/_ah/xmpp/message/chat/", params, files);
  }  
}
