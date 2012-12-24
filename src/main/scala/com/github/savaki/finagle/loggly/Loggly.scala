package com.github.savaki.finagle.loggly

import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.Service
import util.Json
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.conversions.time._
import org.jboss.netty.buffer.ChannelBuffers
import com.twitter.util.Future
import java.util.logging.Logger

/**
 * @author matt.ho@gmail.com
 */
class Loggly(inputKey: String, service: Service[HttpRequest, HttpResponse] = Loggly.service) {
  private[this] val logger: Logger = Logger.getLogger("loggly")

  private[this] val uri: String = "/inputs/%s".format(inputKey)

  def log(message: AnyRef): Future[Unit] = {
    val json = Json.writeValueAsString(message)
    val bytes = json.getBytes("UTF-8")
    val request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri)
    request.setHeader("Host", Loggly.host)
    request.setHeader("Content-Type", "application/json")
    request.setHeader("Content-Length", bytes.length)
    request.setContent(ChannelBuffers.wrappedBuffer(bytes))

    service(request).map {
      response => {
        if (response.getStatus != HttpResponseStatus.OK) {
          val contentLength = HttpHeaders.getContentLength(response).toInt
          val content: Array[Byte] = response.getContent.toByteBuffer.array()
          val data = java.util.Arrays.copyOfRange(content, content.length - contentLength, content.length)
          val json: String = new String(data, "UTF-8")
          logger.warning("ERROR!  received %s while transmitting to loggly -- %s" format(response.getStatus.toString, json))
        }

        ()
      }
    }
  }
}

object Loggly {
  val host = "logs.loggly.com"
  val service = ClientBuilder()
    .codec(Http())
    .hosts("%s:443" format host)
    .tls(host)
    .tcpConnectTimeout(15.seconds)
    .connectTimeout(15.seconds)
    .timeout(120.seconds)
    .hostConnectionLimit(1024)
    //    .reportTo(statsReceiver)
    .build()
}
