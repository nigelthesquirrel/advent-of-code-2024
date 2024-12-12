package com.andystubbs.adventofcode2024.day12.part2

import com.andystubbs.adventofcode2024.util.*

val UP = Movement(-1, 0)
val DOWN = Movement(1, 0)
val LEFT = Movement(0, -1)
val RIGHT = Movement(0, 1)

val MOVEMENTS = listOf(UP, DOWN, LEFT, RIGHT)

val visitedLocations = mutableSetOf<Location>()

fun main() {

    val input = readInput("/day12/part2/input.txt")
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

        val upSides = getNumberSidesHorizontal(regionPair.second, UP)
        val downSides = getNumberSidesHorizontal(regionPair.second, DOWN)
        val leftSides = getNumberSidesVertical(regionPair.second, LEFT)
        val rightSides = getNumberSidesVertical(regionPair.second, RIGHT)

        val totalSides = upSides + downSides + leftSides + rightSides

        println("A region of ${regionPair.first} plants with price $area * $totalSides = ${area * totalSides} ")

        total += area * totalSides
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

fun getNumberSidesHorizontal(region: Set<Location>, movement: Movement): Int {

    var total = 0

    val facingLocations = getFacingLocations(region, movement)
    val rows = facingLocations.groupBy { it.row }

    for(colValues in rows.values) {
        val sides = colValues.map { it.col}
        total += countContinuousGroups(sides.sorted())
    }
    return total
}

fun getNumberSidesVertical(region: Set<Location>, movement: Movement): Int {

    var total = 0

    val facingLocations = getFacingLocations(region, movement)
    val rows = facingLocations.groupBy { it.col }

    for(colValues in rows.values) {
        val sides = colValues.map { it.row}
        total += countContinuousGroups(sides.sorted())
    }
    return total
}


fun getFacingLocations(region: Set<Location>, movement: Movement) : Set<Location> {

    return region.filter { !region.contains(applyMove(it, movement)) }.toSet()
}

fun countContinuousGroups(nums: List<Int>): Int {
    if (nums.isEmpty()) return 0

    var groups = 1
    for (i in 1 until nums.size) {
        if (nums[i] - nums[i - 1] != 1) {
            groups++
        }
    }
    return groups
}