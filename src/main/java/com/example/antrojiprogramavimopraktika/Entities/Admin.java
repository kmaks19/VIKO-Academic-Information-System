package com.example.antrojiprogramavimopraktika.Entities;

import java.time.LocalDate;

public final class Admin extends Person {
    public Admin(int userID, String firstName, String lastName, LocalDate birthDate, String email) {
        super(userID, firstName, lastName, birthDate, email);
    }

    @Override
    public String getRole() {
        return "admin";
    }
}
