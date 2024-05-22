package oop.java;

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

    public int getAge() {
        return Period.between(
                dob,
                LocalDate.now()
        ).getYears();
    }
}