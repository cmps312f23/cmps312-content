package `06`.collections

fun main() {

    // immutable list and mutable list
    val numsList = listOf(1, 2, 3)
    println(numsList)
    val sum = numsList.sum() // => 6
    val result = numsList.reduce { acc, n -> acc + n }
    println("numsList sum = $result")

    val mutableNumsList = mutableListOf(1, 2, 3)
    mutableNumsList.add(4)

    listOf("a", "b", "cc").sumOf { it.length } // => 4

    // immutable set and mutable set
    val colors = setOf("red", "blue", "yellow")
    println(colors)
    val mutableColors = mutableSetOf("red", "blue", "yellow")
    mutableColors.add("pink")
    val longerThan3 = colors.filter { it.length > 3 } // => [blue, yellow]

    // CASE 1: Creating an empty list
    val myEmptyList = emptyList<Int>()

    // Since the list is not mutable, the following is an error:
    // myEmptyList.add(1)

    // CASE 2: Creating a read-only list
    val readOnly = listOf(1, 2, 3)

    // Since the list is not mutable, the following is an error:
    // readOnly.add(1)

    // CASE 3: Creating a mutable list
    val mutableList = mutableListOf(1, 2, 3)

    // Another way of initializing a mutable list
    val mutableList2 = mutableListOf<Int>()

    // add, delete, and update operation are allowed
    mutableList.add(40)
    mutableList.removeAt(0)
    //Remove elements from the list having a value > 20
    mutableList.removeIf { it > 20 }
    mutableList[1] = 70

    // CASE 4: Initialize Function
    val triple = List(5, { it * 3 })
    println(triple)

    // CASE 5: Ten common 'List' scenarios
    val myList = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)

    // Scenario #1: Getting the first item of a list
    myList.first()

    // Scenario #2: Getting the last item of a list
    myList.last()

    // Scenario #3: Getting the item at index X
    myList[4]

    // Scenario #4: Taking X number of items from a list
    myList.take(3)

    // Scenario #5: Getting the index of the last item
    // In Java -> myList.size() - 1
    myList.lastIndex

    // Scenario #6: Checking if a list is empty
    // In Java -> myList.size() == 0
    myList.isEmpty()

    // Scenario #7: Checking if a list is not empty
    // In Java -> myList.size() > 0 or myList.size() != 0
    myList.isNotEmpty()

    // Scenario #8: Checking if a list contains value X
    myList.contains(3)
    3 in myList

    // Scenario #9: Adding an item to a mutable list
    myList.add(9)

    // Scenario #10: Removing an item from a mutable list
    myList.removeAt(0)
}