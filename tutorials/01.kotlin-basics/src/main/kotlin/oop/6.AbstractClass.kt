package oop

import kotlin.math.pow

abstract class Shape {
    abstract fun area() : Double
    open val name
        get() = "Shape"
}

class Rectangle(val width: Double, val height: Double) : Shape() {
    val isSquare
        get() = width == height

    override fun area() = width * height

   /* override val name
        get() = if (isSquare) "Square" else "Rectangle" */
}

class Circle(val radius: Double) : Shape() {
    override fun area() = Math.PI * radius.pow(2)

    override val name
        get() = "Circle"
}

fun main() {
    val shapes = listOf(
        Circle(3.5) ,
        Circle(5.0),
        Rectangle (3.5, 5.0))

    println("> Processing shapes polymorphically")
    shapes.forEach {
        val shapeInfo = when (it) {
            is Circle -> "Its radius is ${it.radius}"
            is Rectangle -> "Its width is ${it.width} and its height is ${it.height}"
            else -> ""
        }
        println("${it.name} area is ${it.area()}. $shapeInfo")
    }
    val areaSum = shapes.sumOf { it.area() }
    println("Sum of area of all shapes ${"%.2f".format(areaSum)}")
}
