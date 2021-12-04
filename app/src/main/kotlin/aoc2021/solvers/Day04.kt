package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.lang.IllegalStateException

class Day04 : Solver {
    override fun partA(): String {
        val input = InputLoader().load(4)
        val (numbers, boards) = prepareBoards(input)

        for (number in numbers) {
            for (board in boards) {
                board.callNumber(number)?.let { return it.toString() }
            }
        }

        throw IllegalStateException()
    }

    override fun partB(): String {
        val input = InputLoader().load(4)
        val (numbers, boards) = prepareBoards(input)
        val mutableBoards = boards.toMutableList()

        for (number in numbers) {
            if (mutableBoards.size > 1) {
                val toDelete = mutableBoards.filter { it.callNumber(number) != null }
                mutableBoards -= toDelete
            } else {
                mutableBoards[0].callNumber(number)?.let { return it.toString() }
            }
        }

        throw IllegalStateException()
    }

    private fun prepareBoards(input: List<String>): Pair<List<Int>, List<Board>> {
        val numbers = input[0].split(",").map(String::toInt)
        val boards = (2..input.size step 6).map { Board(input.drop(it).take(5)) }
        return numbers to boards
    }

    private class Board(rawInput: List<String>) {
        private val tempEntries = rawInput.map { line ->
            line.split(" ").filter(String::isNotBlank).map { n -> Entry(n.toInt()) }
        }

        private val groups = tempEntries.map { Group(it) } +
                tempEntries[0].indices.map { i -> Group(tempEntries.map { e -> e[i] }) }

        private val entries = tempEntries.flatten()

        private val leftOverEntries = entries.map { it.value }.toMutableList()

        fun callNumber(number: Int): Int? {
            if (number in leftOverEntries) {
                leftOverEntries -= number
                entries.first { it.value == number }.isCalled = true
                if (groups.any { it.isFinished() }) {
                    return leftOverEntries.sum() * number
                }
            }

            return null
        }
    }

    private class Entry(val value: Int) {
        var isCalled = false
    }

    private class Group(val entries: List<Entry>) {
        fun isFinished() = entries.all { it.isCalled }
    }
}
