package oop.java;

import androidx.annotation.RequiresApi;
import java.time.LocalDate;
import java.time.Period;

public class Person {
    private final String name;
    private LocalDate dob;

    public Person(String name, LocalDate dob) {
        this.name = name;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) { this.dob = dob; }

    @RequiresApi(api = android.os.Build.VERSION_CODES.O)
    public int getAge() {
        return Period.between(
                dob,
                LocalDate.now()
        ).getYears();
    }
}