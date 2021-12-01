package aoc2021.helpers

import aoc2021.solvers.Solver

class SolverLoader {
    fun load(day: Int): Solver? {
        return try {
            instantiate(className(day))
        } catch (_: ClassNotFoundException) {
            null
        }
    }

    private fun instantiate(className: String) =
        Class.forName(className).constructors.firstOrNull()?.newInstance() as Solver?
    private fun className(day: Int) = "aoc2021.solvers.Day${prefixDay(day)}"
    private fun prefixDay(day: Int) = day.toString().padStart(2, '0')
}
