
import java.io.File

fun main(args: Array<String>) {
  val automatas = mutableListOf<Automata>()
  File("input/input_task4.txt").readLines().forEach {
    val automata = parseRegularExpression(it)
    //File("output/$automata.json").writeText(Gson().toJson(automata))
    automatas.add(automata)
  }

  val results = task3(automatas, File("input/input.txt").readText())

  var stringResult = ""
  println("\nResults:\n")
  for (result in results) {
    stringResult += "<${result.first}, ${result.second}>\n"
  }

  println(stringResult)
  File("output/output_4.txt").writeText(stringResult)
}

fun replaceWithEscapeSymbols(str: String): String {
  var newStr = ""
  for (chr in str) {
    newStr += when(chr) {
      '\t' -> "\\t"
      ' ' -> "\\s"
      '\r' -> "\\t"
      '\n' -> "\\n"
      '\b' -> "\\b"
      else -> chr
    }
  }
  return newStr
}

fun parseRegularExpression(str: String): Automata {
  val lex = str.split(':')
  val tokenName = lex[0]
  val priority = lex[1]
  val regularExpression = lex[2]

  val parser = Parser()
  val automata = parser.generateAutomata(regularExpression)
  return IndeterminateAutomata(
      name = tokenName,
      priority = priority.toInt(),
      alphabet = automata.alphabet,
      initialStates = automata.initialStates,
      finalStates = automata.finalStates,
      transitions = automata.transitions)
}

val