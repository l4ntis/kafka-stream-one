thisProject / name := "Kafka-stream-one"
thisProject / organization := "com.l4ntis"
thisProject / scalaVersion := "2.13.7"

ThisBuild / version := "1.0.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    Compile / scalacOptions += "-Xlint",
    Compile / console / scalacOptions --= Seq("-Ywarn-unused", "-Ywarn-unused-import"),
    libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.8.1" ,
    libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.32",
    libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.2",
    Test / fork := true
  )