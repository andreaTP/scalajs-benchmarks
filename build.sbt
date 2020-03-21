
val projectSettings: Seq[Setting[_]] = Seq(
  organization := "scalajs-benchmarks",
  version := "0.1-SNAPSHOT"
)

val defaultSettings: Seq[Setting[_]] = projectSettings ++ Seq(
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-encoding", "utf8"
  )
)

val defaultJVMSettings: Seq[Setting[_]] = Seq(
  fork in run := !scala.sys.env.get("TRAVIS").exists(_ == "true")
)

val defaultJSSettings: Seq[Setting[_]] = Seq(
  scalaJSUseMainModuleInitializer := true
)

lazy val parent = project.in(file(".")).
  settings(projectSettings: _*).
  settings(
    name := "Scala.js Benchmarks",
    publishArtifact in Compile := false
  )

lazy val common = crossProject.
  settings(defaultSettings: _*).
  settings(
    name := "Scala.js Benchmarks - Common",
    moduleName := "common"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.6.3"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.akka-js" %%% "akkajsactor" % "2.2.6.3"
    )
  )

lazy val commonJVM = common.jvm
lazy val commonJS = common.js

lazy val skynet = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Akka.js Benchmarks - Skynet",
    moduleName := "skynet"
  ).
  dependsOn(common)

lazy val skynetJVM = skynet.jvm
lazy val skynetJS = skynet.js

lazy val chameneos = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Akka.js Benchmarks - Chameneos",
    moduleName := "chameneos"
  ).
  dependsOn(common)

lazy val chameneosJVM = chameneos.jvm
lazy val chameneosJS = chameneos.js

lazy val pingpong = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Akka.js Benchmarks - PingPong",
    moduleName := "pingpong"
  ).
  jsSettings(
    //scalaJSOptimizerOptions in (Compile, fullOptJS) ~= {
    //_.withUseClosureCompiler(false)
    //_.withDisableOptimizer(true)
    //}
  ).
  dependsOn(common)

lazy val pingpongJVM = pingpong.jvm
lazy val pingpongJS = pingpong.js

lazy val pipe = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Akka.js Benchmarks - Pipe",
    moduleName := "pipe"
  ).
  dependsOn(common)

lazy val pipeJVM = pipe.jvm
lazy val pipeJS = pipe.js
