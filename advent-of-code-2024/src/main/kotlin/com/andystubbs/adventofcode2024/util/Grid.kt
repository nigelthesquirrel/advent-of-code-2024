package com.andystubbs.adventofcode2024.util


data class Location(var row: Int, var col: Int)

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