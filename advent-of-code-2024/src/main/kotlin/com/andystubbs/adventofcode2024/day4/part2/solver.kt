package com.andystubbs.adventofcode2024.day4.part2

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day4/part2/input.txt")

    val grid = convertToGrid(input)
    var count = 0;

    for (r in grid.indices) {
        for (c in grid[r].indices) {

            if ((isWord(r,c,1,1,grid,"MAS") || isWord(r,c,1,1,grid,"SAM"))
                &&
                (isWord(r+2,c,-1,1,grid,"MAS") || isWord(r+2 ,c ,-1,1,grid,"SAM"))) {
                    count++
                }
        }
    }
    println(count)
}

fun isWord(rIn: Int, cIn: Int, rStep: Int,  cStep: Int, grid: List<List<Char>>, word:String): Boolean {

    var r = rIn
    var c = cIn

    for(l in word) {

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