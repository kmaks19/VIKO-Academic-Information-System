package com.example.antrojiprogramavimopraktika.Entities;

public final class Subject {
    private final int id;
    private final String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
