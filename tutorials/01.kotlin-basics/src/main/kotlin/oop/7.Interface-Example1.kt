package oop

import java.time.LocalDate
import java.time.format.DateTimeFormatter

//Interface does not have an implementation
//Interface can be implemented by different classes
interface Payable {
    val defaulAmount : Int
    fun getPayText(): String
    fun getPayAmount(): Double
}

class Employee (val firstname: String, val lastname:String, val salary: Double) : Payable {
    override val defaulAmount: Int
        get() = 1000
     override fun getPayText() = "$firstname $lastname, pay them $salary"
     override fun getPayAmount() = salary
}

class Invoice(val invoiceDate: LocalDate, val totalAmount: Double) : Payable {
    override val defaulAmount: Int
        get() = 0

    override fun getPayText(): String {
        val dateStr = invoiceDate.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
        return "Invoice $dateStr"
    }

    override fun getPayAmount() = totalAmount
}

fun main() {
    val payables = listOf(
        Employee("Ali", "Faleh", 1000.5),
        Employee("Sara", "Saleh", 1500.5),
        Invoice(LocalDate.now(), 5000.0)
    )

    payables.forEach { println("Pay ${it.getPayText()}: ${it.getPayAmount()}") }
}