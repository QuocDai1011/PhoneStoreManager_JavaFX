package org.phonestoremanager.repositories;

import org.phonestoremanager.models.OrderUpdateModel;
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

    //TODO
    public int getProductDetailIDByInfomation(OrderUpdateModel orderUpdateModel) {
        int id = -1;
        String sql = "SELECT pd.ProductDetailID\n" +
                "FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n" +
                "JOIN ColorOfProduct c ON pd.ColorID = c.ColorOfProductID\n" +
                "WHERE p.Name = ? AND pd.Ram = ? AND pd.Rom = ? AND c.NameColor = ?;";

        try(Connection con = DatabaseConnection.createConnection()) {
            assert con != null;
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, orderUpdateModel.getProductName());
            st.setInt(2, orderUpdateModel.getRam());
            st.setInt(3, orderUpdateModel.getRom());
            st.setString(4, orderUpdateModel.getColor());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ProductDetailID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
}
