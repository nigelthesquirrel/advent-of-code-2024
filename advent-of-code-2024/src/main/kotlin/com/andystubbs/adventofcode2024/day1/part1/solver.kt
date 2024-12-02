package com.andystubbs.adventofcode2024.day1.part1

import com.andystubbs.adventofcode2024.util.readInput
import kotlin.math.abs

fun main() {

    val input = readInput("/day1/part1/input.txt")

    val pairsList = input.map {
        val items = it.split("\\s+".toRegex())
       Pair<Int, Int>(items[0].toInt(), items[1].toInt())
    }

    val (list1, list2) = pairsList.unzip()

    val differences = list1.sorted().zip(list2.sorted()) { a, b -> abs(a - b) }
    val sum = differences.sum()
    println(sum)
}