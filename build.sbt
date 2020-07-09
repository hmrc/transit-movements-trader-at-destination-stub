import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings
import uk.gov.hmrc.SbtArtifactory
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "transit-movements-trader-at-destination-stub"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin, SbtArtifactory)
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    majorVersion                     := 0,
    libraryDependencies              ++= AppDependencies.compile ++ AppDependencies.test
  )
  .settings(publishingSettings: _*)
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(itSettings): _*)
  .settings(integrationTestSettings(): _*)
  .settings(resolvers += Resolver.jcenterRepo)
  .settings(PlayKeys.playDefaultPort := 9481)
  .settings(scalaVersion := "2.12.11")
  .settings(
    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources"
  )

lazy val itSettings = Defaults.itSettings ++ Seq(
  unmanagedSourceDirectories := Seq(
    baseDirectory.value / "it"
  ),
  parallelExecution := false,
  fork := true,
  javaOptions ++= Seq(
    "-Dlogger.resource=it.logback.xml"
  )
)
