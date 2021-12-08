package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day08 : Solver {
    override fun partA(): String {
        val input = InputLoader().load(8)
        val output = input.map { it.substringAfter(" | ").split(" ") }
        return output.flatten().count { it.length in listOf(2, 3, 4, 7) }.toString()
    }

    override fun partB(): String {
        val input = InputLoader().load(8)
        val numbers = input.map { it.substringBefore(" | ").split(" ") }
        val output = input.map { it.substringAfter(" | ").split(" ") }
        return numbers.indices.sumBy { solve(
            numbers[it].sortedBy { i -> i.length }.map { s -> s.toCharArray().toList() },
            output[it].map { s -> s.toCharArray().toList() })
        }.toString()
    }

    private fun solve(numbers: List<List<Char>>, output: List<List<Char>>): Int {
        val one = numbers.first { it.size == 2 }
        val seven = numbers.first { it.size == 3 }
        val four = numbers.first { it.size == 4 }
        val twoThreeFive = numbers.filter { it.size == 5 }
        val zeroSixNine = numbers.filter { it.size == 6 }

        val top = (seven - one).first()
        val middleAndBottom = ('a'..'g').filter { c -> twoThreeFive.all { c in it } } - top
        val bottom = middleAndBottom.first { c -> zeroSixNine.all { c in it } }
        val middle = (middleAndBottom - bottom).first()
        val topLeft = (four - one - middle).first()
        val five = twoThreeFive.first { topLeft in it }
        val bottomRight = (five - top - middleAndBottom - topLeft).first()
        val topRight = (one - bottomRight).first()
        val bottomLeft = (('a'..'g') - five - topRight).first()

        val result = determineNumbers(output, listOf(top, topLeft, topRight, middle, bottomLeft, bottomRight, bottom))

        return result[0] * 1000 + result[1] * 100 + result[2] * 10 + result[3]
    }

    private fun determineNumbers(output: List<List<Char>>, encoding: List<Char>): List<Int> {
        return output.map { o ->
            when (o.size) {
                2 -> 1
                3 -> 7
                4 -> 4
                7 -> 8
                5 -> {
                    when {
                        encoding[1] in o -> 5
                        encoding[4] in o -> 2
                        else -> 3
                    }
                }
                else -> {
                    when {
                        encoding[3] !in o -> 0
                        encoding[4] in o -> 6
                        else -> 9
                    }
                }
            }
        }
    }
}
