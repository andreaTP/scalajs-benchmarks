package org.akkajs.benchmark.pingpong

import akka.actor._

import org.scalajs.benchmark.Done

object PingPong extends org.scalajs.benchmark.Benchmark {

  override def prefix = "PingPong"

  def run(system: ActorSystem, ref: ActorRef) = {
    system.actorOf(Props(new Actor {
      val pong = context.actorOf(Props(new Pong()))
      val ping = context.actorOf(Props(new Ping(1000, pong)))
      ping ! Pong

      def receive = {
        case "done" =>
          ref ! Done
      }
    }))
  }

  /*
  	inspired from:
  	https://github.com/shamsmahmood/savina
  */

  case object Ping
  case object Pong

  class Ping(var count: Long, pong: ActorRef) extends Actor {

    def receive = {
    	case Pong =>
    		count -= 1

    		if (count < 0) {
    			context.parent ! "done"
    			pong ! PoisonPill
    			self ! PoisonPill
    		} else
    			pong ! Ping
    }
  }

  class Pong extends Actor {
  	def receive = {
  		case Ping =>
  			sender ! Pong
  	}
  }
}
