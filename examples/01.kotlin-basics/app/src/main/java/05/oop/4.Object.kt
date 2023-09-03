package oop

import java.util.*

//Singleton - single instance shared in the whole app
//Can be used without creating an instance of it
object Util {
    fun getNumberOfCores() = Runtime.getRuntime().availableProcessors()

    val randomInt: Int
        get() = Random().nextInt()
}

fun main() {
    println(Util.getNumberOfCores())
    println(Util.randomInt)
}