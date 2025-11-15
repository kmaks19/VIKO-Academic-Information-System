package com.example.antrojiprogramavimopraktika.Entities;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class Session {
    //Singleton pattern
    // Only one object exists.
    private static final Session instance = new Session();

    private final ObjectProperty<Person> currentUser = new SimpleObjectProperty<>();

    private Session() {}

    public static Session getInstance() {
        return instance;
    }

    public ObjectProperty<Person> getCurrentUserProperty() {
        return currentUser;
    }

    public Person getCurrentUserInstance() {
        return currentUser.get();
    }

    public void setCurrentUser(Person currentUser){
        this.currentUser.set(currentUser);
    }

    public void removeUser() {
        this.currentUser.set(null);
    }
}
