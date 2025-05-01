package org.phonestoremanager.daos;

import org.phonestoremanager.models.ProductSpecificationModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailDAO {
    public static ProductDetailDAO getInstance() {
        return new ProductDetailDAO();
    }

    public ProductSpecificationModel getDetailByProductID(int productID) {
        ProductSpecificationModel spec = null;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "SELECT Ram, Rom, Chip, ScreenSize, ScanFrequency, CameraFront, CameraRear, BatteryCapacity, Price FROM ProductDetail\n" +
                "WHERE ProductID = ?";

        try (Connection conn = databaseConnection.connectionData();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                spec = new ProductSpecificationModel();
                spec.setChip(resultSet.getString("Chip"));
                spec.setRam(resultSet.getString("Ram"));
                spec.setRom(resultSet.getString("Rom"));
                spec.setScreenSize(resultSet.getString("ScreenSize"));
                spec.setScanFrequency(resultSet.getString("ScanFrequency"));
                spec.setCameraFront(resultSet.getString("CameraFront"));
                spec.setCameraRear(resultSet.getString("CameraRear"));
                spec.setBatteryCapacity(resultSet.getString("BatteryCapacity"));
                spec.setPrice(resultSet.getString("Price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return spec;
    }
}
