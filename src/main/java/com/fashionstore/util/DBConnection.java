package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Database URL
    private static final String URL =
            "jdbc:mysql://localhost:3306/fashion_store";

    // MySQL Username
    private static final String USER = "root";

    // MySQL Password
    // Change "root" if your MySQL password is different
    private static final String PASSWORD = "root";

    // Load JDBC Driver
    static {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("MySQL JDBC Driver Loaded Successfully!");

        } catch (Exception e) {

            System.out.println("Driver Loading Failed!");

            e.printStackTrace();
        }
    }

    // Get Database Connection
    public static Connection getConnection() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {

            System.out.println("Database Connection Failed!");

            e.printStackTrace();
        }

        return connection;
    }
}