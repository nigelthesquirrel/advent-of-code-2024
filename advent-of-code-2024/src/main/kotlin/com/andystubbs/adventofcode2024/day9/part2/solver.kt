package com.andystubbs.adventofcode2024.day9.part2

import com.andystubbs.adventofcode2024.util.*
import java.math.BigDecimal

fun main() {

    val input = readInput("/day9/part1/input.txt")[0].toMutableList()

    var fileId = 30

    val output = mutableListOf<Int>()

    while(true) {

        if(input.isEmpty()) break
        val occupiedSize = "${input.removeAt(0)}".toInt()
        for(x in 1..occupiedSize) output.add(fileId)
        if(input.isEmpty()) break
        val freeSize = "${input.removeAt(0)}".toInt()
        for(x in 1..freeSize) output.add(0)
        fileId++
    }

    while(fileId >= 30) {

        val length = findLengthOf(fileId, output)
        val original = output.indexOfFirst { it == fileId }

        loop@for(index in 0 until original) {

            if(hasGapOf(index, length, output)) {
                insert(fileId, index, length, output)
                insert(0, original, length, output)
                break@loop
            }
        }

        fileId--
    }

    var checksum = BigDecimal(0)
    for(i in output.indices) {
        val entry = output[i]
        if(entry != 0) {
            val num = entry.toInt() - 30
            checksum = checksum.add(BigDecimal(i * num))
        }
    }

    println(checksum)
}


fun insert(fileId: Int, index:Int, size: Int, list: MutableList<Int>) {

    for(i in 0 until size) {
        list[index+i] = fileId
    }
}

fun hasGapOf(index: Int, size: Int, list: List<Int>): Boolean {

    for(i in 0 until size) {
        if(index + i >= list.size) return false
        if(list[index+i] != 0) return false
    }
    return true
}

fun findLengthOf(fileId: Int, list: List<Int>): Int {

    return list.count { it == fileId }
}