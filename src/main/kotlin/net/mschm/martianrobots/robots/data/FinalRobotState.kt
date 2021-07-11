package net.mschm.martianrobots.robots.data

import net.mschm.martianrobots.robots.enums.OnOffGrid
import net.mschm.martianrobots.robots.data.RobotPosition

/**
 * Holds the final State of the Robot
 */
data class FinalRobotState(val finalPosition: RobotPosition, val robotOnOffGrid: OnOffGrid)
