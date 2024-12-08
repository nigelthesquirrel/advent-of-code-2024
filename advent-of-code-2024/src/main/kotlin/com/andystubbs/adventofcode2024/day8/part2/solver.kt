package com.andystubbs.adventofcode2024.day8.part2

import com.andystubbs.adventofcode2024.day8.part1.addDifference
import com.andystubbs.adventofcode2024.util.*

data class Difference(val row: Int, val col: Int)

fun main() {

    val input = readInput("/day8/part2/input.txt")

    val antennaGrid = convertToGrid(input)
    var antiNodeGrid = convertToGrid(input)
    val antennaMap = extractLocations(antennaGrid)
    printGrid(antennaGrid)
    println(antennaMap)

    val antiNodes = mutableListOf<Location>()
    for(entry in antennaMap.entries) {
        val locationPairs = calculateAllPairs(entry.value)
        locationPairs.forEach { locationPair -> antiNodes.addAll(calculateAntiNodes(locationPair.first, locationPair.second, antennaGrid))}
    }

    for(entry in antennaMap.entries) {
        if(entry.value.size > 1) antiNodes.addAll(entry.value)
    }

    for(location in antiNodes) {
        antiNodeGrid = updateGrid(location, antiNodeGrid, '#')
    }

    printGrid(antiNodeGrid)

    println("Result ${antiNodes.distinct().size}")
}

fun calculateAntiNodes(location1: Location, location2: Location, grid: List<List<Char>>): List<Location> {

    val result = mutableListOf<Location>()
    val difference = location1.calculateDifference(location2)

    var antiNode = location1.addDifference(difference)

    while(!locationOutOfBounds(antiNode, grid)) {
        result.add(antiNode)
        antiNode = antiNode.addDifference(difference)
    }

    return result
}

fun Location.calculateDifference(location: Location): Difference = Difference(this.row - location.row, this.col - location.col)
fun Location.addDifference(difference: Difference): Location = Location(this.row + difference.row, this.col + difference.col)

fun extractLocations(grid: List<List<Char>>): Map<Char,List<Location>> {

    val result = mutableMapOf<Char, MutableList<Location>>()

    for(row in grid.indices) {
        for(col in grid[0].indices) {
            val char = grid[row][col]
            if(char == '.') continue
            val locations = result.getOrPut(char) { mutableListOf() }
            locations.add(Location(row, col))
        }
    }

    return result
}

fun calculateAllPairs(locations: List<Location>): List<Pair<Location, Location>> {
    val pairs = mutableListOf<Pair<Location, Location>>()

    for (i in locations.indices) {
        for (j in i + 1 until locations.size) {
            pairs.add(locations[i] to locations[j])
            pairs.add(locations[j] to locations[i])
        }
    }

    return pairs
}