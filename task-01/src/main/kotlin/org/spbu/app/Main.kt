package org.spbu.app

private const val STRANGER_NAME = "незнакомец"

fun main(args: Array<String>) {
    var name = STRANGER_NAME
    if (args.size != 0) {
        val new = args[0].trim()
        if (new != "") {
            name = new
        }
    }
    println("Привет, $name")
}
