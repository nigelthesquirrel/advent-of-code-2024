package com.andystubbs.adventofcode2024.util

class FileReader

fun readInput(name: String) =
    FileReader::class.java.getResource(name)?.readText(Charsets.UTF_8)?.split("\r\n", "\n")?.filter { it.isNotBlank() }
        ?: emptyList()

fun readInputIncludeBlank(name: String) =
    FileReader::class.java.getResource(name)?.readText(Charsets.UTF_8)?.split("\r\n", "\n")
        ?: emptyList()