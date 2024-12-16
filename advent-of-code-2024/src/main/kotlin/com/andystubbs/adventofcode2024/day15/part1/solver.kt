package com.andystubbs.adventofcode2024.day15.part1

import com.andystubbs.adventofcode2024.day12.part1.DOWN
import com.andystubbs.adventofcode2024.day12.part1.LEFT
import com.andystubbs.adventofcode2024.day12.part1.RIGHT
import com.andystubbs.adventofcode2024.day12.part1.UP
import com.andystubbs.adventofcode2024.util.*


fun main() {

    val input = readInput("/day15/part1/input.txt")

    val gridLines = input.filter { it.contains("#") }
    val grid = convertToGrid(gridLines)

    val movements = input.filter { string -> string.any { it in setOf('<','>','^','V') } }.joinToString("")

    val boxes = mutableSetOf<Location>()
    val walls = mutableSetOf<Location>()
    var robot = Location(-1,-1)

    for(row in grid.indices) {
        for(col in 0 until grid[0].size) {
            if(grid[row][col] == 'O') {
                boxes.add(Location(row, col))
            }
            if(grid[row][col] == '#') {
                walls.add(Location(row, col))
            }
            if(grid[row][col] == '@') {
                robot = Location(row, col)
            }
        }
    }

    for(moveChar in movements) {
        val movement = decodeMove(moveChar)

        if(canMove(robot, movement, walls, boxes)) {
            robot = applyMove(robot, movement)
        }

        val newBoxLocations = mutableSetOf<Location>()
        var boxToMove = robot
        while(boxes.contains(boxToMove)) {
            boxes.remove(boxToMove)
            boxToMove = applyMove(boxToMove, movement)
            newBoxLocations.add(boxToMove)
        }
        boxes.addAll(newBoxLocations)
    }
    printGrid(robot, grid, boxes, walls)

    println(boxes.sumOf { it.row * 100 + it.col })


}

fun printGrid(robot: Location, grid: List<List<Char>>, boxes: Set<Location>, walls: Set<Location>) {

    for (row in grid.indices) {
        println()
        for (col in 0 until grid[0].size) {
            if(robot == Location(row, col)) print("@")
            else if (boxes.contains(Location(row, col))) print("O")
            else if (walls.contains(Location(row, col)))  print("#")
            else print(".")
        }
    }
    println()
}


fun canMove(location: Location, movement: Movement,walls: Set<Location>, boxes: Set<Location>): Boolean {

    val proposedLocation = Location(location.row + movement.row, location.col + movement.col)

    if(walls.contains(proposedLocation)) return false

    if(boxes.contains(proposedLocation)) return canMove(proposedLocation, movement, walls, boxes)

    return true
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

