package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Teacher;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class UserRepository {

    public UserRepository() {}

    // Finds user by username and password
    public ResultSet findUserByUP(Connection conn, String username, String password) throws SQLException {
        String sql = "SELECT userID, firstName, lastName, birthDate, email, role FROM `user` WHERE username = ? AND password = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        return ps.executeQuery();
    }

    public ResultSet findGroupByUserID(Connection conn, int userID) throws SQLException {
        String sql = """
            SELECT sg.groupID, g.groupName
            FROM `studentgroup` sg
            LEFT JOIN `groups` g ON sg.groupID = g.groupID
            WHERE sg.userID = ?
            LIMIT 1
        """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, userID);

        return ps.executeQuery();
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();

        String sql = "SELECT userID, firstName, lastName, email, birthDate FROM `user` WHERE `role` = 'teacher'";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                list.add(new Teacher(
                        rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("birthDate").toLocalDate(),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

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
