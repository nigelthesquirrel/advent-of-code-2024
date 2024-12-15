package com.andystubbs.adventofcode2024.day14.part2

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

    for(x in 1..50000) {
        data = data.map {
            val newLocation = move(it.first, it.second)
            Pair(newLocation, it.second)
        }

        val locations = data.map { it.first }
        if(hasVerticalRow(locations, 10)) {
            println("Suspect tree at $x")
            printStatus(data)
            return
        }
    }

}

fun hasVerticalRow(locations: List<Location>, count: Int): Boolean {

    val groupedByCol = locations.groupBy { it.col }

    for ((_, group) in groupedByCol) {
        val sortedRows = group.map { it.row }.sorted()

        var consecutiveCount = 1
        for (i in 1 until sortedRows.size) {
            if (sortedRows[i] == sortedRows[i - 1] + 1) {
                consecutiveCount++
                if (consecutiveCount == count) return true
            } else {
                consecutiveCount = 1
            }
        }
    }
    return false
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

fun move(location: Location, movement: Movement): Location {

    val newRow = (location.row + movement.row).mod(ROWS)
    val newCol = (location.col + movement.col).mod(COLS)

    return Location(newRow, newCol)
}