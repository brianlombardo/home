package trnqilo

interface Command {
  fun execute(): String
}

fun Exception.getMessage(): String = message ?: "An unknown exception occurred."
