package org.akkajs.benchmark.skynet

import akka.actor._

import org.scalajs.benchmark.Done

object Skynet extends org.scalajs.benchmark.Benchmark {

  override def prefix = "Skynet"

  def run(system: ActorSystem, ref: ActorRef) = {
    system.actorOf(Props(new Actor {
      context.actorOf(Skynet.props) ! Skynet.Start(6/*7*/, 0)
      def receive = {
        case x: Long =>
          ref ! Done
      }
    }))
  }

  val props = Props(new Skynet())

  case class Start(level: Int, num: Long)
}

// from https://github.com/atemerev/skynet
class Skynet() extends Actor {
  import Skynet._

  var todo = 10
  var count = 0L

  def receive = {
    case Start(level, num) =>
      if (level == 1) {
        context.parent ! num
        context.stop(self)
      } else {
        val start = num * 10
        (0 to 9) foreach (i => context.actorOf(props) ! Start(level - 1, start + i))
      }
    case l: Long =>
      todo -= 1
      count += l
      if (todo == 0) {
        context.parent ! count
        context.stop(self)
      }
  }
}
