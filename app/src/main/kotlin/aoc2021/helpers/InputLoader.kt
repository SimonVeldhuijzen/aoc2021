package aoc2021.helpers

class InputLoader {
    fun load(day: Int, part: String? = null): List<String> {
        val dayPart = day.toString().padStart(2, '0')
        val partPart = part ?: ""
        val fileName = "$dayPart$partPart"
        return this::class.java.classLoader.getResource(fileName)?.readText()?.split("\n")
            ?: run {
                println("Resource not found: $fileName")
                listOf()
            }
    }

    fun loadLongs(day: Int, part: String? = null) = load(day, part).map(String::toLong)
}
