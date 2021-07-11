package net.mschm.martianrobots.springboot

import net.mschm.martianrobots.robots.data.GridPosition
import net.mschm.martianrobots.robots.data.RobotPosition
import net.mschm.martianrobots.robots.enums.OnOffGrid
import net.mschm.martianrobots.robots.enums.RobotMovement
import net.mschm.martianrobots.robots.enums.RobotOrientation
import net.mschm.martianrobots.robots.logic.GridProcessor
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class GridController {
    @MessageMapping("/robot")
    @SendTo("/output/robot")
    @Throws(Exception::class)
    fun greeting(robotInput: RobotInput): RobotOutputList {
        val gridProcessor = GridProcessor(
            GridPosition(
                robotInput.maxX ?: throw RuntimeException("missing maxX parameter"),
                robotInput.maxY ?: throw RuntimeException("missing maxY parameter")
            )
        )
        val robotOutputs = RobotOutputList()
        for (robot in robotInput.robots) {
            val finalRobotPosition = gridProcessor.sendRobot(
                RobotPosition(
                    GridPosition(
                        robot.initialX ?: throw java.lang.RuntimeException("missing initialX"),
                        robot.initialY ?: throw RuntimeException("missing initialY")
                    ),
                    RobotOrientation.loadFromChar(
                        robot.initialOrientation ?: throw RuntimeException("missing robot initial orientation")
                    )
                ), robot.movements.map { it -> RobotMovement.loadFromChar(it) }
            )
            val robotOutput = RobotOutput()
            robotOutputs.robots.add(robotOutput)
            robotOutput.xPos = finalRobotPosition.finalPosition.gridPosition.posX
            robotOutput.yPos = finalRobotPosition.finalPosition.gridPosition.posY
            robotOutput.orientation = finalRobotPosition.finalPosition.robotOrientation.oreintationRepresentation
            robotOutput.isLost = finalRobotPosition.robotOnOffGrid == OnOffGrid.ROBOT_LOST
        }
        return robotOutputs
    }
}