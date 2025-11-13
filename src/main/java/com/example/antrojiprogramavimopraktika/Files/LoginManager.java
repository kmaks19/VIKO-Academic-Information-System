package com.example.antrojiprogramavimopraktika.Files;

import com.example.antrojiprogramavimopraktika.Entities.Person;

public final class LoginManager {

    private final FindUser findUser;

    public LoginManager() {
        this.findUser = new FindUser();
    }

    public Person login(String username, String password) {
        return findUser.findUserByUP(username, password);
    }
}
