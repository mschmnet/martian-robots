package net.mschm.martianrobots.robots.logic

import net.mschm.martianrobots.robots.data.GridSpecs
import net.mschm.martianrobots.robots.enums.RobotMovement
import net.mschm.martianrobots.robots.enums.RobotOrientation
import net.mschm.martianrobots.robots.data.RobotPosition

class MovementProcessor(var gridSpecs: GridSpecs, initialPosition: RobotPosition) {
    var isRobotLost: Boolean = false
    var currentPosition: RobotPosition = initialPosition

    fun applyMovement(movement: RobotMovement) {
        if (isRobotLost) {
            return;
        }
        val maybeNewPosition = calculateTheoreticalNextPosition(currentPosition, movement);

        when {
            maybeNewPosition.gridPosition.posY in 0..gridSpecs.highestPosition.posX && maybeNewPosition.gridPosition.posY in 0..gridSpecs.highestPosition.posY -> currentPosition =
                maybeNewPosition
            else -> {
               if (!gridSpecs.safePositions.contains(currentPosition.gridPosition)) {
                   isRobotLost = true
               }
            }
        }
    }

    companion object NexMovementCalculator {
        fun calculateTheoreticalNextPosition(
            currentPosition: RobotPosition,
            movement: RobotMovement
        ): RobotPosition {
            return when (movement) {
                RobotMovement.RIGHT -> currentPosition.copy(robotOrientation = currentPosition.robotOrientation.rotateRight())
                RobotMovement.LEFT -> currentPosition.copy(robotOrientation = currentPosition.robotOrientation.rotateLeft())
                RobotMovement.FORWARD -> when (currentPosition.robotOrientation) {
                    RobotOrientation.NORTH -> currentPosition.copy(gridPosition = currentPosition.gridPosition.copy(posY = currentPosition.gridPosition.posY + 1))
                    RobotOrientation.EAST -> currentPosition.copy(gridPosition = currentPosition.gridPosition.copy(posX = currentPosition.gridPosition.posX + 1))
                    RobotOrientation.SOUTH -> currentPosition.copy(gridPosition = currentPosition.gridPosition.copy(posY = currentPosition.gridPosition.posY - 1))
                    RobotOrientation.WEST -> currentPosition.copy(gridPosition = currentPosition.gridPosition.copy(posX = currentPosition.gridPosition.posX - 1))
                }
            }
        }
    }

}