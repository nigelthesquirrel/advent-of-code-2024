package com.andystubbs.adventofcode2024.day2.part2

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day2/part2/input.txt")

    var safeCount = 0
    for (row in input) {
        val ints = row.split(("\\s+".toRegex())).map { it.toInt() }

        val candidates = ints.removeEachOnce()

        if(candidates.any { isRowSafe(it) }) {
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

fun List<Int>.removeEachOnce(): List<List<Int>> {
    return indices.map { index -> this.filterIndexed { i, _ -> i != index } }
}