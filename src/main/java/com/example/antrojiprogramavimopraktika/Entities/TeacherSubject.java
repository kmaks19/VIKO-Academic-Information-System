package com.example.antrojiprogramavimopraktika.Entities;

public final class TeacherSubject {

    private final int id;
    private final Teacher teacher;
    private final Subject subject;

    public TeacherSubject(int id, Teacher teacher, Subject subject) {
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Subject getSubject() {
        return subject;
    }
}
