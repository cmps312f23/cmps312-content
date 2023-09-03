package controlFlow

fun greet(language: String = "EN") =
    // when expression
    when (language) {
        "AR" -> "مرحبا!"
        "EN" -> "Hello!"
        "FR" -> "Salut!"
        "IT" -> "Ciao!"
        else -> "Sorry, I can't greet you in $language yet"
    }

fun getSeason(month : Int) =
    when (month) {
        12, 1,2  -> "Winter"
        in 3..5 -> "Spring"
        in 6..8 -> "Summer"
        in 9..11 -> "Autumn"
        else -> "Invalid Month"
    }

fun main() {

    println(greet())

    val month = 8

    val season = getSeason(month)

    println("Season for month $month is $season")

    // 'if' vs 'when'
    println(" ")
    val number = 20

    // If
    if (number % 2 == 0) {
        println("$number is even")
    } else {
        println("$number is odd")
    }

    // when: Notice how the 'when' has no argument
    when {
        number % 2 == 0 -> println("$number is even")
        else -> println("$number is odd")
    }
}

/*
Equivalent switch in Java:

        switch (month) {
            case 12:
            case 1:
            case 2:
                season = "Winter";
                break;
            case 3:
            case 4:
            case 5:
                season = "Spring";
                break;
            case 6:
            case 7:
            case 8:
                season = "Summer";
                break;
            case 9:
            case 10:
            case 11:
                season = "Autumn";
                break;
            default:
                season = "Not a valid month";

        }
 */