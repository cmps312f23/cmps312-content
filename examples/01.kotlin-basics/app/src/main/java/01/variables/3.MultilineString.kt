package variables

fun main() {
    val from = "Ali"
    val to = "Ahmed"

    val memo = """
      |This message was created by $from and
      |is being delivered
      |securely to $to
    """

    println(memo)

    println()
    println(memo.trimMargin())

    println()
    println(memo.trimMargin("|")) //replace | with ~, for example.
}