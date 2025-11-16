package com.example.antrojiprogramavimopraktika.Repositories;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class GroupRepository implements IGroupRepository {

    @Override
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();

        String sql = "SELECT * FROM `groups`";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                groups.add(new Group(
                        rs.getInt("groupID"),
                        rs.getString("groupName")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }

    @Override
    public void deleteGroup(int groupID) {
        String sql = "DELETE FROM `groups` WHERE groupID = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, groupID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkIfUnique(String groupName) {
        String sql = "SELECT COUNT(1) FROM `groups` where groupName = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, groupName);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt(1) == 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addGroup(String groupName) {
        String sql = "INSERT INTO `groups` (groupName) VALUES (?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, groupName);
           return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
