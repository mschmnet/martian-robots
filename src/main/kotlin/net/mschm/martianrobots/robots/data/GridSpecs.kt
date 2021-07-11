package net.mschm.martianrobots.robots.data

/**
 * Provides the specification of a grid
 * @param highestPosition: Coordinates of the position most to the North-East (The most to the South-West is (0, 0)
 * @param safePositions: Coordinates of the positions from which it's not possible anymore to fall off the grid
 */
data class GridSpecs(val highestPosition: GridPosition, val safePositions: Set<GridPosition>)
