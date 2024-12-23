package app

import core.control.{ControlSystem, ReindeersNeedRestException, SleighNotStartedException}

import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    val controlSystem = new ControlSystem()
    controlSystem.startSystem()

    var keepRunning = true

    while (keepRunning) {
      println("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ")
      val command = StdIn.readLine()

      command match {
        case "ascend" | "a" =>
          try {
            controlSystem.ascend()
          } catch {
            case e: ReindeersNeedRestException => println(e.getMessage)
            case e: SleighNotStartedException => println(e.getMessage)
          }
        case "descend" | "d" =>
          try {
            controlSystem.descend()
          } catch {
            case e: SleighNotStartedException => println(e.getMessage)
          }
        case "park" | "p" =>
          try {
            controlSystem.park()
          } catch {
            case e: SleighNotStartedException => println(e.getMessage)
          }
        case "quit" | "q" =>
          keepRunning = false
        case _ =>
          println("Invalid command. Please try again.")
      }
    }

    controlSystem.stopSystem()
  }
}
