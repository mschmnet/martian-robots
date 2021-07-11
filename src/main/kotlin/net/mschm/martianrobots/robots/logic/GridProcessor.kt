package net.mschm.martianrobots.robots.logic

import net.mschm.martianrobots.robots.data.*
import net.mschm.martianrobots.robots.enums.OnOffGrid
import net.mschm.martianrobots.robots.enums.RobotMovement

/**
 * A `GridProcessor` starts with an empty Grid and send one robot at a time. A robot may fall off the grid. When this
 * happens it means the position on which this happened is safe from now on. This class holds this state.
 */
class GridProcessor(val highestPosition: GridPosition) {

    val safePositions: MutableSet<GridPosition> = hashSetOf()

    fun sendRobot(initialPosition: RobotPosition, movements: List<RobotMovement>): FinalRobotState {

        val finalPosition = RobotProcessor.calculateFinalPosition(
            GridSpecs(highestPosition, safePositions.toSet()),
            initialPosition,
            movements
        )
        if (finalPosition.robotOnOffGrid.equals(OnOffGrid.ROBOT_LOST)) {
            safePositions.add(finalPosition.finalPosition.gridPosition)
        }
        return finalPosition
    }
}