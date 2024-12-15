package com.andystubbs.adventofcode2024.day13.part1

import com.andystubbs.adventofcode2024.util.*

data class Coordinates(var x: Long, var y: Long)
data class Movement(var x: Long, var y:Long)
fun main() {

    val input = readInput("/day13/part1/input.txt")

    val buttonAs = input.filter { it.contains("Button A") }.map { extractMovement(it) }
    val buttonBs = input.filter { it.contains("Button B") }.map { extractMovement(it) }
    val prizes = input.filter { it.contains("Prize") }.map { extractPriceCoordinates(it) }

    val machines = buttonAs.zip(buttonBs).zip(prizes)

    var totalCost = 0L

    for(machine in machines) {

        val buttonA = machine.first.first
        val buttonB = machine.first.second
        val prize = machine.second

        var cheapest = Long.MAX_VALUE

        val isPossible = isPossible(buttonA.x, buttonB.x, prize.x) && isPossible(buttonA.y, buttonB.y, prize.y)
        if(isPossible) {

            for (numA in 0..100L) {
                for (numB in 0..100L) {
                    val result =
                        Coordinates((buttonA.x * numA) + (buttonB.x * numB), (buttonA.y * numA) + (buttonB.y * numB))

                    if(result == prize) {
                        val cost = (numA * 3L) + numB
                        if(cost < cheapest) cheapest = cost
                    }
                }
            }
            if(cheapest != Long.MAX_VALUE) totalCost += cheapest
        }
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
    return Coordinates(matchResult!!.groupValues[1].toLong(), matchResult.groupValues[2].toLong())
}