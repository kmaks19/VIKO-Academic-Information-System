package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.*;
import com.example.antrojiprogramavimopraktika.Interfaces.IStudentGroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentGroupRepository implements IStudentGroupRepository {

    @Override
    public List<StudentGroup> getAllStudentGroups() {
        List<StudentGroup> list = new ArrayList<>();

        String sql = """
            SELECT
                sg.studentGroupID,
                u.userID,
                u.firstName,
                u.lastName,
                u.email,
                u.birthDate,
                g.groupID,
                g.groupName
            FROM `studentgroup` sg
            JOIN user u ON u.userID = sg.userID
            JOIN `groups` g ON g.groupID = sg.groupID
            ORDER BY g.groupName, u.lastName;
        """;

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                list.add(new StudentGroup(
                        rs.getInt("studentGroupID"),
                        new Student(
                                rs.getInt("userID"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getDate("birthDate").toLocalDate(),
                                rs.getString("email")
                        ),
                        new Group(
                                rs.getInt("groupID"),
                                rs.getString("groupName")
                        )
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Student> getStudentsWithoutGroup() {
        List<Student> list = new ArrayList<>();

        String sql = """
            SELECT u.userID, u.firstName, u.lastName, u.birthDate, u.email
            FROM user u
            LEFT JOIN `studentgroup` sg ON sg.userID = u.userID
            WHERE u.role = 'student' AND sg.userID IS NULL
        """;

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                list.add(new Student(
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

    @Override
    public boolean assignStudentToGroup(int studentID, int groupID) {
        String sql = "INSERT INTO studentgroup (userID, groupID) VALUES (?, ?)";

        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, studentID);
            stmt.setInt(2, groupID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeStudentFromGroup(int studentGroupID) {
        String sql = "DELETE FROM studentgroup WHERE studentGroupID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentGroupID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
