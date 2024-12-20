package com.andystubbs.adventofcode2024.day13.part2

import com.andystubbs.adventofcode2024.util.*
import kotlin.math.abs

data class Coordinates(var x: Long, var y: Long)
data class Movement(var x: Long, var y:Long)
fun main() {

    val input = readInput("/day13/part1/input.txt")

    val buttonAs = input.filter { it.contains("Button A") }.map { extractMovement(it) }
    val buttonBs = input.filter { it.contains("Button B") }.map { extractMovement(it) }
    val prizes = input.filter { it.contains("Prize") }.map { extractPriceCoordinates(it) }

    val machines = buttonAs.zip(buttonBs).zip(prizes)

    var totalCost = 0L

    for (machine in machines) {

        val buttonA = machine.first.first
        val buttonB = machine.first.second
        val prize = machine.second

        val denominatorA = (buttonB.x * buttonA.y) - (buttonB.y * buttonA.x)
        val denominatorB = (buttonA.x * buttonB.y) - (buttonA.y * buttonB.x)

        if (denominatorA == 0L || denominatorB == 0L) continue

        val numeratorA = (prize.y * buttonB.x) - (buttonB.y * prize.x)
        val numeratorB = (prize.x * buttonA.y) - (prize.y * buttonA.x)

        if (numeratorA % denominatorA != 0L) {
            continue
        }

        if (numeratorB % denominatorB != 0L) {
            continue
        }

        // Calculate integer solutions
        val numA = numeratorA / denominatorA
        val numB = numeratorB / denominatorB

        totalCost += (abs(numA) * 3L) + abs(numB)
    }

    println("Total $totalCost")

}

fun isPossible(a1:Long, a2:Long, total:Long): Boolean {

    fun gcd(x: Long, y: Long): Long {
        return if (y == 0L) x else gcd(y, x % y)
    }

    val gcd = gcd(a1, a2)
    return total % gcd == 0L
}


fun extractMovement(string: String): Movement {

    val regex = Regex("X\\+(\\d+), Y\\+(\\d+)")
    val matchResult = regex.find(string)
    return Movement(matchResult!!.groupValues[1].toLong(), matchResult.groupValues[2].toLong())
}

fun extractPriceCoordinates(string: String): Coordinates {

    val regex = Regex("X\\=(\\d+), Y\\=(\\d+)")
    val matchResult = regex.find(string)
    return Coordinates(matchResult!!.groupValues[1].toLong() + 10000000000000, matchResult.groupValues[2].toLong() + 10000000000000)
}