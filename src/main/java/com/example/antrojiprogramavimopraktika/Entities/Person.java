package com.example.antrojiprogramavimopraktika.Entities;

public abstract class Person {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String birthDate;
    protected String email;

    public Person(int userID, String firstName, String lastName, String birthDate, String email) {
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

    public String getBirthDate() {
        return this.birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public abstract String getRole();
}
