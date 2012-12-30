package com.github.savaki.finagle.loggly

import model.{FacetsRequest, Result, SearchRequest}
import org.jboss.netty.handler.codec.http.{HttpMethod, HttpResponse, HttpRequest}
import com.twitter.finagle.Service
import org.apache.commons.codec.binary.Base64
import com.twitter.util.Future

/**
 * @author matt.ho@gmail.com
 */
class LogglyQuery(domain: String,
                  username: String,
                  password: String,
                  serviceOverride: Service[HttpRequest, HttpResponse] = null) extends JsonClient {

  protected lazy val service = if (serviceOverride != null) serviceOverride else Loggly.newClient(host, tls = false)

  /**
   * @return the host that we intend to connect to
   */
  protected val host = {
    "%s.loggly.com".format(domain)
  }

  protected val authentication: String = {
    "Basic " + new String(Base64.encodeBase64("%s:%s".format(username, password).getBytes(DEFAULT_ENCODING)), DEFAULT_ENCODING)
  }

  override protected def newHttpRequest[T](method: HttpMethod, uri: String, params: Map[String, String]): HttpRequest = {
    val request: HttpRequest = super.newHttpRequest(method, uri, params)
    request.setHeader("Authorization", authentication)
    request
  }

  def search(request: SearchRequest): Future[Result] = {
    get[Result]("/api/search/", request.toMap)
  }

  def facets(request: FacetsRequest): Future[Result] = {
    get[Result](request.uri, request.toMap)
  }
}


