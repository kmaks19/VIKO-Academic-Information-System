package com.example.antrojiprogramavimopraktika.Entities;

public final class Admin extends Person {
    public Admin(int userID, String firstName, String lastName, String birthDate, String email) {
        super(userID, firstName, lastName, birthDate, email);
    }

    @Override
    public String getRole() {
        return "admin";
    }
}
