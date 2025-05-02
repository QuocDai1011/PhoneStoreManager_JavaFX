package org.phonestoremanager.repositories;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductColorRepository {
    public static ProductColorRepository getInstance() {
        return new ProductColorRepository();
    }

    public List<String> getColorByProductID(int productID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<String> colors = new ArrayList<>();
        String sql = "SELECT Color FROM ProductDetail\n" +
                "WHERE ProductID = ?";

        try(Connection connection = databaseConnection.connectionData();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                colors.add(resultSet.getString("color"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return colors;
    }
}
