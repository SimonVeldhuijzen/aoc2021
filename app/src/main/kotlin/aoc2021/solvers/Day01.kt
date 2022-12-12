package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.util.*

class Day01 : Solver {
    override fun partA() = calculateTopElves(1)

    override fun partB() = calculateTopElves(3)

    private fun calculateTopElves(top: Int) =
        generateElves().map { it.sum() }.sortedDescending().take(top).sum().toString()

    private fun generateElves(): LinkedList<List<Long>> {
        val input = InputLoader().load(1)
        val elves = LinkedList<List<Long>>()
        var current = LinkedList<Long>()
        for (line in input) {
            if (line == "") {
                elves.add(current.toList())
                current = LinkedList<Long>()
            } else {
                current.add(line.toLong())
            }
        }

        return elves
    }
}
