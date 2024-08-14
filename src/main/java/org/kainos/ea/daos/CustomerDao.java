package org.kainos.ea.daos;

import org.kainos.ea.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT CustomerID, name, address, phone FROM Customer");

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("CustomerID"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query =
                    "SELECT CustomerID, name, address, phone FROM Customer WHERE CustomerID=?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new Customer(resultSet.getInt("CustomerID"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"));
            }
            return null;
        }
    }
}
