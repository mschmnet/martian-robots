package net.mschm.martianrobots.robots.data

import net.mschm.martianrobots.robots.enums.RobotOrientation

/**
 * Represents the position on the grid and the orientation of a Robot on the Grid
 */
data class RobotPosition(val gridPosition: GridPosition, val robotOrientation: RobotOrientation)
