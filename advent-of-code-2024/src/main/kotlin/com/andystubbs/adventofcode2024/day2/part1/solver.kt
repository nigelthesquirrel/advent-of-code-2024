package com.andystubbs.adventofcode2024.day2.part1

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day2/part1/input.txt")

    var safeCount = 0
    for (row in input) {
        val ints = row.split(("\\s+".toRegex())).map { it.toInt() }

        if (isRowSafe(ints)) {
            safeCount++
        }
    }

    println(safeCount)
}

fun isRowSafe(list: List<Int>): Boolean {
    return list.zipWithNext().all { (a, b) -> isStepSafeIncreasing(a, b) } || list.zipWithNext()
        .all { (a, b) -> isStepSafeDecreasing(a, b) }
}

fun isStepSafeIncreasing(a: Int, b: Int): Boolean {
    return a - b >= 1 && a - b <= 3
}

fun isStepSafeDecreasing(a: Int, b: Int): Boolean {
    return a - b <= -1 && a - b >= -3
}