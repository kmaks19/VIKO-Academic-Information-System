package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Entities.GroupSubject;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupSubjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class GroupSubjectRepository implements IGroupSubjectRepository {


    @Override
    public List<Subject> getUnassignedSubjectsForGroup(int groupID) {
        List<Subject> list = new ArrayList<>();

        String sql = """
            SELECT s.subjectID, s.subjectName
            FROM `subject` s
            LEFT JOIN `groupsubjects` gs
                ON gs.subjectID = s.subjectID
                AND gs.groupID = ?
            WHERE gs.subjectID IS NULL;
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, groupID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Subject(
                        rs.getInt("subjectID"),
                        rs.getString("subjectName")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<GroupSubject> getGroupSubjects() {
        List<GroupSubject> list = new ArrayList<>();

        String sql = """
            SELECT
                gs.groupSubjectID,
                g.groupID,
                g.groupName,
                s.subjectID,
                s.subjectName
            FROM `groupsubjects` gs
            JOIN `groups` g ON g.groupID = gs.groupID
            JOIN `subject` s ON s.subjectID = gs.subjectID
            ORDER BY g.groupName, s.subjectName
        """;

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                list.add(new GroupSubject(
                        rs.getInt("groupSubjectID"),
                        new Group(
                                rs.getInt("groupID"),
                                rs.getString("groupName")
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

    @Override
    public boolean addGroupSubject(int groupID, int subjectID) {
        String sql = "INSERT INTO `groupsubjects` (groupID, subjectID) VALUES (?, ?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, groupID);
            stmt.setInt(2, subjectID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeGroupSubject(int groupSubjectID) {
        String sql = "DELETE FROM `groupsubjects` WHERE groupSubjectID = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, groupSubjectID);
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
