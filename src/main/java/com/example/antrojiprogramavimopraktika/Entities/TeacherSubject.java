package com.example.antrojiprogramavimopraktika.Entities;

public final class TeacherSubject {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String subjectName;

    public TeacherSubject(int id, String firstName, String lastName, String subjectName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
