package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.util.*

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
        val result = LinkedList<Long>()

        for (i in 0 until days) {
            result.add(values.sum())
            val temp = values[0]
            for (j in 0..7) {
                values[j] = values[j + 1]
            }
            values[8] = temp
            values[6] = values[6] + temp
        }
        return result.toList()
    }
}
