package com.github.savaki.finagle.loggly.util

import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.map.ObjectMapper

/**
 * @author matt.ho@gmail.com
 */
object Json {
  private[this] val mapper = new ObjectMapper()

  def writeValueAsString(value: AnyRef): String = {
    mapper.writeValueAsString(value)
  }

  def readValue[T](json: String, klass: Class[T]): T = {
    mapper.readValue(json, klass)
  }

  def readValue[T](node: JsonNode, klass: Class[T]): T = {
    mapper.readValue(node, klass)
  }
}
