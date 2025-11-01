ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
lazy val root = (project in file("."))
  .settings(
    name := "clase5"
  )
