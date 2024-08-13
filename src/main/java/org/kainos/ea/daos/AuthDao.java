package org.kainos.ea.daos;

import org.kainos.ea.models.LoginRequest;
import org.kainos.ea.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public boolean createUser(LoginRequest loginRequest) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        String insertStatement = "INSERT INTO `User` (`Username`, `Password`, `RoleID`) VALUES (?, ?, ?);";
        PreparedStatement st = connection.prepareStatement(
                insertStatement,
                PreparedStatement.RETURN_GENERATED_KEYS
        );

        st.setString(1, loginRequest.getUsername());
        st.setString(2, loginRequest.getPassword());
        st.setInt(3, 2);

        int affectedRows = st.executeUpdate();

        if (affectedRows == 1) {
            return true;
        }
        return false;
    }
}
