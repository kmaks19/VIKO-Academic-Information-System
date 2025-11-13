package com.example.antrojiprogramavimopraktika.Entities;

public final class Teacher extends Person {
    public Teacher(int userID, String firstName, String lastName, String birthDate, String email) {
        super(userID, firstName, lastName, birthDate, email);
    }

    @Override
    public String getRole() {
        return "teacher";
    }
}
