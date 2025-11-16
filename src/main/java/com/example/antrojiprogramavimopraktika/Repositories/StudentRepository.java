package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Interfaces.IStudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class StudentRepository implements IStudentRepository {

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT
                    userID, firstName, lastName, email, birthDate
                FROM user
                WHERE role = 'student'
       """;

        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                students.add(new Student(
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
        return students;
    }
}
