package com.andystubbs.adventofcode2024.day12.part1

import com.andystubbs.adventofcode2024.util.*

val UP = Movement(-1, 0)
val DOWN = Movement(1, 0)
val LEFT = Movement(0, -1)
val RIGHT = Movement(0, 1)

val MOVEMENTS = listOf(UP, DOWN, LEFT, RIGHT)

val visitedLocations = mutableSetOf<Location>()

fun main() {

    val input = readInput("/day12/part1/input.txt")
    val grid = convertToGrid(input)

    val allRegions = mutableListOf<Pair<Char,Set<Location>>>()

    for(row in grid.indices) {
        for(col in grid[0].indices) {

            val location = Location(row, col)
            val flower = grid[location.row][location.col]
            if(visitedLocations.contains(location)) continue
            allRegions.add(Pair(flower, extractRegion(flower, location, grid)))
        }
    }

    var total = 0
    for(regionPair in allRegions) {

        val area = regionPair.second.size
        val fences = regionPair.second.sumOf { calculateFenceForLocation(it, regionPair.second) }
        total += area * fences
    }
    println(total)
}


fun extractRegion(flower: Char, location: Location, grid: List<List<Char>>): Set<Location> {

    val region = mutableSetOf<Location>()
    walk(flower, location, region, grid)
    return region
}

fun walk(flower: Char, location: Location, region: MutableSet<Location>, grid: List<List<Char>>) {

    region.add(location)
    visitedLocations.add(location)

    for (movement in MOVEMENTS) {
        if (canMove(flower, location, movement, region, grid)) {
            val newLocation = applyMove(location, movement)
            walk(flower, newLocation, region, grid)
        }
    }
}

fun canMove(flower: Char,location: Location, movement: Movement, region: Set<Location>, grid: List<List<Char>>): Boolean {

    val proposedLocation = applyMove(location, movement)
    if (region.contains(proposedLocation)) return false
    if (locationOutOfBounds(proposedLocation, grid)) return false

    return grid[proposedLocation.row][proposedLocation.col] == flower
}

fun calculateFenceForLocation(location: Location, region: Set<Location>): Int {

    var fences = 4
    for(move in MOVEMENTS)
    if(region.contains(applyMove(location, move))) fences--
    return fences
}