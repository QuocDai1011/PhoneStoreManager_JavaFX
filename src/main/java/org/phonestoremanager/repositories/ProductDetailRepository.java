package org.phonestoremanager.repositories;

import org.phonestoremanager.models.ProductSpecificationModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailRepository {
    public static ProductDetailRepository getInstance() {
        return new ProductDetailRepository();
    }

    public ProductSpecificationModel getDetailByProductIDAndROM(int productID, int ROM) {
        ProductSpecificationModel spec = null;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "SELECT * FROM Product p\n" +
                "JOIN Brand b ON b.BrandID = p.BrandID\n" +
                "JOIN ProductDetail pd ON pd.ProductID = p.ProductID\n" +
                "WHERE p.ProductID = ? AND pd.ROM = ?";

        try (Connection conn = databaseConnection.connectionData();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, ROM);;
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
