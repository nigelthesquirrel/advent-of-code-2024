package com.andystubbs.adventofcode2024.day7.part1

import com.andystubbs.adventofcode2024.util.readInput

fun main() {

    val input = readInput("/day7/part2/example.txt")
    val totals = input.map { it.split(":")[0].toLong() }
    val numbers = input.map { numberList -> numberList.split(": ")[1].split(" ").map { it.toLong() } }

    val totalNumberPairs = totals.zip(numbers)

    var result:Long = 0

    totalNumberPairs.forEach { item ->
        run {

            val operatorCombos = generateCombinations(listOf('+', '*'), item.second.size-1)
            val operands = List(operatorCombos.size) { item.second}
            val equation = operands.zip(operatorCombos)

            equation.forEach {

                val operatorsStack = it.second.toMutableList()
                val operandsStack = it.first.toMutableList()
                var total = operandsStack.removeAt(0)

                while(operatorsStack.isNotEmpty()) {

                    val operator = operatorsStack.removeAt(0)
                    val operand = operandsStack.removeAt(0)

                    if(operator == '+') total += operand
                    if(operator == '*') total *= operand
                }
                if(total == item.first) {
                    result += item.first
                    return@run
                }
            }
        }
    }

    println(result)
}

fun generateCombinations(chars: List<Char>, n: Int): List<List<Char>> {
    var result = listOf(emptyList<Char>())
    repeat(n) {
        result = result.flatMap { current ->
            chars.map { char -> current + char }
        }
    }
    return result
}