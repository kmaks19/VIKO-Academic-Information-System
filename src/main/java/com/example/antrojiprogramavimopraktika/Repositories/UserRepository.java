package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Interfaces.IUserRepository;

import java.sql.*;
import java.time.LocalDate;

public final class UserRepository implements IUserRepository {

    public UserRepository() {}

    // Finds user by username and password
    @Override
    public ResultSet findUserByUP(Connection conn, String username, String password) throws SQLException {
        String sql = "SELECT userID, firstName, lastName, birthDate, email, role FROM `user` WHERE username = ? AND password = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        return ps.executeQuery();
    }

    @Override
    // Finds user by first name and last name
    public boolean userExists(String firstName, String lastName) {
        String sql = "SELECT 1 FROM user WHERE firstName = ? AND lastName = ? LIMIT 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUser(int userID) {
        String sql = "DELETE FROM `user` WHERE userID = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUser(String firstName, String lastName, String email, LocalDate birthdate, String role) {
        String sql = "INSERT INTO `user` (firstName, lastName, email, birthDate, role, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setDate(4, Date.valueOf(birthdate));
            stmt.setString(5, role);
            stmt.setString(6, firstName);
            stmt.setString(7, lastName);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
