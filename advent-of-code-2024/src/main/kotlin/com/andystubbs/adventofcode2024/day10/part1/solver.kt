package com.andystubbs.adventofcode2024.day10.part1

import com.andystubbs.adventofcode2024.util.*

val UP = Movement(-1, 0)
val DOWN = Movement(1, 0)
val LEFT = Movement(0, -1)
val RIGHT = Movement(0, 1)

val MOVEMENTS = listOf(UP, DOWN, LEFT, RIGHT)

fun main() {

    val input = readInput("/day10/part1/input.txt")
    val grid = convertToGrid(input)

    var total = 0
    val starts = find(grid, '0')

    for (start in starts) {
        val peaks = mutableSetOf<Location>()
        calculateNumberOfPeaks(start, peaks, grid)
        total += peaks.size
    }
    println(total)
}

fun calculateNumberOfPeaks(location: Location, peaks: MutableSet<Location>, grid: List<List<Char>>) {

    for (movement in MOVEMENTS) {
        if (canMove(location, movement, grid)) {
            val newLocation = applyMove(location, movement)
            calculateNumberOfPeaks(newLocation, peaks, grid)
        }
    }
    if (grid[location.row][location.col] == '9') peaks.add(location)
}


fun canMove(location: Location, movement: Movement, grid: List<List<Char>>): Boolean {

    val proposedLocation = applyMove(location, movement)
    if (locationOutOfBounds(proposedLocation, grid)) return false

    val currentHeight = grid[location.row][location.col]
    val proposedHeight = grid[proposedLocation.row][proposedLocation.col]

    if (!proposedHeight.isDigit()) return false
    return "$proposedHeight".toInt() - "$currentHeight".toInt() == 1
}


