import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"       %% "bootstrap-backend-play-27"     % "5.0.0",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-27"    % "5.0.0",
    "uk.gov.hmrc"       %% "play-nunjucks"                 % "0.27.0-play-27",
    "uk.gov.hmrc"       %% "play-nunjucks-viewmodel"       % "0.13.0-play-27",
    "org.webjars.npm"   % "govuk-frontend"                 % "3.3.0",
    "wolfendale"        %% "scalacheck-gen-regexp"         % "0.1.1"
  )

  val test = Seq(
    "org.mockito"            % "mockito-core"              % "3.3.3",
    "org.scalatest"          %% "scalatest"                % "3.2.0",
    "com.typesafe.play"      %% "play-test"                % current,
    "org.pegdown"            % "pegdown"                   % "1.6.0",
    "org.jsoup"              %  "jsoup"                    % "1.10.3",
    "org.scalatestplus.play" %% "scalatestplus-play"       % "4.0.3",
    "org.scalatestplus"      %% "mockito-3-2"              % "3.1.2.0",
    "org.scalacheck"         %% "scalacheck"               % "1.14.3",
    "org.scalatestplus"      %% "scalatestplus-scalacheck" % "3.1.0.0-RC2",
    "com.github.tomakehurst" % "wiremock-standalone"       % "2.27.1",
    "com.vladsch.flexmark"   % "flexmark-all"              % "0.35.10"
  ).map(_ % "test")
}
