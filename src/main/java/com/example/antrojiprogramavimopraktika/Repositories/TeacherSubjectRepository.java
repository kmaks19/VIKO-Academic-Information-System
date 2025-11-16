package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Entities.Teacher;
import com.example.antrojiprogramavimopraktika.Entities.TeacherSubject;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherSubjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TeacherSubjectRepository implements ITeacherSubjectRepository {

    @Override
    public List<TeacherSubject> getTeacherSubjects() {
        List<TeacherSubject> list = new ArrayList<>();

        String sql = """
            SELECT 
                ts.tsSubjectID,
                ts.teacherID,
                ts.subjectID,
    
                u.firstName,
                u.lastName,
                u.birthDate,
                u.email,
    
                s.subjectName
            FROM teachersubjects ts
            JOIN user u ON u.userID = ts.teacherID
            JOIN subject s ON s.subjectID = ts.subjectID
            ORDER BY u.lastName, s.subjectName
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new TeacherSubject(
                        rs.getInt("tsSubjectID"),
                        new Teacher(
                                rs.getInt("teacherID"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getDate("birthDate").toLocalDate(),
                                rs.getString("email")
                        ),
                        new Subject(
                                rs.getInt("subjectID"),
                                rs.getString("subjectName")
                        )
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean addTeacherSubject(int teacherID, int subjectID) {
        String sql = "INSERT INTO teachersubjects (teacherID, subjectID) VALUES (?, ?)";

        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, teacherID);
            stmt.setInt(2, subjectID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTeacherSubject(int tsSubjectID) {
        String sql = "DELETE FROM teachersubjects WHERE tsSubjectID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tsSubjectID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
