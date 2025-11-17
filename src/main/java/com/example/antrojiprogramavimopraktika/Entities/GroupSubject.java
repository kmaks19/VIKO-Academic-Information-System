package com.example.antrojiprogramavimopraktika.Entities;

public final class GroupSubject {

    private final int id;
    private final Group group;
    private final Subject subject;

    public GroupSubject(int id, Group group, Subject subject) {
        this.id = id;
        this.group = group;
        this.subject = subject;
    }

    public int getID() {
        return this.id;
    }

    public Group getGroup() {
        return this.group;
    }

    public Subject getSubject() {
        return this.subject;
    }
}
