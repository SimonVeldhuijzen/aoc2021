package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.awt.Point
import kotlin.math.max

class Day13 : Solver {
    override fun partA(): String {
        val (points, folds) = load()
       fold(points, folds[0])
        return points.size.toString()
    }

    override fun partB(): String {
        val (points, folds) = load()
        folds.forEach { f -> fold(points, f) }
        val x = points.maxOf { it.x } + 1
        val y = points.maxOf { it.y } + 1
        val result = MutableList(y) { Array(x) { ' ' } }
        points.forEach { p -> result[p.y][p.x] = '#' }
        return "\n" + result.joinToString("\n") { l -> l.joinToString("") }
    }

    private fun fold(points: MutableSet<Point>, fold: Point) {
        val value = max(fold.x, fold.y)
        if (fold.x == 0) {
            foldHorizontal(points, value)
        } else {
            foldVertical(points, value)
        }
    }

    private fun foldHorizontal(points: MutableSet<Point>, amount: Int) {
        for (point in points.filter { p -> p.y > amount }) {
            val newPoint = Point(point.x, point.y - (point.y - amount) * 2)
            points -= point
            points += newPoint
        }
    }

    private fun foldVertical(points: MutableSet<Point>, amount: Int) {
        for (point in points.filter { p -> p.x > amount }) {
            val newPoint = Point(point.x - (point.x - amount) * 2, point.y)
            points -= point
            points += newPoint
        }
    }

    private fun load(): Pair<MutableSet<Point>, List<Point>> {
        val input = InputLoader().load(13)
        val dots = input
            .takeWhile { it.isNotEmpty() }
            .map { l -> Point(l.substringBefore(",").toInt(), l.substringAfter(",").toInt()) }
            .toMutableSet()
        val folds = input
            .drop(dots.size + 1)
            .map { l ->
                val isX = l[11] == 'x'
                val value = l.drop(13).toInt()
                Point(if (isX) value else 0, if (isX) 0 else value)
            }
        return dots to folds
    }
}
