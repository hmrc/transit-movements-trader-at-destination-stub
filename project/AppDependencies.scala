import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"       %% "bootstrap-play-26"   % "1.13.0"
  )

  val test = Seq(
    "org.mockito"            % "mockito-core"          % "3.3.3",
    "org.scalatest"          %% "scalatest"            % "3.2.0",
    "com.typesafe.play"      %% "play-test"            % current,
    "org.pegdown"            % "pegdown"               % "1.6.0",
    "org.scalatestplus.play" %% "scalatestplus-play"   % "3.1.3",
    "org.scalatestplus"      %% "mockito-3-2"          % "3.1.2.0",
    "org.scalacheck"         %% "scalacheck"           % "1.14.3",
    "com.github.tomakehurst" % "wiremock-standalone"   % "2.27.1",
    "com.vladsch.flexmark"   % "flexmark-all"          % "0.35.10"
  ).map(_ % "test")
}
