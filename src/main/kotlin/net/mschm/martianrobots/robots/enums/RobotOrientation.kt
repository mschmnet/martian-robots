package net.mschm.martianrobots.robots.enums

enum class RobotOrientation(val oreintationRepresentation: Char) {
    NORTH('N'),
    EAST('E'),
    SOUTH('S'),
    WEST('W'),
    ;

    fun rotateRight(): RobotOrientation {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun rotateLeft(): RobotOrientation {
        return when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }
    }

    companion object {
        fun loadFromChar(char: Char): RobotOrientation{
            return values().find { it.oreintationRepresentation.equals(char) }
                ?: throw RuntimeException("Robot orientation $char not implemented")
        }
    }
}