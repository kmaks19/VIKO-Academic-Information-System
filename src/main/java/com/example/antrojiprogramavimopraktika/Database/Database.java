package com.example.antrojiprogramavimopraktika.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

    private Database() {}

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "akademine_sistema";
    private static final String FULL_URL = URL + DATABASE_NAME;
    private static final String USER = "root";
    // Note: (CHANGE)
    //  Linux password ROOT
    //  For windows password ""
    // Two different OS
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FULL_URL, USER, PASSWORD);
    }

    public static void createIfNotExists() {
        String query = "CREATE DATABASE IF NOT EXISTS akademine_sistema;";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = conn.createStatement()
        ) {
            st.execute(query);
        } catch (SQLException e) {
            System.err.println("Failed to create database: " + e.getMessage());
        }
    }
}
