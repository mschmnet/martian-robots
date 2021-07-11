package net.mschm.martianrobots

import net.mschm.martianrobots.robots.data.*
import net.mschm.martianrobots.robots.logic.RobotProcessor
import net.mschm.martianrobots.robots.data.FinalRobotState
import net.mschm.martianrobots.robots.enums.OnOffGrid
import net.mschm.martianrobots.robots.enums.RobotMovement
import net.mschm.martianrobots.robots.enums.RobotOrientation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RobotProcessorTest {

    @Test
    fun fistTestCaseRobotOnGrid() {
        val gridSpecs = GridSpecs(GridPosition(5, 3), hashSetOf())
        val initialPosition = RobotPosition(GridPosition(1,1), RobotOrientation.EAST)
        val movements = listOf(
            RobotMovement.RIGHT,
            RobotMovement.FORWARD,
            RobotMovement.RIGHT,
            RobotMovement.FORWARD,
            RobotMovement.RIGHT,
            RobotMovement.FORWARD,
            RobotMovement.RIGHT,
            RobotMovement.FORWARD
        )

        val result = RobotProcessor.calculateFinalPosition(gridSpecs, initialPosition, movements)

        Assertions.assertThat(result)
            .isEqualTo(FinalRobotState(RobotPosition(GridPosition(1, 1), RobotOrientation.EAST), OnOffGrid.ROBOT_ON_GRID))
    }

    @Test
    fun secondTestCaseRobotLost() {
        val gridSpecs = GridSpecs(GridPosition(5, 3), hashSetOf())
        val initialPosition = RobotPosition(GridPosition(3, 2), RobotOrientation.NORTH)
        val movements = listOf(
            RobotMovement.FORWARD,
            RobotMovement.RIGHT,
            RobotMovement.RIGHT,
            RobotMovement.FORWARD,
            RobotMovement.LEFT,
            RobotMovement.LEFT,
            RobotMovement.FORWARD,
            RobotMovement.FORWARD,
            RobotMovement.RIGHT,
            RobotMovement.RIGHT,
            RobotMovement.FORWARD,
            RobotMovement.LEFT,
            RobotMovement.LEFT,
        )

        val result = RobotProcessor.calculateFinalPosition(gridSpecs, initialPosition, movements)

        Assertions.assertThat(result)
            .isEqualTo(FinalRobotState(RobotPosition(GridPosition(3, 3), RobotOrientation.NORTH), OnOffGrid.ROBOT_LOST))
    }

    @Test
    fun thirdTestScentPreventsMovingOff() {
        val gridSpecs = GridSpecs(GridPosition(5, 3), hashSetOf(GridPosition(3,3)))
        val initialPosition = RobotPosition(GridPosition(0, 3), RobotOrientation.WEST)
        val movements = listOf(
            RobotMovement.LEFT,
            RobotMovement.LEFT,
            RobotMovement.FORWARD,
            RobotMovement.FORWARD,
            RobotMovement.FORWARD,
            RobotMovement.LEFT,
            RobotMovement.FORWARD,
            RobotMovement.LEFT,
            RobotMovement.FORWARD,
            RobotMovement.LEFT,
        )

        val result = RobotProcessor.calculateFinalPosition(gridSpecs, initialPosition, movements)

        Assertions.assertThat(result)
            .isEqualTo(FinalRobotState(RobotPosition(GridPosition(2, 3), RobotOrientation.SOUTH), OnOffGrid.ROBOT_ON_GRID))
    }
}