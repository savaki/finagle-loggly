package com.github.savaki.finagle.loggly

import model.{FacetsRequest, Result, SearchRequest}

/**
 * @author matt.ho@gmail.com
 */
class LogglyQueryTest extends TestSuite {
  "#search" should "query loggly" in {
    val loggly = new Loggly(inputKey)
    loggly.submit(Map("hello" -> "world")).get()

    val query = new LogglyQuery(domain, username, password)
    val result: Result = query.search(SearchRequest(query = """ip:98.210.193.120""")).get()

    result should not(be(null))
  }

  "#facets" should "generate summary data" in {
    val loggly = new Loggly(inputKey)
    loggly.submit(Map("hello" -> "world")).get()

    val query = new LogglyQuery(domain, username, password)
    val result: Result = query.facets(FacetsRequest(query = """ip:98.210.193.120""")).get()

    result should not(be(null))
  }
}
