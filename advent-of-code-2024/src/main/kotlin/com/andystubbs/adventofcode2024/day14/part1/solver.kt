package com.andystubbs.adventofcode2024.day14.part1

import com.andystubbs.adventofcode2024.util.*

val ROWS = 103
val COLS = 101

fun main() {


    val input = readInput("/day14/part1/input.txt")

    var data = input.map { line ->

        val regex = Regex("-?\\d+")
        val nums = regex.findAll(line).map { it.value.toInt() }.toList()

        val location = Location(nums[1], nums[0])
        val movement = Movement(nums[3], nums[2])
        Pair(location, movement)
    }

    for(x in 1..100) {
        data = data.map {
            val newLocation = move(it.first, it.second)
            Pair(newLocation, it.second)
        }
    }

    val quadrants = data.map { inQuadrant(it.first) }.filter { it != 0 }

    print(quadrants.groupBy { it }.values.map { it.size }.reduce{acc, num -> acc * num})
}

fun printStatus(data: List<Pair<Location, Movement>>) {

    println()
    for (row in 0 until ROWS) {
        println()
        for (col in 0 until COLS) {

            if (data.any { it.first == Location(row, col) })  print(data.count { it.first == Location(row, col) })
            else print(".")
        }
    }
}

fun inQuadrant(location: Location): Int {
    val midRow = (ROWS - 1) / 2
    val midCol = (COLS - 1) / 2

    return when {
        location.row < midRow && location.col < midCol -> 1
        location.row < midRow && location.col > midCol -> 2
        location.row > midRow && location.col < midCol -> 3
        location.row > midRow && location.col > midCol -> 4
        else -> 0
    }
}

fun move(location: Location, movement: Movement): Location {

    val newRow = (location.row + movement.row).mod(ROWS)
    val newCol = (location.col + movement.col).mod(COLS)

    return Location(newRow, newCol)
}