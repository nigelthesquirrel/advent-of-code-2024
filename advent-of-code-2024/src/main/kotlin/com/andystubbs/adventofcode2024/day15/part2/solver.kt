package com.andystubbs.adventofcode2024.day15.part2

import com.andystubbs.adventofcode2024.day12.part1.DOWN
import com.andystubbs.adventofcode2024.day12.part1.LEFT
import com.andystubbs.adventofcode2024.day12.part1.RIGHT
import com.andystubbs.adventofcode2024.day12.part1.UP
import com.andystubbs.adventofcode2024.day14.part2.move
import com.andystubbs.adventofcode2024.util.*

fun main() {

    val input = readInput("/day15/part1/example.txt")

    val gridLines = input.filter { it.contains("#") }
    val grid = convertToGrid(gridLines)

    val movements = input.filter { string -> string.any { it in setOf('<','>','^','V') } }.joinToString("")

    val leftBoxes = mutableSetOf<Location>()
    val rightBoxes = mutableSetOf<Location>()
    val walls = mutableSetOf<Location>()
    var robot = Location(-1,-1)

    for(row in grid.indices) {
        for(col in 0 until grid[0].size) {
            if(grid[row][col] == 'O') {
                leftBoxes.add(Location(row, 2 * col))
                rightBoxes.add(Location(row, 2 * col+1))
            }
            if(grid[row][col] == '#') {
                walls.add(Location(row, 2 * col))
                walls.add(Location(row, 2 * col+1))
            }
            if(grid[row][col] == '@') {
                robot = Location(row, 2 * col)
            }
        }
    }

    printGrid(robot, grid, leftBoxes, rightBoxes, walls)

    print(getTouchingBoxes(robot, LEFT, emptySet(), walls, leftBoxes, rightBoxes))

    /*
    for(moveChar in movements) {
        val movement = decodeMove(moveChar)


    }
     */

}

fun getTouchingBoxes(location: Location, movement: Movement, touchingBoxes: Set<Location>, walls: Set<Location>, leftBoxes: Set<Location>, rightBoxes: Set<Location>): Set<Location> {

    val proposedLocation = applyMove(location, movement)
    var proposedLocation2 = Location(-1,-1)

    if(movement == UP || movement == DOWN) {
        if(leftBoxes.contains(proposedLocation)) {
            proposedLocation2 = applyMove(proposedLocation, RIGHT)
        }
        if(rightBoxes.contains(proposedLocation)) {
            proposedLocation2 = applyMove(proposedLocation, LEFT)
        }
    }

    if(walls.contains(proposedLocation) || walls.contains(proposedLocation2)) throw RuntimeException("Wall hit!")

    if(leftBoxes.contains(proposedLocation) || rightBoxes.contains(proposedLocation)) return touchingBoxes union getTouchingBoxes(proposedLocation, movement, touchingBoxes, walls, leftBoxes, rightBoxes) union getTouchingBoxes(proposedLocation2, movement, touchingBoxes, walls, leftBoxes, rightBoxes)
    return touchingBoxes
}





fun printGrid(robot: Location, grid: List<List<Char>>, leftBoxes: Set<Location>, rightBoxes: Set<Location>, walls: Set<Location>) {

    for (row in grid.indices) {
        println()
        for (col in 0 until 2 * grid[0].size) {
            if(robot == Location(row, col)) print("@")
            else if (leftBoxes.contains(Location(row, col))) print("[")
            else if (rightBoxes.contains(Location(row, col))) print("]")
            else if (walls.contains(Location(row, col)))  print("#")
            else print(".")
        }
    }
    println()
}





fun decodeMove(char: Char): Movement {

    return when (char) {
        '^' -> UP
        'v' -> DOWN
        '<' -> LEFT
        '>' -> RIGHT
        else -> error("Unknown direction")
    }
}