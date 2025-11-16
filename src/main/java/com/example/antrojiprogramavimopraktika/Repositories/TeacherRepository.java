package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Teacher;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TeacherRepository implements ITeacherRepository {

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        String sql = """
            SELECT userID, firstName, lastName, email, birthDate
            FROM user
            WHERE role = 'teacher'
        """;

        try(Connection conn = Database.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                teachers.add(new Teacher(
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
        return teachers;
    }
}
