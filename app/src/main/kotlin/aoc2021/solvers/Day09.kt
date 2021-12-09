package aoc2021.solvers

import aoc2021.helpers.InputLoader
import java.awt.Point

class Day09 : Solver {
    override fun partA(): String {
        val input = load()

        return input.mapIndexed { x, row ->
            row.filterIndexed { y, entry ->
                neighbours(input, Point(x, y)).all { n -> n.value > entry }
            }.map { it + 1 }
        }.flatten().sum().toString()
    }

    override fun partB(): String {
        val input = load()

        val basins = mutableListOf<MutableList<Point>>()
        input.forEachIndexed { x, row ->
            row.forEachIndexed { y, entry ->
                if (entry < 9) {
                    val point = Point(x, y)
                    val neighbourBasins = neighbours(input, point).filter { it.value < 9 && it.value != entry }.mapNotNull { n ->
                        basins.firstOrNull { basin -> n.key in basin }
                    }.toSet().toList()

                    for (basin in neighbourBasins.drop(1)) {
                        neighbourBasins[0] += basin
                        basins -= basin
                    }

                    if (neighbourBasins.any()) {
                        neighbourBasins[0] += point
                    } else {
                        basins += mutableListOf(point)
                    }
                }
            }
        }

        return basins.sortedByDescending { it.size }.take(3).map { it.size }.fold(1) { current, next -> current * next }.toString()
    }

    private fun load() = InputLoader().load(9).map { line ->
        line.map { c -> c - '0' }
    }

    private fun neighbours(values: List<List<Int>>, point: Point): Map<Point, Int> {
        return listOfNotNull(
            if (point.x <= 0) null else Point(point.x-1, point.y) to values[point.x-1][point.y],
            if (point.y <= 0) null else Point(point.x, point.y-1) to values[point.x][point.y-1],
            if (point.x >= values.size - 1) null else Point(point.x+1, point.y) to values[point.x+1][point.y],
            if (point.y >= values[point.x].size - 1) null else Point(point.x, point.y+1) to values[point.x][point.y+1]
        ).toMap()
    }
}
