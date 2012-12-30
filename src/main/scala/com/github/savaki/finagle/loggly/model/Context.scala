package com.github.savaki.finagle.loggly.model

import reflect.BeanProperty

/**
 * @author matt.ho@gmail.com
 */
class Context {
  @BeanProperty
  var rows: Int = 0

  @BeanProperty
  var from: String = null

  @BeanProperty
  var until: String = null

  @BeanProperty
  var start: Int = 0

  @BeanProperty
  var query: String = null

  @BeanProperty
  var order: String = null
}
