package com.andystubbs.adventofcode2024.day5.part1

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day5/part1/input.txt")

    val rulesInput = input.filter { it.contains("|") }
    val printingsInput = input.filter { it.contains(",") }

    val rules = processRulesInput(rulesInput)
    var sum = 0

    for(input in printingsInput) {

        val nos = input.split(",").map { it.toInt() }.toMutableList()
        val result = verifyRow(nos,rules)
        if(result) {
            sum += nos.middleItem()
        }
    }

    println(sum)
}

fun verifyRow(nos:List<Int>, rules: Map<Int, Set<Int>> ): Boolean {

    val nosm = nos.toMutableList()

    while(nosm.isNotEmpty()) {
        var current = nosm.removeAt(0)
        for(subsequent in nosm) {
            if((rules[subsequent]?:emptyList()).contains(current)) return false
        }
    }

    return true
}

fun processRulesInput(input: List<String>): Map<Int, Set<Int>> {

    val result = mutableMapOf<Int, MutableSet<Int>>()

    input.forEach {

        val tokens = it.split("|")
        val item = tokens[0].toInt()
        val after = tokens[1].toInt()

        val values = result.computeIfAbsent(item) { mutableSetOf<Int>() }
        values.add(after)
    }

    return result
}

fun <T> List<T>.middleItem(): T {
    return this[this.size / 2]
}