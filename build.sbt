///
/// ThisBuild -- applies to subprojects too
///

scalaVersion in ThisBuild := "2.10.2"

scalacOptions in ThisBuild ++=
  "-deprecation -unchecked -feature -Xcheckinit -encoding us-ascii -target:jvm-1.7 -Xlint -Xfatal-warnings"
  .split(" ").toSeq

javacOptions in ThisBuild ++=
  "-g -deprecation -encoding us-ascii -Werror -Xlint:all -Xlint:-serial -Xlint:-fallthrough -Xlint:-path -source 1.7 -target 1.7"
  .split(" ").toSeq

// only log problems plz
ivyLoggingLevel in ThisBuild := UpdateLogging.Quiet

// this makes script-writing easier
retrieveManaged in ThisBuild := true

// we're not cross-building for different Scala versions
crossPaths in ThisBuild := false

nogen in ThisBuild  := { System.setProperty("org.nlogo.noGenerator", "true") }

libraryDependencies in ThisBuild ++= Seq(
  "asm" % "asm-all" % "3.3.1",
  "org.jmock" % "jmock" % "2.5.1" % "test",
  "org.jmock" % "jmock-legacy" % "2.5.1" % "test",
  "org.jmock" % "jmock-junit4" % "2.5.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
  "org.scalatest" %% "scalatest" % "2.0.M5b" % "test"
)

name := "NetLogo"

artifactName := { (_, _, _) => "NetLogoHeadless.jar" }

onLoadMessage := ""

resourceDirectory in Compile <<= baseDirectory(_ / "resources")

scalaSource in Compile <<= baseDirectory(_ / "src" / "main")

scalaSource in Test <<= baseDirectory(_ / "src" / "test")

javaSource in Compile <<= baseDirectory(_ / "src" / "main")

javaSource in Test <<= baseDirectory(_ / "src" / "test")

unmanagedResourceDirectories in Compile <+= baseDirectory { _ / "resources" }

sourceGenerators in Compile <+= JFlexRunner.task

resourceGenerators in Compile <+= I18n.resourceGeneratorTask

mainClass in Compile := Some("org.nlogo.headless.Main")

Extensions.extensionsTask

all := { () }

all <<= all.dependsOn(
  packageBin in Compile,
  compile in Test,
  Extensions.extensions)

seq(Testing.settings: _*)

seq(Depend.settings: _*)

seq(Classycle.settings: _*)

seq(Dump.settings: _*)

seq(ChecksumsAndPreviews.settings: _*)

// supported yet in sbt 0.13?
// org.scalastyle.sbt.ScalastylePlugin.Settings
