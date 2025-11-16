package com.example.antrojiprogramavimopraktika.Interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public interface IUserRepository {
    ResultSet findUserByUP(Connection conn, String username, String password) throws SQLException;

    boolean userExists(String firstName, String lastName);

    void removeUser(int userID);

    boolean addUser(String firstName, String lastName, String email, LocalDate birthdate, String role);
}
