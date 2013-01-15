name := "finagle-loggly"

organization := "com.github.savaki"

version := "0.1"

resolvers += "Twitter Repo" at "http://maven.twttr.com"

resolvers += "OSS Sonatype" at "https://oss.sonatype.org/content/groups/public/"

{
    val finagleVersion = "5.3.9"
    libraryDependencies ++= Seq(
        "org.codehaus.jackson" % "jackson-core-asl" % "1.9.9",
        "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.9",
        "com.twitter" % "finagle-core" % finagleVersion withSources(),
        "com.twitter" % "finagle-native" % finagleVersion withSources(),
        "com.twitter" % "finagle-http" % finagleVersion withSources()
    )
}

{
    libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.9.1" % "test" withSources()
    )
}

scalaVersion := "2.9.2"

