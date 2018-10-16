import sbt._

object Dependencies {

  val playVersion = "2.4.3"
  val playWS = "com.typesafe.play" %% "play-ws" % playVersion
  val playJson_211 = "com.typesafe.play" %% "play-json" % playVersion

  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % "test"

  // 2.11 -> WS 2.4.3
  // 2.12 -> 2.6 most recent

  def playWS(scalaVersion: String): ModuleID = withScalaVersion(scalaVersion)(
    playWSjson,
    playJson_211
  )

  private def withScalaVersion(scalaVersion: String)(
      scala212: ModuleID,
      scalaFallback: ModuleID): ModuleID = {
    if (scalaVersion.startsWith("2.12")) scala212 else scalaFallback
  }

}
