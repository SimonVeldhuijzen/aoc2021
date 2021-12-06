package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day06 : Solver {
    override fun partA() = simulate(80)
    override fun partB() = simulate(256)

    private fun simulate(days: Int): String {
        val input = InputLoader().load(6)[0].split(",").map(String::toInt).groupBy { it }
        val zeroBased = simulateZero(days)
        return input.map { zeroBased[days - it.key] * it.value.size }.sum().toString()
    }

    private fun simulateZero(days: Int): List<Long> {
        val values = MutableList(9) { 0L }
        values[0] = 1

        return listOf(1L) + (1 until days).map {
            val temp = values[0]
            for (j in 0..7) {
                values[j] = values[j + 1]
            }
            values[8] = temp
            values[6] = values[6] + temp
            values.sum()
        }
    }
}
