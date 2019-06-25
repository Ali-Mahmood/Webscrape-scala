name := "Dashboard"

version := "0.1"

scalaVersion := "2.12.6"

val Http4sVersion = "0.18.21"
val LogbackVersion = "1.2.3"
val scalaLoggingVersion = "3.9.0"
val circeVersion = "0.10.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.itv",
    name := "example-project",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "com.datastax.cassandra" % "cassandra-driver-core" % "3.3.2"
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
  )

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-literal"
).map(_ % circeVersion)