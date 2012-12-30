package com.github.savaki.finagle.loggly

import java.io.{File, FileInputStream}
import java.util.Properties
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

/**
 * @author matt.ho@gmail.com
 */
class TestSuite extends FlatSpec with ShouldMatchers {
  def inputKey = TestSuite.inputKey

  def username = TestSuite.username

  def password = TestSuite.password

  def domain = TestSuite.domain
}

object TestSuite {
  lazy val properties: Properties = {
    val inputStream = new FileInputStream(new File(System.getProperty("user.home"), "finagle-loggly.properties"))
    val properties = new Properties()
    properties.load(inputStream)
    inputStream.close()
    properties
  }

  lazy val inputKey = properties.getProperty("loggly.input_key")

  lazy val username = properties.getProperty("loggly.username")

  lazy val password = properties.getProperty("loggly.password")

  lazy val domain = properties.getProperty("loggly.domain")
}
