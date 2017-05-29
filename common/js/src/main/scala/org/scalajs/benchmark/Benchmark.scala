/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2003-2013, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    (c) 2013, Jonas Fonseca    **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark

import scala.compat.Platform
import scala.scalajs.js
import js.annotation.JSExport

import org.scalajs.benchmark.dom._

import akka.actor._

case object Done
/** `Benchmark` base class based on the deprecated scala.testing.Benchmark.
 *
 *  The `run` method has to be defined by the user, who will perform the
 *  timed operation there.
 *
 *  This will run the benchmark a minimum of 5 times and for at least 2
 *  seconds.
 *
 *  @author Iulian Dragos, Burak Emir
 */
abstract class Benchmark extends js.JSApp {

  def main(): Unit = {
    report({status: String => {
      println(s"$prefix: $status")
    }})
  }

  @JSExport
  def mainHTML(): Unit = {
    import DOM.document

    document.title = prefix

    val body = document.body

    val title = document.createElement("h1")
    title.textContent = prefix
    body.appendChild(title)

    val runButton =
      document.createElement("button").asInstanceOf[HTMLButtonElement]
    runButton.textContent = "Run benchmarks"
    body.appendChild(runButton)

    val statusText = document.createElement("p")
    body.appendChild(statusText)

    runButton.onclick = { () =>
      runButton.enabled = false
      statusText.textContent = "Running ..."

      js.timers.setTimeout(10) {
        try {
          report(status => {
            statusText.textContent = status
            runButton.enabled = true
          })
        } catch {
          case th: Throwable => th.toString()
        }
      }
    }
  }

  /** This method should be implemented by the concrete benchmark.
   *  It will be called by the benchmarking code for a number of times.
   *
   *  @see setUp
   *  @see tearDown
   */
  def run(system: ActorSystem, ref: ActorRef): Unit

  /** Run the benchmark the specified number of milliseconds and return
   *  the average execution time in microseconds.
   */
  def runBenchmark(timeMinimum: Long, runsMinimum: Int)(f: Double => Unit): Unit = {
    val conf =
      com.typesafe.config.ConfigFactory
        .parseString("""
          akka {
            akka.log-dead-letters = 0
            akka.log-dead-letters-during-shutdown = off
          }""")
        .withFallback(akkajs.Config.default)
    val system = ActorSystem(prefix, conf)

    var runs = 0
    val startTime = Platform.currentTime
    var stopTime = startTime + timeMinimum
    var currentTime = startTime

    case object Start
    system.actorOf(Props(new Actor{
      def receive = {
        case Start =>
          run(context.system, self)
        case Done =>
          runs += 1
          currentTime = Platform.currentTime
          if (currentTime < stopTime || runs < runsMinimum) {
            run(context.system, self)
          } else {
            val elapsed = currentTime - startTime
            f(1000.0 * elapsed / runs)
          }
      }
    })) ! Start
  }

  /** Prepare any data needed by the benchmark, but whose execution time
   *  should not be measured. This method is run before each call to the
   *  benchmark payload, 'run'.
   */
  def setUp(): Unit = ()

  /** Perform cleanup operations after each 'run'. For micro benchmarks,
   *  think about using the result of 'run' in a way that prevents the JVM
   *  to dead-code eliminate the whole 'run' method. For instance, print or
   *  write the results to a file. The execution time of this method is not
   *  measured.
   */
  def tearDown(): Unit = ()

  /** A string that is written at the beginning of the output line
   *  that contains the timings. By default, this is the class name.
   */
  def prefix: String = getClass().getName()

  def warmUp(f: () => Unit): Unit = {
    runBenchmark(100, 5)(_ => f())
  }

  def report(f: String => Unit): Unit = {
    setUp()
    warmUp(
      () => runBenchmark(100, 3)({avg: Double => {
        tearDown()
        f(s"$avg us")
      }})
    )
  }
}
