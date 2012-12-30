package com.github.savaki.finagle.loggly

import reflect.BeanProperty

/**
 * @author matt.ho@gmail.com
 */
class LogglyTest extends TestSuite {
  "Loggly" should "encode json objects" in {
    val loggly = new Loggly(inputKey)
    val message = new Message
    message.message = "hello world"
    loggly.submit(message).get()
  }
}

class Message {
  @BeanProperty
  var message: String = null
}
