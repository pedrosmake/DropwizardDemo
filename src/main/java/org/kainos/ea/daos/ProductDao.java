package org.kainos.ea.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.kainos.ea.models.Product;
import org.kainos.ea.models.ProductRequest;

public class ProductDao {

	public List<Product> getAllOrders() throws SQLException {
		List<Product> products = new ArrayList<>();

		try (Connection connection = DatabaseConnector.getConnection()) {
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT ProductID, Name, Price FROM Product;");

			while (resultSet.next()) {
				Product product = new Product(resultSet.getInt("ProductID"), resultSet.getString("Name"),
						resultSet.getDouble("Price"));

				products.add(product);
			}
		}

		return products;
	}

	public Product getProductById(int productId) throws SQLException {
		try (Connection connection = DatabaseConnector.getConnection()) {
			PreparedStatement statement = connection
					.prepareStatement("SELECT ProductID, Name, Price FROM Product WHERE ProductID = ?");
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return new Product(resultSet.getInt("ProductID"), resultSet.getString("Name"),
						resultSet.getDouble("Price"));
			}
		}
		return null;
	}

	public int createProduct(ProductRequest productRequest) throws SQLException {
		Connection c = DatabaseConnector.getConnection();
		String insertStatement = "INSERT INTO Product (Name, Description, Price) VALUES (?, ?, ?)";
		PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

		st.setString(1, productRequest.getName());
		st.setString(2, productRequest.getDescription());
		st.setDouble(3, productRequest.getPrice());

		st.executeUpdate();

		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	public void updateProduct(int id, ProductRequest productRequest) throws SQLException {
		Connection c = DatabaseConnector.getConnection();

		String updateStatement = "UPDATE Product SET Name = ?, Description = ?, Price = ? WHERE ProductID = ?";
		System.out.println(updateStatement);

		PreparedStatement st = c.prepareStatement(updateStatement);

		st.setString(1, productRequest.getName());
		st.setString(2, productRequest.getDescription());
		st.setDouble(3, productRequest.getPrice());
		st.setInt(4, id);
		System.out.println(st);

		st.executeUpdate();
	}

	public void deleteProduct(int id) throws SQLException {
		Connection c = DatabaseConnector.getConnection();
		String deleteStatement = "DELETE FROM Product WHERE ProductID = ?";
		PreparedStatement st = c.prepareStatement(deleteStatement);
		st.setInt(1, id);
		st.executeUpdate();
	}
}
