package net.mschm.martianrobots.robots.enums

enum class RobotMovement(val movementRepresentation: Char) {
    RIGHT('R'),
    LEFT('L'),
    FORWARD('F'),
    ;

    companion object {
        fun loadFromChar(char: Char): RobotMovement {
            return values().find { it.movementRepresentation.equals(char) }
                ?: throw RuntimeException("Movement $char not implemented")
        }
    }
}