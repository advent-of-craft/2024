ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.4.2"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "io.cucumber" %% "cucumber-scala" % "8.2.0" % Test,
  "io.cucumber" % "cucumber-junit" % "7.7.0" % Test,
  "io.cucumber" % "cucumber-java" % "7.7.0" % Test
)