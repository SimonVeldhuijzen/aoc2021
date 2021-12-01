package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day01 : Solver {
    override fun partA(): String {
        val input = InputLoader().loadLongs(1)
        return countIncreases(input)
    }

    override fun partB(): String {
        val input = InputLoader().loadLongs(1)
        val windowed = createSlidingSums(input, 3)
        return countIncreases(windowed)
    }

    private fun countIncreases(input: List<Long>) =
        input.indices.drop(1).count { input[it] > input[it - 1] }.toString()

    private fun createSlidingSums(input: List<Long>, size: Int) =
        input.indices.drop(size - 1).map { input.drop(it - size + 1).take(size).sum() }
}
