package com.andystubbs.adventofcode2024.day16.part2

import com.andystubbs.adventofcode2024.day16.part2.Direction.*
import com.andystubbs.adventofcode2024.util.*

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

data class LocationDirection(val location: Location, val direction: Direction)

lateinit var start: Location
lateinit var end: Location
lateinit var walls: List<Location>
lateinit var locationCost: MutableMap<LocationDirection, Long>
lateinit var grid: List<List<Char>>
lateinit var bestPath: MutableSet<Location>
var minimumCost = 0L

fun main() {

    val input = readInput("/day16/part2/input.txt")
    grid = convertToGrid(input)

    start = findFirst(grid, 'S')
    end = findFirst(grid, 'E')
    walls = find(grid, '#')
    locationCost = mutableMapOf()
    bestPath = mutableSetOf()

    var reindeer = start

    minimumCost = 99460

    reindeer = start
    locationCost = mutableMapOf()
    bestPath = mutableSetOf()

    handleMove(reindeer, EAST, 0, mutableSetOf())
    println(bestPath.size)

}

fun handleMove(reindeer: Location, direction: Direction, cost: Long, path:Set<Location>) {

    if(cost > minimumCost) return
    if(cost > (locationCost[LocationDirection(reindeer, direction)] ?: Long.MAX_VALUE)) return

    val updatedPath = path + reindeer

    locationCost[LocationDirection(reindeer,direction)] = cost

    if(reindeer == end) {

        if(cost == minimumCost) {
            bestPath.addAll(updatedPath)
        }

        return
    }

    if(canMove(reindeer, direction)) {
        handleMove(move(reindeer, direction), direction, cost+1,updatedPath)
    }

    val rightDirection = rotateRight(direction)
    if(canMove(reindeer, rightDirection)) {
        handleMove(move(reindeer, rightDirection), rightDirection, cost+1001, updatedPath)
    }

    val leftDirection = rotateLeft(direction)
    if(canMove(reindeer, leftDirection)) {
        handleMove(move(reindeer, leftDirection), leftDirection, cost + 1001, updatedPath)
    }

}

fun printGrid(grid: List<List<Char>>, reindeer: Location, direction: Direction, path: Set<Location>) {
    println()
    for (row in grid.indices) {
        for (col in grid[0].indices) {

            val location = Location(row, col)
            if (reindeer == location) print(directionToChar(direction))
            else if (location == start) print('S')
            else if (location == end) print('E')
            else if (walls.contains(location)) print('#')
            else if (path.contains(location)) print('O')
            else print('.')
        }
        println()
    }
    println()
}

fun directionToChar(direction: Direction): Char {
    return when(direction) {
        NORTH -> '^'
        EAST -> '>'
        SOUTH -> 'v'
        WEST -> '<'
    }
}

fun rotateRight(direction: Direction): Direction {
    return when(direction) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

fun rotateLeft(direction: Direction): Direction {
    return when(direction) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }
}

fun move(location: Location, direction: Direction): Location {

    return when(direction) {
        NORTH -> applyMove(location, UP)
        EAST -> applyMove(location, RIGHT)
        SOUTH -> applyMove(location, DOWN)
        WEST -> applyMove(location, LEFT)
    }
}

fun canMove(location: Location, direction: Direction): Boolean {
    val proposedLocation = move(location, direction)
    return(!walls.contains(proposedLocation))
}

