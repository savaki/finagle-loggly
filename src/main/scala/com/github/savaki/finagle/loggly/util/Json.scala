package com.github.savaki.finagle.loggly.util

import org.codehaus.jackson.{JsonGenerator, Version, JsonNode}
import org.codehaus.jackson.map.{SerializerProvider, JsonSerializer, ObjectMapper}
import org.codehaus.jackson.map.module.SimpleModule
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion
import java.util

/**
 * @author matt.ho@gmail.com
 */
class MapSerializer extends JsonSerializer[Map[_, _]] {
  def serialize(map: Map[_, _], generator: JsonGenerator, provider: SerializerProvider) {
    val javaMap = new util.HashMap[Object, Object]()
    map.foreach {
      entry => javaMap.put(entry._1.asInstanceOf[AnyRef], entry._2.asInstanceOf[AnyRef])
    }
    generator.writeObject(javaMap)
  }
}

/**
 * @author matt.ho@gmail.com
 */
object Json {
  private[this] val mapper: ObjectMapper = {
    val m: ObjectMapper = new ObjectMapper()

    val module: SimpleModule = new SimpleModule("finagle-loggly", new Version(1, 0, 0, ""))
    module.addSerializer(classOf[Map[_, _]], new MapSerializer())

    m.registerModule(module)
    m.setSerializationInclusion(Inclusion.NON_NULL)
    m
  }


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
