package com.andystubbs.adventofcode2024.day3.part2

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day3/part2/input.txt").joinToString()

    val regex = Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""")
    val matches = regex.findAll(input).map { it.value }.toList()

    var sum: Long = 0

    val extractor = Regex("""mul\((\d+),(\d+)\)""")
    val doRegex = Regex("""do\(\)""")
    val dontRegex = Regex("""don't\(\)""")

    var enabled = true
    for(match in matches) {
        val extractResult = extractor.matchEntire(match)
        if(extractResult != null && enabled) {
            sum += extractResult.groupValues[1].toInt() * extractResult.groupValues[2].toInt()
        }

        if(doRegex.matches(match)) enabled = true
        if(dontRegex.matches(match)) enabled = false
    }

    println(sum)
}