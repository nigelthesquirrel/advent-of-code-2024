package com.andystubbs.adventofcode2024.day9.part1

import com.andystubbs.adventofcode2024.util.*
import java.math.BigDecimal

fun main() {

    val input = readInput("/day9/part1/input.txt")[0].toMutableList()

    println(input.joinToString(""))

    var index = 30

    val output = mutableListOf<Int>()

    while(true) {

        if(input.isEmpty()) break
        val occupiedSize = "${input.removeAt(0)}".toInt()
        for(x in 1..occupiedSize) output.add(index)
        if(input.isEmpty()) break
        val freeSize = "${input.removeAt(0)}".toInt()
        for(x in 1..freeSize) output.add(0)
        index++
    }

    var tailPointer = output.size-1
    var nextSpace = findNextSpace(output)

    while(nextSpace < tailPointer) {
        output[nextSpace] = output[tailPointer]
        output[tailPointer] = 0
        nextSpace = findNextSpace(output)
        tailPointer--
    }

    var checksum = BigDecimal(0)
    for(i in output.indices) {
        val entry = output[i]
        if(entry == 0) break
        val num = entry.toInt() - 30
        checksum = checksum.add(BigDecimal(i * num))
    }

    println(checksum)
}

fun findNextSpace(output: List<Int>) =  output.indexOfFirst { it == 0 }

