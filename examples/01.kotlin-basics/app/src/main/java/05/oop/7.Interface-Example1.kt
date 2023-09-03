package oop

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//Interface does not have an implementation
//Interface can be implemented by different classes
interface Payable {
    fun getPayText(): String
    fun getPayAmount(): Double
}

class Employee (val firstname: String, val lastname:String, val salary: Double) : Payable {
    override fun getPayText() = "$firstname $lastname"
    override fun getPayAmount() = salary
}

class Invoice(val invoiceDate: LocalDate, val totalAmount: Double) : Payable {
    //@TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPayText(): String {
        val dateStr = invoiceDate.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
        return "Invoice $dateStr"
    }

    override fun getPayAmount() = totalAmount
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val payables = listOf<Payable>(
        Employee("Ali", "Faleh", 1000.5),
        Employee("Sara", "Saleh", 1500.5),
        Invoice(LocalDate.now(), 5000.0)
    )

    payables.forEach { println("Pay ${it.getPayText()}: ${it.getPayAmount()}") }
}