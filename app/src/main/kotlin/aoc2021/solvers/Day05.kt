package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.awt.Point
import kotlin.math.max

class Day05 : Solver {
    override fun partA() = count(false)
    override fun partB() = count(true)

    private fun count(includeDiagonal: Boolean): String {
        val lines = InputLoader().load(5).map { Line(it) }
        val points = preparePoints(lines)

        lines.filter { includeDiagonal || it.isHorizontal }.forEach { line ->
            line.coveredPoints().forEach { point ->
                points[point] = points[point]!! + 1
            }
        }

        return points.count { it.value >= 2 }.toString()
    }

    private fun preparePoints(lines: List<Line>): MutableMap<Point, Int> {
        val maxX = lines.maxOf { max(it.a.x, it.b.x) }
        val maxY = lines.maxOf { max(it.a.y, it.b.y) }
        return (0..maxX).map { x ->
            (0..maxY).map { y -> Point(x, y) }
        }.flatten().associateWith { 0 }.toMutableMap()
    }

    private class Line(rawInput: String) {
        val a: Point
        val b: Point
        val isHorizontal: Boolean

        init {
            a = makePoint(rawInput.substringBefore(" -> "))
            b = makePoint(rawInput.substringAfter(" -> "))
            isHorizontal = a.x == b.x || a.y == b.y
        }

        fun coveredPoints() = sequence {
            val deltaX = b.x - a.x
            val deltaY = b.y - a.y
            val gcd = gcd(deltaX, deltaY)
            val xStep = deltaX / gcd
            val yStep = deltaY / gcd
            for (i in 0 .. gcd) {
                yield(Point(a.x + i * xStep, a.y + i * yStep))
            }
        }

        private fun makePoint(input: String) =
            Point(input.substringBefore(",").toInt(), input.substringAfter(",").toInt())

        private fun gcd(a: Int, b: Int): Int {
            if (a < b) return gcd(b, a)
            if (b < 0) return gcd(b * -1, a)
            if (b == 0) return a
            return gcd(b, a % b)
        }
    }
}
