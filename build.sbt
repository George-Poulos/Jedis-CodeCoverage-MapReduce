name := "george_poulos_hw2"

version := "2.9.0-SNAPSHOT"

scalaVersion := "2.12.3"


lazy val dependencies = Seq(
  "org.apache.commons" % "commons-pool2" % "2.4.2",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "org.slf4j" % "slf4j-api" % "1.7.25"
)

lazy val makeReport = TaskKey[Unit]("makeReport", "Deletes a cache file")

makeReport := {
  import sys.process._
  Seq("sbt", "clean", "compile", "test")!
  import sys.process._
  Seq("gradle", "clean", "build", "cloverGenerateReport")!
}


lazy val compileSettings = javaSource in Compile := baseDirectory.value / "src" / "main"
lazy val testSettings = javaSource in Test := baseDirectory.value / "src"  / "test"
lazy val root = (project in file ("."))
  .settings(compileSettings, testSettings)
  .settings(libraryDependencies ++= dependencies)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
