ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.13.0"

lazy val root = (project in file("."))
  .settings(
    name := "clase4"
  )
