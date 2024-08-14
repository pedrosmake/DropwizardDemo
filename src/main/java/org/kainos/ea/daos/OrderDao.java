package org.kainos.ea.daos;

import org.kainos.ea.models.Customer;
import org.kainos.ea.models.Order;
import org.kainos.ea.models.OrderRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT OrderID, CustomerID, Name as `CustomerName`, "
                            + "OrderDate FROM `Order` "
                            + "join `Customer` using (CustomerID);");

            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("OrderID"),
                        new Customer(resultSet.getInt("CustomerID"),
                                resultSet.getString("CustomerName")),
                        resultSet.getTimestamp("OrderDate"));

                orders.add(order);
            }
        }

        return orders;
    }

    public Order getOrderById(final int orderId) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT OrderID, CustomerID, Name as `CustomerName`,"
                    + "OrderDate FROM `Order` "
                    + "join `Customer` using (CustomerID) "
                    + "WHERE OrderID = ?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new Order(resultSet.getInt("OrderID"),
                        new Customer(resultSet.getInt("CustomerID"),
                                resultSet.getString("CustomerName")),
                        resultSet.getTimestamp("OrderDate"));
            }
        }
        return null;
    }

    public int createOrder(final OrderRequest orderRequest)
            throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        String insertStatement =
                "INSERT INTO `Order` (CustomerID, OrderDate) VALUES (?, ?);";

        PreparedStatement st = c.prepareStatement(insertStatement,
                Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, orderRequest.getCustomerId());
        st.setTimestamp(2, orderRequest.getOrderDate());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void updateOrder(final int id, final OrderRequest orderRequest)
            throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        String updateStatement =
                "UPDATE `Order` SET CustomerID = ? " + "WHERE OrderID = ?;";

        PreparedStatement st = c.prepareStatement(updateStatement);
        st.setInt(1, orderRequest.getCustomerId());
        st.setInt(2, id);

        st.executeUpdate();

    }

    public void deleteOrder(final int orderId) throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        String deleteStatement = "DELETE FROM `Order` WHERE OrderID = ?;";
        PreparedStatement st = c.prepareStatement(deleteStatement);
        st.setInt(1, orderId);
        st.executeUpdate();
    }
}
