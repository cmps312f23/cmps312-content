package oop

fun String.isPhoneNum() = length == 7 && all { it.isDigit() }
/*
    - By default, all classes in kotlin are 'final'.
    Therefore, to allow extending a class it must be marked as 'open'
    - We must explicitly marks as 'open' the methods and properties from
      the base class that are allowed to be overriden.
    - A class property or method marked as 'open' cannot have a visibility of 'private'
    - If a class is 'final' then declaring properties or methods as 'open' has no effect.
 */
// You can set default values for properties, if necessary
open class Person(var firstName: String,
                  val lastName: String, val age: Int) {

    var id : Int = 0
    var mobile : String = ""

    // Secondary constructor must call the primary constructor with "this".
    constructor(firstName: String,
                lastName: String,
                age: Int, mobile: String) : this(firstName, lastName, age) {

        if (mobile.isPhoneNum())
            this.mobile = mobile
    }

    val fullName: String
         get() = "$firstName $lastName"

    fun isUnderAge() = age < 18

    override fun toString() = "$firstName $lastName. Age: $age"

    /* The primary constructor cannot contain code, so use the init block
        for initialization and data validation
    */
    init {
            // Initialization code goes here such as auto-setting the id
            id = (1..100).shuffled().first()
            //Validation
            if (age <=0)
                throw IllegalArgumentException("$age is invalid age")
    }
}