package com.example.antrojiprogramavimopraktika.Entities;

public final class Student extends Person {

    private final Group group;

    public Student(int userID, String firstName, String lastName, String birthDate, String email, Group group) {
        super(userID, firstName, lastName, birthDate, email);
        this.group = group;
    }

    public Group getGroup() { return this.group; }

    @Override
    public String getRole() {
        return "student";
    }
}
