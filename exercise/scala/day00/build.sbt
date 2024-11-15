ThisBuild / scalaVersion := "3.3.0" // Scala 3 version

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.15" % "test",
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"
)