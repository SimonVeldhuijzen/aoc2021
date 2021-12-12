package aoc2021.solvers

import aoc2021.helpers.InputLoader

class Day12 : Solver {
    override fun partA(): String {
        val nodes = load()
        val startNode = nodes.first { it.id == "start" }
        return generate(startNode, nodes - startNode, false).toString()
    }

    override fun partB(): String {
        val nodes = load()
        val startNode = nodes.first { it.id == "start" }
        return generate(startNode, nodes - startNode, true).toString()
    }

    private fun generate(node: Node, nodes: List<Node>, mayVisitTwice: Boolean, visited: List<Node> = listOf()): Int {
        if (!node.isBig && node in visited && (!mayVisitTwice || visited.toSet().size < visited.size)) {
            return 0
        }

        if (node.id == "end") {
            return 1
        }

        if (node.connectedNodes.isEmpty()) {
            return 0
        }

        val newVisited = if (node.isBig) visited else visited + node
        return node.connectedNodes.intersect(nodes).sumBy { cn -> generate(cn, nodes, mayVisitTwice, newVisited) }
    }

    private fun load(): List<Node> {
        val input = InputLoader().load(12)
        val nodes = mutableListOf<Node>()
        for (line in input) {
            val from = line.substringBefore("-")
            val to = line.substringAfter("-")
            val fromNode: Node = nodes.firstOrNull { it.id == from }
                ?: Node(from, from.toUpperCase() == from).also { nodes += it }
            val toNode = nodes.firstOrNull { it.id == to }
                ?: Node(to, to.toUpperCase() == to).also { nodes += it }
            fromNode.connectedNodes += toNode
            toNode.connectedNodes += fromNode
        }
        return nodes
    }

    private class Node(val id: String, val isBig: Boolean) {
        val connectedNodes = mutableListOf<Node>()

        override fun toString(): String {
            return "$id - ${connectedNodes.joinToString(", ") { it.id }}"
        }
    }
}
