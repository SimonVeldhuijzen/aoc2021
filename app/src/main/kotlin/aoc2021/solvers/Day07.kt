package aoc2021.solvers

import aoc2021.helpers.InputLoader
import kotlin.math.abs

class Day07 : Solver {
    override fun partA(): String {
        val input = parseInput()
        return range(input).minOf { i -> input.sumOf { abs(it - i) } }.toString()
    }

    override fun partB(): String {
        val input = parseInput()
        return range(input).minOf { i -> input.sumOf {
            val amount = abs(it - i)
            (amount * (amount + 1)) / 2
        } }.toString()
    }

    private fun parseInput() = InputLoader().load(7)[0].split(",").map(String::toInt)

    private fun range(input: List<Int>) =
        (input.minOrNull() ?: throw IllegalStateException())..(input.maxOrNull() ?: throw IllegalStateException())
}
