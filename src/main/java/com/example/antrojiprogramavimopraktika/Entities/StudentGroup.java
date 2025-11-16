package com.example.antrojiprogramavimopraktika.Entities;

public final class StudentGroup {
    private final int studentGroupID;
    private final Student student;
    private final Group group;

    public StudentGroup(int studentGroupID, Student student, Group group) {
        this.studentGroupID = studentGroupID;
        this.student = student;
        this.group = group;
    }

    public int getStudentGroupID() {
        return this.studentGroupID;
    }

    public Student getStudent() {
        return this.student;
    }

    public Group getGroup() {
        return this.group;
    }
}
