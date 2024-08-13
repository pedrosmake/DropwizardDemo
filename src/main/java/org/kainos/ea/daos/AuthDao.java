package org.kainos.ea.daos;

import org.kainos.ea.models.LoginRequest;
import org.kainos.ea.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
    public User getUser(LoginRequest loginRequest) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query =
                    "SELECT Username, Password, RoleID FROM `User` WHERE username = ? AND Password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, loginRequest.getUsername());
            preparedStatement.setString(2, loginRequest.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new User(
                        resultSet.getString("Username"),
                        resultSet.getString("Password"),
                        resultSet.getInt("RoleID")
                );
            }
        }
        return null;
    }
}
