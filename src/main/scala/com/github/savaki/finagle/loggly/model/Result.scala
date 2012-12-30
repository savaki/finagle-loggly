package com.github.savaki.finagle.loggly.model

import reflect.BeanProperty
import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.annotate.JsonProperty

/**
 * @author matt.ho@gmail.com
 */
class Result {
  @BeanProperty
  var numFound: Int = 0

  @BeanProperty
  var gap: String = null

  @BeanProperty
  var start: Int = 0

  @BeanProperty
  @JsonProperty("gmt_offset")
  var gmtOffset: String = null

  @BeanProperty
  var context: Context = null

  @BeanProperty
  var data: JsonNode = null
}
