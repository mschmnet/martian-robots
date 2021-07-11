package net.mschm.martianrobots.robots.data

/**
 * Identifies a position of the grid by its coordinates (x increases towards East, y increases towards North)
 * The lower-left coordinate es (0, 0)
 */
data class GridPosition(val posX: Int, val posY: Int)
