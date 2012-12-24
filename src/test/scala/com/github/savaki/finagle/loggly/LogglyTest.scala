package com.github.savaki.finagle.loggly

import org.scalatest.FlatSpec
import reflect.BeanProperty
import java.io.{File, FileInputStream}
import java.util.Properties

/**
 * @author matt.ho@gmail.com
 */
class LogglyTest extends FlatSpec {
  val inputKey = {
    val inputStream = new FileInputStream(new File(System.getProperty("user.home"), "finagle-loggly.properties"))
    val properties = new Properties()
    properties.load(inputStream)
    inputStream.close()
    properties.getProperty("input_key")
  }

  "Loggly" should "encode json objects" in {
    val loggly = new Loggly(inputKey) {
      protected def service = null
    }

    val message = new Message
    message.message = "hello world"
    loggly.log(message).get()
  }
}

class Message {
  @BeanProperty
  var message: String = null
}
