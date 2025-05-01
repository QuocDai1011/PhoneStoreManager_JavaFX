package org.phonestoremanager.daos;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductROMDAO {
    public static ProductROMDAO getInstance() {
        return new ProductROMDAO();
    }

    public List<String> getROMByProductID(int productID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<String> roms = new ArrayList<>();

        String sql = "SELECT Rom FROM ProductDetail\n" +
                "WHERE ProductID = ?;";

        try(Connection conn = databaseConnection.connectionData();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                roms.add(resultSet.getString("rom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roms;
    }
}
