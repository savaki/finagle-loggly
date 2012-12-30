package com.github.savaki.finagle.loggly.model

/**
 * @author matt.ho@gmail.com
 */
case class FacetsRequest(query: String,
                         from: String = null,
                         until: String = null,
                         buckets: String = null,
                         gap: String = null,
                         facetBy: String = null,
                         callback: String = null,
                         format: String = null) {
  def toMap: Map[String, String] = {
    var result: Map[String, String] = Map("q" -> query)

    if (from != null) {
      result = Map("from" -> from) ++ result
    }

    if (until != null) {
      result = Map("until" -> until) ++ result
    }

    if (buckets != null) {
      result = Map("buckets" -> buckets) ++ result
    }

    if (gap != null) {
      result = Map("gap" -> gap) ++ result
    }

    if (facetBy != null) {
      result = Map("facetby" -> facetBy) ++ result
    }

    if (callback != null) {
      result = Map("callback" -> callback) ++ result
    }

    if (format != null) {
      result = Map("format" -> format) ++ result
    }

    result
  }

  def uri: String = {
    facetBy match {
      case "ip" => "/api/facets/ip/"
      case "inputname" => "/api/facets/input/"
      case _ => "/api/facets/date/"
    }
  }
}


