import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-27"            % "5.12.0",
    "uk.gov.hmrc"       %% "bootstrap-backend-play-27"             % "5.12.0",
    "uk.gov.hmrc"       %% "play-nunjucks"                 % "0.29.0-play-26",
    "uk.gov.hmrc"       %% "play-nunjucks-viewmodel"       % "0.14.0-play-26",
    "org.webjars.npm"   % "govuk-frontend"                 % "3.13.0",
    "wolfendale"        %% "scalacheck-gen-regexp"         % "0.1.2"
  )

  val test = Seq(
    "org.mockito"            % "mockito-core"              % "3.12.3",
    "org.scalatest"          %% "scalatest"                % "3.2.9",
    "com.typesafe.play"      %% "play-test"                % current,
    "org.jsoup"              %  "jsoup"                    % "1.14.2",
    "org.scalatestplus.play" %% "scalatestplus-play"       % "4.0.3",
    "org.scalatestplus"      %% "mockito-3-2"              % "3.1.2.0",
    "org.scalacheck"         %% "scalacheck"               % "1.15.4",
    "org.scalatestplus"      %% "scalatestplus-scalacheck" % "3.1.0.0-RC2",
    "com.github.tomakehurst" % "wiremock-standalone"       % "2.27.2",
    "com.vladsch.flexmark"   % "flexmark-all"              % "0.35.10"
  ).map(_ % "test")
}
