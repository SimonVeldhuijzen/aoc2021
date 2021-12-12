package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.awt.Point

class Day11 : Solver {
    override fun partA(): String {
        val grid = Grid()

        var count = 0
        for (i in 0 until 100) {
            grid.flash()
            count += grid.resetFlashed()
        }

        return count.toString()
    }

    override fun partB(): String {
        val grid = Grid()

        var step = 1
        while (true) {
            grid.flash()
            val amount = grid.resetFlashed()
            if (amount == grid.size) {
                return step.toString()
            }

            step++
        }
    }

    private class Grid {
        val values = InputLoader()
            .load(11)
            .map { line ->
                line.map { it - '0' }.toMutableList()
            }

        val maxX = values.size - 1
        val maxY = values[0].size - 1
        val size = values.size * values[0].size

        fun resetFlashed(): Int {
            var count = 0
            values.forEach { line ->
                line.indices.forEach { i ->
                    if (line[i] > 9) {
                        line[i] = 0
                        count++
                    }
                }
            }

            return count
        }

        fun flash() {
            (0..maxX).forEach { x -> (0..maxY).forEach { y -> flash(x, y) } }
        }

        private fun flash(x: Int, y: Int) {
            values[x][y] = values[x][y] + 1
            if (values[x][y] == 10) {
                neighbours(x, y).forEach { n ->
                    flash(n.x, n.y)
                }
            }
        }

        fun countFlashes(): Int {
            return values.sumBy { row -> row.count { it > 9 } }
        }

        fun neighbours(x: Int, y: Int): List<Point> {
            return listOfNotNull(
                if (x > 0) Point(x - 1, y) else null,
                if (x < maxX) Point(x + 1, y) else null,
                if (y > 0) Point(x, y - 1) else null,
                if (y < maxY) Point(x, y + 1) else null,
                if (x > 0 && y > 0) Point(x - 1, y - 1) else null,
                if (x < maxX && y > 0) Point(x + 1, y - 1) else null,
                if (x > 0 && y < maxY) Point(x - 1, y + 1) else null,
                if (x < maxX && y < maxY) Point(x + 1, y + 1) else null,
            )
        }
    }

    private class Grid2 {
        val values: List<MutableList<Int?>> = InputLoader()
            .load(11)
            .map { line ->
                line.map { it - '0' }.toMutableList()
            }

        val maxX = values.size - 1
        val maxY = values[0].size - 1

        fun increaseByOne() {
            values.forEach { line ->
                line.indices.forEach { i ->
                    line[i] = line[i]!! + 1
                }
            }
        }

        fun resetFlashed() {
            values.forEach { line ->
                line.indices.forEach { i ->
                    if (line[i] == null) {
                        line[i] = 0
                    }
                }
            }
        }

        fun flash(): Int {
            val result = values.indices.sumBy { x ->
                values[x].indices.sumBy { y ->
                    if (values[x][y] != null && values[x][y]!! > 9) {
                        neighbours(x, y).forEach { n ->
                            if (values[n.x][n.y] != null) {
                                values[n.x][n.y] = values[n.x][n.y]!! + 1
                            }
                        }
                        values[x][y] = null
                        1
                    } else {
                        0
                    }
                }
            }

            return if (result > 0) {
                flash() + result
            } else {
                result
            }
        }

        fun neighbours(x: Int, y: Int): List<Point> {
            return listOfNotNull(
                if (x > 0) Point(x - 1, y) else null,
                if (x < maxX) Point(x + 1, y) else null,
                if (y > 0) Point(x, y - 1) else null,
                if (y < maxY) Point(x, y + 1) else null,
                if (x > 0 && y > 0) Point(x - 1, y - 1) else null,
                if (x < maxX && y > 0) Point(x + 1, y - 1) else null,
                if (x > 0 && y < maxY) Point(x - 1, y + 1) else null,
                if (x < maxX && y < maxY) Point(x + 1, y + 1) else null,
            )
        }
    }
}
