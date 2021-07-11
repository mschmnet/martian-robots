package net.mschm.martianrobots

import net.mschm.martianrobots.cli.MartianRobotsCli
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MartianRobot

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        printHelp()
        return
    }
    when (args[0]) {
        "spring-boot" -> runSpringBootApplication()
        "cli" -> MartianRobotsCli.runCli()
        else -> printHelp()
    }
}

private fun printHelp() {
    println(
        """
You have to provied one argumnet:
    * spring-boot: Run spring boot application
    * cli: Provide input on Standard Input
"""
    )
}

private fun runSpringBootApplication() {
    runApplication<MartianRobot>()
    return
}