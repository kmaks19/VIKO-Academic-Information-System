package com.example.antrojiprogramavimopraktika.Files;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Entities.Admin;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Entities.Person;
import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Entities.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class FindUser {

    public FindUser() {}

    // Find user by username and password then find his role and according to role create object, if student search for his group and retrieve group data
    public Person findUserByUP(String username, String password) {
        String sql =
            "SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1";

        try (Connection conn = Database.getConnection()) {
            String sqlUser =
                "SELECT userID, firstName, lastName, birthDate, email, role FROM user WHERE username = ? AND password = ?";

            PreparedStatement ps = conn.prepareStatement(sqlUser);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            int userID = rs.getInt("userID");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String birthDate = rs.getString("birthDate");
            String email = rs.getString("email");
            String role = rs.getString("role");

            switch (role) {
                case "admin":
                    return new Admin(
                        userID,
                        firstName,
                        lastName,
                        birthDate,
                        email
                    );
                case "teacher":
                    return new Teacher(
                        userID,
                        firstName,
                        lastName,
                        birthDate,
                        email
                    );
                case "student": {
                    String sqlGroup =
                        "SELECT sg.groupID, g.groupName " +
                        "FROM studentgroup sg " +
                        "LEFT JOIN `groups` g ON g.groupID = sg.groupID " +
                        "WHERE sg.userID = ? LIMIT 1";

                    PreparedStatement psGroup = conn.prepareStatement(sqlGroup);

                    psGroup.setInt(1, userID);

                    ResultSet rsGroup = psGroup.executeQuery();

                    Group group = null;

                    if (rsGroup.next()) {
                        int groupID = rsGroup.getInt("groupID");
                        String groupName = rsGroup.getString("groupName");
                        group = new Group(groupID, groupName);
                    }

                    return new Student(
                        userID,
                        firstName,
                        lastName,
                        birthDate,
                        email,
                        group
                    );
                }
                default:
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
