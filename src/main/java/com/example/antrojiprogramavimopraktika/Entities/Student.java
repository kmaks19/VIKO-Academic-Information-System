package com.example.antrojiprogramavimopraktika.Entities;

import java.time.LocalDate;

public final class Student extends Person {

    public Student(int userID, String firstName, String lastName, LocalDate birthDate, String email) {
        super(userID, firstName, lastName, birthDate, email);
    }

    @Override
    public String getRole() {
        return "student";
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
