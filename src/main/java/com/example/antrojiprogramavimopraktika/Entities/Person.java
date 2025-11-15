package com.example.antrojiprogramavimopraktika.Entities;

import java.time.LocalDate;

public abstract class Person {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;
    protected String email;

    public Person(int userID, String firstName, String lastName, LocalDate birthDate, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public abstract String getRole();
}
