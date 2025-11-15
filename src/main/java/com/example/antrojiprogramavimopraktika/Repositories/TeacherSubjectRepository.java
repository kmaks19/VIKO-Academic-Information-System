package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.TeacherSubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TeacherSubjectRepository {

    public List<TeacherSubject> getTeacherSubjects() {
        List<TeacherSubject> subjects = new ArrayList<>();

        String sql = """
            SELECT ts.tsSubjectID, u.firstName, u.lastName, s.subjectName
            FROM `teachersubjects` ts
            JOIN user u ON u.userID = ts.teacherID
            JOIN subject s ON s.subjectID = ts.subjectID
        """;

        try(Connection conn = Database.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                subjects.add(new TeacherSubject(
                        rs.getInt("tsSubjectID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("subjectName")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subjects;
    }
}
