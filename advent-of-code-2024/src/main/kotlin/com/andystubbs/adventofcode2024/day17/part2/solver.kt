package com.andystubbs.adventofcode2024.day17.part2


import com.andystubbs.adventofcode2024.util.*

var hasOutput = false
var output = ""
var registerA: Long = 0
var registerB: Long = 0
var registerC: Long = 0

var registerBOriginal: Long = 0
var registerCOriginal: Long = 0

var pc: Long = 0L

lateinit var program: List<Long>
lateinit var programAsString: String

fun main() {

    val input = readInput("/day17/part2/input.txt")
    program = input.first { it.contains("Program") }.split(": ")[1].split(",").map { it.toLong() }
    programAsString = program.joinToString(",")

    registerA = input.first { it.contains("Register A") }.split(": ")[1].toLong()
    registerBOriginal = input.first { it.contains("Register B") }.split(": ")[1].toLong()
    registerCOriginal = input.first { it.contains("Register C") }.split(": ")[1].toLong()

    doSearch("")
}


fun doSearch(search: String):Boolean {

    for(d in 0..7) {
        val newSearch = "$search$d"
        output = runProgram(newSearch)

        if(programAsString == output) {
            println("Found match for $newSearch base 8");
            println("Which is ${newSearch.toLong(8)} base 10")
            return true
        }

        if(programAsString.endsWith(output)) {
            if(doSearch(newSearch)) {
                return true
            }
        }
    }
    return false
}



fun runProgram(aAsOctal:String): String {

    val a = aAsOctal.toLong(8)

    output = ""
    hasOutput = false
    pc = 0

    registerA = a
    registerB = registerBOriginal
    registerC = registerCOriginal

    while (pc < program.size) {

        val opCode = program[pc.toInt()]
        val operand = program[(pc + 1).toInt()]
        applyOpCode(opCode, operand)
    }
    return output
}

fun applyOpCode(opCode: Long, operand: Long) {

    when (opCode) {
        0L -> adv(operand)
        1L -> bxl(operand)
        2L -> bst(operand)
        3L -> jnz(operand)
        4L -> bxc(operand)
        5L -> out(operand)
        6L -> bdv(operand)
        7L -> cdv(operand)
        else -> error("Unknown opcode")
    }

}

fun adv(comboOperand: Long) {
    registerA = registerA / (1 shl getComboOperand(comboOperand).toInt())
    pc += 2
}

fun bxl(operand: Long) {
    registerB = registerB xor operand
    pc += 2
}

fun bst(comboOperand: Long) {
    registerB = getComboOperand(comboOperand) % 8
    pc += 2
}

fun jnz(operand: Long) {
    if (registerA == 0L) {
        pc += 2
        return
    }

    pc = operand
}

fun bxc(operand: Long) {
    registerB = registerB xor registerC
    pc += 2
}

fun out(comboOperand: Long) {
    if(hasOutput) output = "$output,"
    output = "$output${(getComboOperand(comboOperand) % 8)}"
    hasOutput = true
    pc += 2
}

fun bdv(comboOperand: Long) {
    registerB = registerA / (1 shl getComboOperand(comboOperand).toInt())
    pc += 2
}

fun cdv(comboOperand: Long) {
    registerC = registerA / (1 shl getComboOperand(comboOperand).toInt())
    pc += 2
}

fun getComboOperand(comboOperand: Long): Long {

    return when (comboOperand) {
        0L, 1L, 2L, 3L -> comboOperand
        4L -> registerA
        5L -> registerB
        6L -> registerC
        else -> error("Invalid comboOperand")
    }

}