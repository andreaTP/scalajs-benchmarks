/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2003-2016, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \                               **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark

import akka.actor._

case object Done
/** Simple benchmarking framework.
 *
 *  The `run` method has to be defined by the user, who will perform the
 *  timed operation there.
 *
 *  This will run the benchmark a minimum of 5 times and for at least 2
 *  seconds.
 */
abstract class Benchmark {

  def main(args: Array[String]): Unit = {
    main()
  }

  def main(): Unit = {
    val status = report({status: String => println(s"$prefix: $status")})
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
    val system = ActorSystem()

    var runs = 0
    val startTime = System.nanoTime()
    var stopTime = startTime + timeMinimum.toLong * 1000000L
    var currentTime = startTime

    case object Start
    system.actorOf(Props(new Actor{
      def receive = {
        case Start =>
          run(context.system, self)
        case Done =>
          runs += 1
          currentTime = System.nanoTime()
          if (currentTime < stopTime || runs < runsMinimum) {
            run(context.system, self)
          } else {
            val elapsed = currentTime - startTime
            f((elapsed / 1000).toDouble / runs)
            system.terminate()
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
    runBenchmark(3000, 5)(_ => f())
  }

  def report(f: String => Unit): Unit = {
    setUp()
    warmUp(
      () => runBenchmark(20000, 10)({avg: Double => {
        tearDown()
        f(s"$avg us")
      }})
    )
  }
}
