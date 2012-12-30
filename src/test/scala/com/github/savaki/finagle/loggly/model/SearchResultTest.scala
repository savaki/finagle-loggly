package com.github.savaki.finagle.loggly.model

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.savaki.finagle.loggly.util.Json

/**
 * @author matt.ho@gmail.com
 */
class SearchResultTest extends FlatSpec with ShouldMatchers {
  "SearchResult" should "be deserializable from json" in {
    val json =
      """
        |{
        |    "data": [],
        |    "numFound": 0,
        |    "context": {
        |        "rows": "10",
        |        "from": "NOW-1DAY",
        |        "until": "NOW",
        |        "start": "0",
        |        "query": "hello",
        |        "order": "desc"
        |    }
        |}
      """.stripMargin
    val result: Result = Json.readValue(json, classOf[Result])
    result should not(be(null))
  }
}
