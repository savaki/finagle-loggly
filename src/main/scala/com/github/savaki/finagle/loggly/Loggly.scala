package com.github.savaki.finagle.loggly

import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.conversions.time._
import com.twitter.util.Future

/**
 * @author matt.ho@gmail.com
 */
class Loggly(inputKey: String,
             protected val service: Service[HttpRequest, HttpResponse] = Loggly.service) extends JsonClient {
  private[this] val uri: String = "/inputs/%s".format(inputKey)

  protected def host = Loggly.host

  def submit(message: AnyRef): Future[Unit] = {
    post[Unit](uri, message)
  }
}

object Loggly {
  val host = "logs.loggly.com"

  val service = newClient(host)

  def newClient(hostname: String, tls: Boolean = true): Service[HttpRequest, HttpResponse] = {
    val template = ClientBuilder()
      .codec(Http())
      .tcpConnectTimeout(15.seconds)
      .connectTimeout(15.seconds)
      .timeout(120.seconds)
      .hostConnectionLimit(1024)

    if (tls) {
      val hosts: String = "%s:443" format hostname
      template
        .hosts(hosts)
        .tls(hostname)
        .build()
    } else {
      val hosts: String = "%s:80" format hostname
      template
        .hosts(hosts)
        .build()
    }
  }
}
