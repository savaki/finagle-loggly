package com.github.savaki.finagle.loggly.util

import com.github.savaki.finagle.loggly.TestSuite
import reflect.BeanProperty
import org.codehaus.jackson.JsonNode

/**
 * @author matt.ho@gmail.com
 */
class JsonTest extends TestSuite {
  "#readValue" should "deserialize JsonNode fields" in {
    val json =
      """
        |{
        | "data":[
        |   {"name":"alice"},
        |   {"name":"bob"},
        |   {"name":"charles"},
        |   {"name":"david"}
        | ]
        |}
      """.stripMargin

    val sample: Sample = Json.readValue(json, classOf[Sample])
    sample.data should not(be(null))
    sample.data.length should be(4)
    sample.data.map(node => node.get("name").asText()) should be(Array("alice", "bob", "charles", "david"))
  }
}

class Sample {
  @BeanProperty
  var data: Array[JsonNode] = null
}
