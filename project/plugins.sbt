resolvers += "HMRC-open-artefacts-maven" at "https://open.artefacts.tax.service.gov.uk/maven2"

resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)


resolvers += Resolver.typesafeRepo("releases")


addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.5.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-git-versioning" % "2.2.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-artifactory" % "1.15.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-distributables" % "2.1.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.8")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1")

addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.16")
