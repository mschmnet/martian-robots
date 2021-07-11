package net.mschm.martianrobots.robots.logic

import net.mschm.martianrobots.robots.data.*
import net.mschm.martianrobots.robots.enums.OnOffGrid
import net.mschm.martianrobots.robots.enums.RobotMovement

/**
 * Given a grid specification, processes the movements of **one** Robot and returns the final position. This class is
 * stateless. It receives the grid specification and all the movements of a robot, and applies each of them, one after
 * another.
 */
object RobotProcessor {

    fun calculateFinalPosition(
        gridSpecs: GridSpecs,
        initialPosition: RobotPosition,
        movements: List<RobotMovement>
    ): FinalRobotState {
        val grid = MovementProcessor(gridSpecs, initialPosition)
        for (movement in movements) {
            grid.applyMovement(movement)
            if (grid.isRobotLost){
                break
            }
        }
        return FinalRobotState(
            grid.currentPosition,
            if (grid.isRobotLost) OnOffGrid.ROBOT_LOST else OnOffGrid.ROBOT_ON_GRID
        )
    }
}