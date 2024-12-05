package com.andystubbs.adventofcode2024.day4.part1

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day4/part1/input.txt")

    val grid = convertToGrid(input)
    var count = 0;

    for (r in grid.indices) {
        for (c in grid[r].indices) {

            for(rStep in -1..1) {
                for(cStep in -1..1) {
                    if(isXmas(r,c,rStep,cStep, grid)) {
                        count += 1
                    }
                }
            }
        }
    }
    println(count)
}

fun isXmas(rIn: Int, cIn: Int, rStep: Int,  cStep: Int, grid: List<List<Char>>): Boolean {

    var r = rIn
    var c = cIn

    for(l in "XMAS") {

        if(r < 0 ||  r >= grid.size) return false
        if(c < 0 || c >= grid[r].size) return false

        val curr = grid[r][c]
        if(curr != l) return false
        r += rStep
        c += cStep
    }

    return true
}

fun convertToGrid(strings: List<String>): List<List<Char>> {
    return strings.map { it.toList() }
}