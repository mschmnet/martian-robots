package net.mschm.martianrobots.cli

import net.mschm.martianrobots.robots.data.*
import net.mschm.martianrobots.robots.enums.OnOffGrid
import net.mschm.martianrobots.robots.enums.RobotMovement
import net.mschm.martianrobots.robots.enums.RobotOrientation
import net.mschm.martianrobots.robots.logic.GridProcessor

object MartianRobotsCli {

    fun runCli() {
        var line: String = readLine() ?: return
        val (xMax, yMax) = line.split(' ')
        val gridProcessor = GridProcessor(GridPosition(xMax.toInt(), yMax.toInt()))
        while (true) {
            line = readLine() ?: break
            val (x, y, o) = line.split(' ')
            line = readLine() ?: break
            val movements = line.toCharArray().map { RobotMovement.loadFromChar(it) }
            val robotFinalState =
                gridProcessor.sendRobot(RobotPosition(GridPosition(x.toInt(), y.toInt()), RobotOrientation.loadFromChar(o.first())), movements)
            println(
                "${robotFinalState.finalPosition.gridPosition.posX} ${robotFinalState.finalPosition.gridPosition.posY} ${robotFinalState.finalPosition.robotOrientation.oreintationRepresentation}${
                    if (robotFinalState.robotOnOffGrid.equals(
                            OnOffGrid.ROBOT_LOST
                        )
                    ) " LOST" else ""
                }"
            )
        }
    }
}