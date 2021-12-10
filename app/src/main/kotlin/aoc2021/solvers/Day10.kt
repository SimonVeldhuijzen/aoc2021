package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.util.*

class Day10 : Solver {
    override fun partA(): String {
        return InputLoader().load(10).sumBy { line ->
            val stack = Stack<Char>()
            for (c in line) {
                when (c) {
                    in chars.keys -> stack.push(chars[c]!!)
                    stack.peek() -> stack.pop()
                    else -> return@sumBy syntaxScores[c]!!
                }
            }
            0
        }.toString()
    }

    override fun partB(): String {
        val input = InputLoader().load(10).mapNotNull { line ->
            val stack = Stack<Char>()
            for (c in line) {
                when (c) {
                    in chars.keys -> stack.push(chars[c]!!)
                    stack.peek() -> stack.pop()
                    else -> return@mapNotNull null
                }
            }

            var score = 0L
            while (stack.any()) {
                score = score * 5 + autocompleteScores[stack.pop()]!!
            }

            score
        }.sorted()

        return input[input.size / 2].toString()
    }

    private val chars = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    private val syntaxScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    private val autocompleteScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )
}
