package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Grade;
import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Interfaces.IGradeRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class GradeRepository implements IGradeRepository {

    @Override
    public List<Grade> getGradeListForTeacher(int teacherID) {
        List<Grade> list = new ArrayList<>();

        String sql = """
            SELECT
                g.gradeID,
                g.grade,
                g.gradeDate,
                u.userID,
                u.firstName,
                u.lastName,
                u.birthDate,
                u.email,
                s.subjectID,
                s.subjectName
            FROM grades g
            JOIN user u
                ON u.userID = g.studentID
            JOIN subject s
                ON s.subjectID = g.subjectID
            JOIN studentgroup sg
                ON sg.userID = u.userID
            JOIN groupsubjects gs
                ON gs.groupID = sg.groupID
                AND gs.subjectID = s.subjectID
            JOIN teachersubjects ts
                ON ts.subjectID = s.subjectID
            WHERE ts.teacherID = ?
            ORDER BY u.lastName, s.subjectName, g.gradeDate DESC;
        """;

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherID);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                list.add(new Grade(
                        rs.getInt("gradeID"),
                        new Student(
                                rs.getInt("userID"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getDate("birthDate").toLocalDate(),
                                rs.getString("email")
                        ),
                        new Subject(
                                rs.getInt("subjectID"),
                                rs.getString("subjectName")
                        ),
                        rs.getInt("grade"),
                        rs.getDate("gradeDate").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean addGrade(int studentID, int subjectID, int grade, LocalDate gradeDate) {
        String sql = """
            INSERT INTO grades (studentID, subjectID, grade, gradeDate)
            VALUES (?, ?, ?, ?)
        """;

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentID);
            stmt.setInt(2, subjectID);
            stmt.setInt(3, grade);
            stmt.setDate(4, Date.valueOf(gradeDate));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateGrade(int gradeID, int newGrade) {
        String sql = "UPDATE grades SET grade = ? WHERE gradeID = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newGrade);   // FIXED
            stmt.setInt(2, gradeID);    // FIXED

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Grade> getGradeListForStudent(int studentID) {
        List<Grade> list = new ArrayList<>();

        String sql = """
            SELECT
                g.gradeID,
                g.grade,
                g.gradeDate,
                s.subjectID,
                s.subjectName
            FROM grades g
            JOIN subject s
                ON s.subjectID = g.subjectID
            WHERE g.studentID = ?
            ORDER BY g.gradeDate DESC;
        """;

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                list.add(new Grade(
                        rs.getInt("gradeID"),
                        null,
                        new Subject(
                                rs.getInt("subjectID"),
                                rs.getString("subjectName")
                        ),
                        rs.getInt("grade"),
                        rs.getDate("gradeDate").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}

