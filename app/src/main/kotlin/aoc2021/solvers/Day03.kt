package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day03 : Solver {
    override fun partA(): String {
        val input = InputLoader().load(3)
        val halfCount = input.size / 2
        val gamma = (0 until input[0].length).map { i ->
            if (input.count { it[i] == '1' } > halfCount) '1' else '0'
        }.toCharArray().concatToString()
        val epsilon = gamma.map { if (it == '1') '0' else '1' }.toCharArray().concatToString()

        return (gamma.toInt(2) * epsilon.toInt(2)).toString()
    }

    override fun partB(): String {
        val input = InputLoader().load(3)
        val oxygens = input.toMutableList()
        val co2s = input.toMutableList()
        for (i in 0 until input[0].length) {
            filterRatings(oxygens, i, false)
            filterRatings(co2s, i, true)
        }

        return (oxygens[0].toInt(2) * co2s[0].toInt(2)).toString()
    }

    private fun filterRatings(list: MutableList<String>, index: Int, shouldEqual: Boolean) {
        if (list.size == 1) {
            return
        }

        val zeroes = list.count { it[index] == '0' }
        val ones = list.count { it[index] == '1' }
        val filter = when (zeroes.compareTo(ones)) {
            -1 -> '1'
            0 -> '1'
            1 -> '0'
            else -> throw IllegalStateException()
        }

        list.removeIf { (it[index] == filter) == shouldEqual }
    }
}
