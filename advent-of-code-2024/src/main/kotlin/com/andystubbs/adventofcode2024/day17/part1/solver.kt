package com.andystubbs.adventofcode2024.day17.part1


import com.andystubbs.adventofcode2024.util.*

var hasOutput = false

var registerA: Int = 0
var registerB: Int = 0
var registerC: Int = 0
var pc: Int = 0
lateinit var program: List<Int>
fun main() {

    val input = readInput("/day17/part1/input.txt")

    registerA = input.first { it.contains("Register A") }.split(": ")[1].toInt()
    registerB = input.first { it.contains("Register B") }.split(": ")[1].toInt()
    registerC = input.first { it.contains("Register C") }.split(": ")[1].toInt()

    program = input.first { it.contains("Program") }.split(": ")[1].split(",").map { it.toInt() }

    while(pc < program.size) {

        val opCode = program[pc]
        val operand = program[pc+1]
        applyOpCode(opCode, operand)
    }

    println()
    println("Register A $registerA")
    println("Register B $registerB")
    println("Register C $registerC")

}

fun applyOpCode(opCode: Int, operand: Int) {

    when (opCode) {
        0 -> adv(operand)
        1 -> bxl(operand)
        2 -> bst(operand)
        3 -> jnz(operand)
        4 -> bxc(operand)
        5 -> out(operand)
        6 -> bdv(operand)
        7 -> cdv(operand)
        else -> error("Unknown opcode")
    }

}

fun adv(comboOperand: Int) {
    registerA = registerA / (1 shl getComboOperand(comboOperand))
    pc += 2
}

fun bxl(operand: Int) {
    registerB = registerB xor operand
    pc += 2
}

fun bst(comboOperand: Int) {
    registerB = getComboOperand(comboOperand) % 8
    pc += 2
}

fun jnz(operand: Int) {
    if (registerA == 0) {
        pc += 2
        return
    }

    pc = operand
}

fun bxc(operand: Int) {
    registerB = registerB xor registerC
    pc += 2
}

fun out(comboOperand: Int) {
    if(hasOutput) print(",")
    print(getComboOperand(comboOperand) % 8)
    hasOutput = true
    pc += 2
}

fun bdv(comboOperand: Int) {
    registerB = registerA / (1 shl getComboOperand(comboOperand))
    pc += 2
}

fun cdv(comboOperand: Int) {
    registerC = registerA / (1 shl getComboOperand(comboOperand))
    pc += 2
}

fun getComboOperand(comboOperand: Int): Int {

    return when (comboOperand) {
        0, 1, 2, 3 -> comboOperand
        4 -> registerA
        5 -> registerB
        6 -> registerC
        else -> error("Invalid comboOperand")
    }

}