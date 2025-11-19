package com.example.antrojiprogramavimopraktika.Entities;

import java.time.LocalDate;

public final class Grade {

    private final int id;
    private final Student student;
    private final Subject subject;
    private int grade;
    private final LocalDate gradeDate;

    public Grade(int id, Student student, Subject subject, int grade, LocalDate gradeDate) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.grade = grade;
        this.gradeDate = gradeDate;
    }

    public int getId() {
        return this.id;
    }

    public Student getStudent() {
        return this.student;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return this.gradeDate;
    }
}
