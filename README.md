# Akka.Js Benchmarks

all the basic infrastructure come from original scalajs-benchmarks project.

## Last results

Akka.Js 1.2.5.12-SNAPSHOT, Scala 2.12.2 (always in us), Scala.Js fullOpt.

Akka JVM is 2.5.12 with Scala 2.12.2 too.

|PingPong|Node 10.0.0|JVM|
|:-------:|:-----:|:-:|
||2175|1305|

|Chameneos|Node 10.0.0|JVM|
|:-------:|:-----:|:-:|
||367|136|

|Pipe|Node 10.0.0|JVM|
|:-------:|:-----:|:-:|
||15252|2876|

|Skynet|Node 10.0.0|JVM|
|:-------:|:-----:|:-:|
||1786750|73971|

#### Historical results

Akka.Js 1.2.5.6, Scala 2.12.2 (always in us).
Akka JVM is 2.5.6 with Scala 2.12.2 too.

|PingPong|Node 7.10.1|Node 8.6.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||2739|2507|1143|

|Chameneos|Node 7.10.1|Node 8.6.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||579|491|152|

|Pipe|Node 7.10.1|Node 8.6.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||24354|19074|2972|

|Skynet|Node 7.10.1|Node 8.6.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||3040100|4721600|70232|


Akka.Js 1.2.5.4, Scala 2.12.2 (always in us).
Akka JVM is 2.5.4 with Scala 2.12.2 too.

|PingPong|Node 7.10.1|Node 8.4.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||2654|2484|1160|

|Chameneos|Node 7.10.1|Node 8.4.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||541|488|156|

|Pipe|Node 7.10.1|Node 8.4.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||24814|18904|3015|

|Skynet|Node 7.10.1|Node 8.4.0|JVM|
|:-------:|:-----:|:-----:|:-:|
||3020600|5117900|69524|

Updated result with 1.2.5.2 (all benchs are with Scala 2.12 on Node 7.3.0, us):

|PingPong|1.2.5.0|1.2.5.1|1.2.5.2|JVM|
|:-------:|:-----:|:-----:|:-----:|:-:|
||2476333|2317666|3923|1192|

|Chameneos|1.2.5.0|1.2.5.1|1.2.5.2|JVM|
|:-------:|:-----:|:-----:|:-----:|:-:|
||145857|136133|2061|183|

|Pipe|1.2.5.0|1.2.5.1|1.2.5.2|JVM|
|:-------:|:-----:|:-----:|:-----:|:-:|
||1379666|1235000|29000|3120|

|Skynet|1.2.5.0|1.2.5.1|1.2.5.2|JVM|
|:-------:|:-----:|:-----:|:-----:|:-:|
||16915333|7208333|3066000|70231|


Some results on my machine (in us):

|SKYNET / Node |	1.2.5.0 |	1.2.5.1 |
|:------------:|----------|---------|
|2.11 Fast     |22717000	|8392666  |
|2.11 Full     |19642666	|6973333  |
|2.12 Fast     |19631000	|8536000  |
|2.12 Full     |16915333	|7208333  |

<img src="https://image.ibb.co/dBhrrQ/akkajs_perf_imp.png" alt="akkajs_perf_imp" border="0">

For fun I checked against GraalVM:

|SKYNET 1.2.5.1	| Node	| Graal |
|:-------------:|-------|-------|
|2.11 Fast      |8392666|6636000|
|2.11 Full      |6973333|6554666|
|2.12 Fast	      |8536000|6147333|
|2.12 Full	      |7208333|5343666|

<img src="https://image.ibb.co/b4TmQk/akkajs_perf_graal.png" alt="akkajs_perf_graal" border="0">

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
