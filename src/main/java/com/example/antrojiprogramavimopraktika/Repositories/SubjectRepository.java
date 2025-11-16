package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Interfaces.ISubjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SubjectRepository implements ISubjectRepository {

    @Override
    public List<Subject> getAllUnassignedSubjects() {
        List<Subject> subjects = new ArrayList<>();

        String sql = """
            SELECT s.subjectID, s.subjectName
            FROM subject s
            LEFT JOIN teachersubjects ts
                ON ts.subjectID = s.subjectID
            WHERE ts.subjectID IS NULL;
        """;

        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                subjects.add(new Subject(
                        rs.getInt("subjectID"),
                        rs.getString("subjectName")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjects;
    }
}
