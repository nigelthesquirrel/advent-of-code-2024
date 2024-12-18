package com.andystubbs.adventofcode2024.day16.part1

import com.andystubbs.adventofcode2024.day16.part1.Direction.*
import com.andystubbs.adventofcode2024.util.*

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

lateinit var start: Location
lateinit var end: Location
lateinit var walls: List<Location>
lateinit var locationCost: MutableMap<Location, Long>
lateinit var grid: List<List<Char>>
lateinit var bestPath: Set<Location>

fun main() {

    val input = readInput("/day16/part1/input.txt")
    grid = convertToGrid(input)

    start = findFirst(grid, 'S')
    end = findFirst(grid, 'E')
    walls = find(grid, '#')
    locationCost = mutableMapOf()

    val reindeer = start

    printGrid(grid, reindeer, EAST, walls)

    handleMove(reindeer, EAST, 0)

    println(locationCost[end])

}

fun handleMove(reindeer: Location, direction: Direction, cost: Long) {

    if((locationCost[reindeer] ?: Long.MAX_VALUE) < cost) return

    locationCost[reindeer] = cost

    if(reindeer == end) return

    if(canMove(reindeer, direction)) {
        handleMove(move(reindeer, direction), direction, cost+1)
    }

    val rightDirection = rotateRight(direction)
    if(canMove(reindeer, rightDirection)) {
        handleMove(move(reindeer, rightDirection), rightDirection, cost+1001)
    }

    val leftDirection = rotateLeft(direction)
    if(canMove(reindeer, leftDirection)) {
        handleMove(move(reindeer, leftDirection), leftDirection, cost + 1001)
    }

}

fun printGrid(grid: List<List<Char>>, reindeer: Location, direction: Direction, walls: List<Location>) {
    println()
    for (row in grid.indices) {
        for (col in grid[0].indices) {

            val location = Location(row, col)
            if (reindeer == location) print(directionToChar(direction))
            else if (location == start) print('S')
            else if (location == end) print('E')
            else if (walls.contains(location)) print('#')
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
        WEST -> '^'
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

fun finished(location: Location, end: Location) = location == end