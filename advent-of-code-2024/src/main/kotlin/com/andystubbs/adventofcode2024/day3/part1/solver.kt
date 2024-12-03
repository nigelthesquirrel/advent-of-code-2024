package com.andystubbs.adventofcode2024.day3.part1

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day3/part1/input.txt").joinToString()

    val regex = Regex("""mul\(\d+,\d+\)""")
    val matches = regex.findAll(input).map { it.value }.toList()

    var sum: Long = 0

    val extractor = Regex("""mul\((\d+),(\d+)\)""")
    for(match in matches) {
        val extractResult = extractor.matchEntire(match)
        val x = extractResult!!.groupValues[1].toInt()
        val y = extractResult.groupValues[2].toInt()
        sum += x * y
    }

    println(sum)
}