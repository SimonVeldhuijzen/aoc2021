package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day03 : Solver {
    override fun partA(): String {
        val input = InputLoader().load(2)
        return input.fold(Position()) { current, next ->
            val (operation, amount) = next.split(" ")
            when (operation) {
                "forward" -> current.mutateHorizontal(amount.toInt())
                "down" -> current.mutateDepth(amount.toInt())
                "up" -> current.mutateDepth(amount.toInt() * -1)
                else -> current
            }
        }.multiplication
    }

    override fun partB(): String {
        val input = InputLoader().load(2)

        return input.fold(Position()) { current, next ->
            val (operation, amount) = next.split(" ")
            when (operation) {
                "forward" -> current.mutateDepth(amount.toInt() * current.aim).mutateHorizontal(amount.toInt())
                "down" -> current.mutateAim(amount.toInt())
                "up" -> current.mutateAim(amount.toInt() * -1)
                else -> current
            }
        }.multiplication
    }

    private data class Position(val depth: Int = 0, val horizontal: Int = 0, val aim: Int = 0) {
        val multiplication by lazy { (depth * horizontal).toString() }
        fun mutateDepth(amount: Int) = copy(depth = depth + amount)
        fun mutateHorizontal(amount: Int) = copy(horizontal = horizontal + amount)
        fun mutateAim(amount: Int) = copy(aim = aim + amount)
    }
}
