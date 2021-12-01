package aoc2021

import aoc2021.helpers.SolverLoader

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Provide one argument containing the day")
        return
    }

    val day = args[0].toIntOrNull() ?: run { println("The day should be an integer"); return }
    val solver = SolverLoader().load(day) ?: run { println("No solver found for day $day"); return }

    fun trySolve(f: () -> String) = try {
        f()
    } catch (t: Throwable) {
        t.message
    }

    val partA = trySolve { solver.partA() }
    val partB = trySolve { solver.partB() }

    println("Part a: $partA")
    println("Part b: $partB")
}
