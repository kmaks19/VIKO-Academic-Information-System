package com.example.antrojiprogramavimopraktika.Files;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.*;
import com.example.antrojiprogramavimopraktika.Interfaces.IUserRepository;
import com.example.antrojiprogramavimopraktika.Repositories.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class LoginManager {

    private final IUserRepository repo;

    public LoginManager() {
        this.repo = new UserRepository();
    }

    public Person login(String username, String password) {
        try(Connection conn = Database.getConnection()) {
            ResultSet rs = repo.findUserByUP(conn, username, password);

            if(!rs.next()) {
                return null;
            }

            int userID = rs.getInt("userID");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
            String email = rs.getString("email");
            String role = rs.getString("role");

            switch(role) {
                case "admin":
                    return new Admin(userID, firstName, lastName, birthDate, email);
                case "teacher":
                    return new Teacher(userID, firstName, lastName, birthDate, email);
                case "student": {
                    return new Student(userID, firstName, lastName, birthDate, email);
                }
                default:
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
