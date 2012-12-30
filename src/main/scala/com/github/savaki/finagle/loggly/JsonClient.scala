package com.github.savaki.finagle.loggly

import org.jboss.netty.handler.codec.http._
import com.twitter.util.Future
import java.net.URLEncoder
import util.Json
import org.jboss.netty.buffer.ChannelBuffers
import com.twitter.finagle.Service
import java.util.logging.Logger

/**
 * @author matt.ho@gmail.com
 */
trait JsonClient {
  val DEFAULT_ENCODING: String = "UTF-8"

  private[this] val logger: Logger = Logger.getLogger("loggly")

  private[this] val unitType: Class[Unit] = classOf[Unit]

  protected def service: Service[HttpRequest, HttpResponse]

  /**
   * @return the host that we intend to connect to
   */
  protected def host: String

  protected def updateHttpRequest(request: HttpRequest) {
    // intentionally left blank -- supply your own functionality by overriding this
  }

  def get[T](uri: String, params: Map[String, String] = Map())(implicit t: Manifest[T]): Future[T] = {
    execute[T](HttpMethod.GET, uri, params = params)
  }

  def post[T](uri: String, body: AnyRef)(implicit t: Manifest[T]): Future[T] = {
    execute[T](HttpMethod.POST, uri, body)
  }

  protected def execute[T](method: HttpMethod, uri: String, body: AnyRef = null, params: Map[String, String] = Map())(implicit t: Manifest[T]): Future[T] = {
    val request: HttpRequest = newHttpRequest(method, uri, params)

    if (body != null) {
      val json = Json.writeValueAsString(body)
      val bytes = json.getBytes(DEFAULT_ENCODING)
      request.setHeader("Content-Type", "application/json")
      request.setHeader("Content-Length", bytes.length)
      request.setContent(ChannelBuffers.wrappedBuffer(bytes))
    }

    service(request).map {
      response => {
        val contentLength = HttpHeaders.getContentLength(response).toInt
        val json: String = if (contentLength > 0) {
          val content: Array[Byte] = response.getContent.toByteBuffer.array()
          val data: Array[Byte] = java.util.Arrays.copyOfRange(content, content.length - contentLength, content.length)
          new String(data, DEFAULT_ENCODING)

        } else {
          null
        }

        if (response.getStatus != HttpResponseStatus.OK) {
          logger.warning("ERROR!  received %s while sending to loggly -- %s" format(response.getStatus.toString, json))
          throw new RuntimeException(json)

        } else {
          if (t.erasure == unitType) {
            ().asInstanceOf[T]

          } else {
            Json.readValue[T](json, t.erasure.asInstanceOf[Class[T]])
          }
        }
      }
    }
  }

  protected def newHttpRequest[T](method: HttpMethod, uri: String, params: Map[String, String] = Map()): HttpRequest = {
    val queryString = params.map {
      pair => "%s=%s".format(pair._1, URLEncoder.encode(pair._2, DEFAULT_ENCODING))
    }.mkString("&")
    val uriToUse = if (queryString.isEmpty) uri else uri + "?" + queryString

    val r = new DefaultHttpRequest(HttpVersion.HTTP_1_1, method, uriToUse)
    r.setHeader("Host", host)
    r
  }
}
