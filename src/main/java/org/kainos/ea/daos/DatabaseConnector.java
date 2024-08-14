package org.kainos.ea.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            return connection;
        }

        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        String host = System.getenv("DB_HOST");
        String database = System.getenv("DB_NAME");

        if (username == null || password == null || host == null
                || database == null) {
            throw new IllegalArgumentException("Environment variables not set");
        }

        connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + database + "?useSSL=false",
                username,
                password);

        return connection;
    }
}
