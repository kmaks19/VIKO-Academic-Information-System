package com.example.antrojiprogramavimopraktika.Entities;

import java.time.LocalDate;

public final class Teacher extends Person {
    public Teacher(int userID, String firstName, String lastName, LocalDate birthDate, String email) {
        super(userID, firstName, lastName, birthDate, email);
    }

    public int getID() {
        return this.userID;
    }

    @Override
    public String getRole() {
        return "teacher";
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
