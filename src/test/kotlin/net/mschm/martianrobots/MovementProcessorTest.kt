package net.mschm.martianrobots

import net.mschm.martianrobots.robots.data.GridPosition
import net.mschm.martianrobots.robots.enums.RobotMovement
import net.mschm.martianrobots.robots.enums.RobotOrientation
import net.mschm.martianrobots.robots.data.RobotPosition
import net.mschm.martianrobots.robots.logic.MovementProcessor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class MovementProcessorTest {

    @Test
    fun testRobotRightRotation() {
        var robotPosition =
            MovementProcessor.calculateTheoreticalNextPosition(RobotPosition(GridPosition(1, 1), RobotOrientation.NORTH), RobotMovement.RIGHT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.EAST)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(robotPosition, RobotMovement.RIGHT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.SOUTH)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(robotPosition, RobotMovement.RIGHT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.WEST)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(robotPosition, RobotMovement.RIGHT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.NORTH)))
    }

    @Test
    fun testRobotLeftRotation() {
        var robotPosition =
            MovementProcessor.calculateTheoreticalNextPosition(RobotPosition(GridPosition(1, 1), RobotOrientation.NORTH), RobotMovement.LEFT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.EAST)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(robotPosition, RobotMovement.LEFT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.SOUTH)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(robotPosition, RobotMovement.LEFT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.WEST)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(robotPosition, RobotMovement.LEFT)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 1), RobotOrientation.NORTH)))
    }

    @Test
    fun testForwardMovement() {
        var robotPosition =
            MovementProcessor.calculateTheoreticalNextPosition(RobotPosition(GridPosition(1, 1), RobotOrientation.NORTH), RobotMovement.FORWARD)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 2), RobotOrientation.NORTH)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(RobotPosition(GridPosition(1,1), RobotOrientation.EAST), RobotMovement.FORWARD)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(2, 1), RobotOrientation.EAST)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(RobotPosition(GridPosition(1,1), RobotOrientation.SOUTH), RobotMovement.FORWARD)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(1, 0), RobotOrientation.SOUTH)))

        robotPosition = MovementProcessor.calculateTheoreticalNextPosition(RobotPosition(GridPosition(1,1), RobotOrientation.WEST), RobotMovement.FORWARD)
        Assertions.assertThat(robotPosition.equals(RobotPosition(GridPosition(0, 1), RobotOrientation.WEST)))
    }
}