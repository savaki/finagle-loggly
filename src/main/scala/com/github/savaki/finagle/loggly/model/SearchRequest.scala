package com.github.savaki.finagle.loggly.model

import reflect.BeanProperty
import org.codehaus.jackson.JsonNode

/**
 * @author matt.ho@gmail.com
 */
case class SearchRequest(query: String,
                         rows: Int = 10,
                         start: Int = 0,
                         from: String = null,
                         until: String = null,
                         order: String = null,
                         callback: String = null,
                         format: String = null,
                         fields: String = null) {
  def toMap: Map[String, String] = {
    var result: Map[String, String] = Map("q" -> query, "rows" -> rows.toString, "start" -> start.toString)

    if (from != null) {
      result = Map("from" -> from) ++ result
    }

    if (until != null) {
      result = Map("until" -> until) ++ result
    }

    if (order != null) {
      result = Map("order" -> order) ++ result
    }

    if (callback != null) {
      result = Map("callback" -> callback) ++ result
    }

    if (format != null) {
      result = Map("format" -> format) ++ result
    }

    if (fields != null) {
      result = Map("fields" -> fields) ++ result
    }

    result
  }
}





