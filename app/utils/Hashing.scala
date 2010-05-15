package utils

import java.security.MessageDigest

object Hashing {

  def hashSaltString(str:String):String = {
    var saltstr = str + AppConfig.getProp("hash.salt")
    
    var md5val = ""
    var algorithm = MessageDigest.getInstance("SHA")
    
    var defaultBytes = saltstr.getBytes
    algorithm.reset
    algorithm.update(defaultBytes)
    var messageDigest = algorithm.digest()
    var hexString = new StringBuffer

    for (i <- 0 until messageDigest.length) {
      var hex = Integer.toHexString(0xFF & messageDigest(i))
      if (hex.length == 1) {
        hexString.append('0')
      }
      hexString.append(hex)
    }
    md5val = hexString.toString()
    println(md5val)
    md5val
  }
}
