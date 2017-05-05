#Akka.Js Benchmarks

all the basic infrastructure come from original scalajs-benchmarks project.

Some results on my machine (in us):

|SKYNET / Node |	1.2.5.0 |	1.2.5.1 |
|:------------:|----------|---------|
|2.11 Fast     |22717000	|8392666  |
|2.11 Full     |19642666	|6973333  |
|2.12 Fast     |19631000	|8536000  |
|2.12 Full     |16915333	|7208333  |

<a href="https://imgbb.com/"><img src="https://image.ibb.co/dBhrrQ/akkajs_perf_imp.png" alt="akkajs_perf_imp" border="0"></a><br /><a target='_blank' href='https://imgbb.com/'>online upload photos</a><br />

For fun I checked against GraalVM:

|SKYNET 1.2.5.1	| Node	| Graal |
|:-------------:|-------|-------|
|2.11 Fast      |8392666|6636000|
|2.11 Full      |6973333|6554666|
|2.12 Fast	      |8536000|6147333|
|2.12 Full	      |7208333|5343666|

<a href="https://imgbb.com/"><img src="https://image.ibb.co/b4TmQk/akkajs_perf_graal.png" alt="akkajs_perf_graal" border="0"></a><br /><a target='_blank' href='https://imgbb.com/'>online upload photos</a><br />

# Scala.js Benchmarks

[![Build Status](https://travis-ci.org/sjrd/scalajs-benchmarks.svg?branch=master)](https://travis-ci.org/sjrd/scalajs-benchmarks)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.8.svg)](https://www.scala-js.org/)

Benchmarks for [Scala.js](https://www.scala-js.org/)

All derivative work is the copyright of their respective authors and
distributed under their original license. All original work unless otherwise
stated is distributed under the [same license as
Scala.js](https://github.com/sjrd/scala-js-benchmarks/LICENSE).

## Get started

Open `sbt` and run the benchmarks with the `run` command of every project.
For example,

    > richardsJS/run

To test the fullOpt version, use, as usual:

    > set scalaJSStage in Global := FullOptStage

The benchmarks cross-compiled and can be run with the JVM too:

    > richardsJVM/run
