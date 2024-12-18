package com.andystubbs.adventofcode2024.util


data class Location(var row: Int, var col: Int)
data class Movement(var row: Int, var col: Int)

val UP = Movement(-1, 0)
val DOWN = Movement(1, 0)
val LEFT = Movement(0, -1)
val RIGHT = Movement(0, 1)

val MOVEMENTS = listOf(UP, DOWN, LEFT, RIGHT)

fun applyMove(location: Location, movement: Movement) = Location(location.row + movement.row, location.col + movement.col)

fun updateGrid(location: Location, grid: List<List<Char>>, char: Char): List<List<Char>> {
    return grid.mapIndexed { rowIndex, row ->
        if (rowIndex == location.row) {
            row.mapIndexed { colIndex, cell ->
                if (colIndex == location.col) char else cell
            }
        } else {
            row
        }
    }
}

fun convertToGrid(strings: List<String>): List<List<Char>> {
    return strings.map { it.toMutableList()}.toMutableList()
}

fun locationOutOfBounds(location: Location, grid: List<List<Char>>) = (location.row >= grid.size || location.col >= grid[0].size || location.row < 0 || location.col < 0)

fun printGrid(grid: List<List<Char>>) {
    println()
    for(row in grid) {
        for(col in row) {
            print(col)
        }
        println()
    }
    println()
}

fun findFirst(grid: List<List<Char>>, char: Char):Location {
    for(row in 0 until grid.size) {
        for(col in 0 until grid[0].size) {
            if(grid[row][col] == char) {
                return Location(row, col)
            }
        }
    }
    error("Can't find $char")
}

fun find(grid: List<List<Char>>, char: Char):List<Location> {

    val locations = mutableListOf<Location>()
    for(row in 0 until grid.size) {
        for(col in 0 until grid[0].size) {
            if(grid[row][col] == char) {
                locations.add(Location(row, col))
            }
        }
    }
    return locations
}