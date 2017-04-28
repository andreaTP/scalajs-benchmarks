package org.akkajs.benchmark.pipe

import akka.actor._

import scala.annotation.tailrec
import org.scalajs.benchmark.Done

object Pipe extends org.scalajs.benchmark.Benchmark {

  override def prefix = "Pipe"

  def run(system: ActorSystem, ref: ActorRef) = {
    system.actorOf(Props(new PipeManager(ref))) !  Start(1000)
  }

  case class Start(num: Long)

  class PipeManager(ref: ActorRef) extends Actor {

    	@tailrec
    	private def genChildrens(n: Long, next: ActorRef): ActorRef = {
    		val newOne = context.actorOf(Props(new PipeActor(next)))
    		if (n > 0)
          	genChildrens(n-1, newOne)
          else
          	newOne
    	}

      def receive = {
        case Start(targetSize) =>
          genChildrens(targetSize, self) ! Token
          context.become(operative(sender))
      }

      def operative(answareTo: ActorRef): Receive = {
     	  case Token =>
     	  	ref ! Done
     	  	context.children.foreach(_ ! PoisonPill)
      }
    }

    case object Token
    case object Pong

    var start = 0L
    var end = 0L

    class PipeActor(next: ActorRef) extends Actor {
    	def receive = {
    		case Token =>
    			next ! Token
    	}
    }
}
