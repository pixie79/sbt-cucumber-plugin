import sbt._
import Keys._

object Settings {
  val buildOrganization = "rocks.bdd"
  val buildScalaVersion = "2.10.3"
  val buildVersion      = "0.9.1"

  val buildSettings = Defaults.defaultSettings ++
                      Seq (organization  := buildOrganization,
                           scalaVersion  := buildScalaVersion,
                           version       := buildVersion,
                           scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8"),
                           publishTo     := Some(Resolver.file("file",  new File("deploy-repo"))))
}

object Dependencies {

  private val CucumberVersion = "1.2.2"

  def cucumberJvm(scalaVersion: String) =
    "info.cukes" %% "cucumber-scala" % CucumberVersion % "compile"

  val testInterface = "org.scala-tools.testing" % "test-interface" % "0.5" % "compile"
  
  val scalaReflect = "org.scala-lang" % "scala-reflect" % "2.10.3"

  val scalaLibrary = "org.scala-lang" % "scala-library" % "2.10.3"

}

object Build extends Build {
  import Dependencies._
  import Settings._

  lazy val parentProject = Project("sbt-cucumber-parent", file ("."),
    settings = buildSettings).aggregate(pluginProject, integrationProject)

  lazy val pluginProject = Project("sbt-cucumber-plugin", file ("plugin"),
    settings = buildSettings ++
               Seq(
                 scalaVersion := "2.10.3",
                 sbtPlugin := true,
                 libraryDependencies += scalaReflect,
                 libraryDependencies += scalaLibrary))

  lazy val integrationProject = Project ("sbt-cucumber-integration", file ("integration"),
    settings = buildSettings ++
               Seq(resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
               scalaVersion := "2.10.3",
               libraryDependencies <+= scalaVersion { sv => cucumberJvm(sv) },
               libraryDependencies += testInterface,
               libraryDependencies += scalaReflect,
               libraryDependencies += scalaLibrary))
}

