ThisBuild / name := "clase3"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
lazy val root = (project in file("."))
  .settings(
    name := "clase3"
  )
