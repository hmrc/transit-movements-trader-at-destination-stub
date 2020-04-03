import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(
    "org.reactivemongo" %% "play2-reactivemongo"           % "0.18.6-play26",
    "uk.gov.hmrc"       %% "logback-json-logger"           % "4.6.0",
    "uk.gov.hmrc"       %% "play-health"                   % "3.14.0-play-26",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping" % "1.2.0-play-26",
    "uk.gov.hmrc"       %% "bootstrap-play-26"             % "1.4.0",
    "uk.gov.hmrc"       %% "play-whitelist-filter"         % "3.1.0-play-26",
    "uk.gov.hmrc"       %% "play-nunjucks"                 % "0.23.0-play-26",
    "uk.gov.hmrc"       %% "play-nunjucks-viewmodel"       % "0.8.0-play-26",
    "org.webjars.npm"   % "govuk-frontend"                 % "3.3.0"
  )

  val test = Seq(
    "org.scalatest"           %% "scalatest"                % "3.0.8"                 % "test",
    "com.typesafe.play"       %% "play-test"                % current                 % "test",
    "org.pegdown"             %  "pegdown"                  % "1.6.0"                 % "test",
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "3.1.2"                 % "test",
    "org.mockito"             %  "mockito-all"              % "1.10.19",
  )
}
