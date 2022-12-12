package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day02 : Solver {
    override fun partA(): String {
        val input = InputLoader().load(2).dropLast(1)
        return input.sumBy { calculateScore(it[0], it[2]) }.toString()
    }

    override fun partB(): String {
        val input = InputLoader().load(2).dropLast(1)
        return input.sumBy { calculateScore2(it[0], it[2]) }.toString()
    }

    fun calculateScore(other: Char, me: Char): Int {
        val baseScore = me - 'W'
        val otherAsInt: Int = other - 'A' + 1
        val difference = ((baseScore - otherAsInt) % 3 + 3) % 3
        // 1 = win
        // 2 = lose
        // 3 = tie
        val gameScore = when (difference) {
            1 -> 6
            2 -> 0
            0 -> 3
            else -> throw Exception()
        }

        return gameScore + baseScore
    }

    fun calculateScore2(other: Char, me: Char): Int {
        // x = lose
        // y = tie
        // z = win
        val combinations = mapOf(
            'A' to mapOf(
                'X' to 'Z',
                'Y' to 'X',
                'Z' to 'Y',
            ),
            'B' to mapOf(
                'X' to 'X',
                'Y' to 'Y',
                'Z' to 'Z',
            ),
            'C' to mapOf(
                'X' to 'Y',
                'Y' to 'Z',
                'Z' to 'X',
            )
        )

        return calculateScore(other, combinations[other]!![me]!!)
    }
}
