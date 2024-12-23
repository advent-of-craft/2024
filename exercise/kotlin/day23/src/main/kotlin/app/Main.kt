package app

import core.control.ControlSystem
import core.control.ReindeersNeedRestException
import core.control.SleighNotStartedException
import java.util.*

fun main() {
    val controlSystem = ControlSystem()
    controlSystem.startSystem()

    val scanner = Scanner(System.`in`)
    var keepRunning = true

    while (keepRunning) {
        println("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ")
        val command = scanner.nextLine()

        when (command) {
            "ascend", "a" -> try {
                controlSystem.ascend()
            } catch (e: ReindeersNeedRestException) {
                println(e.message)
            } catch (e: SleighNotStartedException) {
                println(e.message)
            }

            "descend", "d" -> try {
                controlSystem.descend()
            } catch (e: SleighNotStartedException) {
                println(e.message)
            }

            "park", "p" -> try {
                controlSystem.park()
            } catch (e: SleighNotStartedException) {
                println(e.message)
            }

            "quit", "q" -> keepRunning = false
            else -> println("Invalid command. Please try again.")
        }
    }

    scanner.close()
    controlSystem.stopSystem()
}