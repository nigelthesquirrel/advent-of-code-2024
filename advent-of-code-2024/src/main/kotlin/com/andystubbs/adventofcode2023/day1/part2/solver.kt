package com.andystubbs.adventofcode2023.day1.part2

import com.andystubbs.adventofcode2023.util.readInput
fun main() {

    val input = readInput("/day1/part2/input.txt")

    val pairsList = input.map {
        val items = it.split("\\s+".toRegex())
        Pair(items[0].toInt(), items[1].toInt())
    }

    val (list1, list2) = pairsList.unzip()

    val similarityScore = list1.sumOf { item ->
        val count = list2.count { it == item }
        item * count
    }

    println(similarityScore)
}