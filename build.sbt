name := "a-day-with-sbt"
organization := "se.marcuslonnberg"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.4"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "de.heikoseeberger" %% "akka-http-play-json" % "1.5.3"
)

initialCommands in console :=
  """
    |import se.marcuslonnberg.adaywithsbt.Sorter
  """.stripMargin

/*
 * sbt-buildinfo
 */

enablePlugins(BuildInfoPlugin)
buildInfoKeys := Seq[BuildInfoKey](
  name,
  scalaVersion,
  sbtVersion,
  BuildInfoKey("commitId" -> git.gitHeadCommit.value.get),
  BuildInfoKey("branch" -> git.gitCurrentBranch.value)
)
buildInfoPackage := "se.marcuslonnberg.adaywithsbt"

/*
 * sbt-git
 */

enablePlugins(GitVersioning)
// Remove version above

/*
 * sbt-native-packager
 */

enablePlugins(JavaAppPackaging)

/*
 * sbt-docker
 */

enablePlugins(sbtdocker.DockerPlugin)

dockerfile in docker := {
  val source = stage.value
  val appDir = "/app"
  new Dockerfile {
    from("java:openjdk-8-jre")
    expose(8080)
    workDir(appDir)
    volume(appDir + "/logs")
    entryPoint(s"$appDir/bin/${executableScriptName.value}")
    copy(source, appDir)
  }
}

imageNames in docker := Seq(
  ImageName("marcuslonnberg/a-day-with-sbt:" + git.gitCurrentBranch.value),
  ImageName("marcuslonnberg/a-day-with-sbt:" + git.gitHeadCommit.value.get)
)
