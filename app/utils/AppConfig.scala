package utils

import java.net.URL
import java.util.Properties
import java.io.FileInputStream
import java.io.File


object AppConfig {
  var testMode = false
  
  def getProp(name:String):String = {
    var propFileName = "app.properties"
    if (testMode) {
      propFileName += ".test"
    }
    var url = ClassLoader.getSystemResource(propFileName)
    var p = new Properties
    p.load( url.openStream() )
    p.getProperty(name)
  }
}
