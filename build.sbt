name := "ScalaKafkaDemoApp"

version := "0.1"

scalaVersion := "2.13.1"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
libraryDependencies += "com.typesafe.play" % "play-json_2.11" % "2.4.6"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"
libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.1"
