package org.phonestoremanager.repositories;

import org.phonestoremanager.models.BrandModel;
import org.phonestoremanager.models.ProductModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;

public class ProductRepository{
    public static ProductRepository getInstance() {
        return new ProductRepository();
    }

    public void insertProduct(int productID, BrandModel brandID, String name, String description) {
        String sql = "INSERT INTO Product (ProductID ,BrandID, Name, Description)\n" +
                "VALUES (? ,?, ?, ?)";
        try(Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, brandID.getBrandID());
            preparedStatement.setNString(3, name);
            preparedStatement.setNString(4, description);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm dòng sản phẩm thành công.");
            } else {
                System.out.println("Không thêm được dòng sản phẩm.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMaxProductID() {
        String sql = "SELECT MAX(ProductID) FROM Product";
        try (Connection connection = DatabaseConnection.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void deleteProduct(ProductModel p) {
        String deleteProductDetailSQL = "DELETE FROM ProductDetail WHERE ProductID = ?";
        String deleteProductSQL = "DELETE FROM Product WHERE ProductID = ?";

        try (Connection connection = DatabaseConnection.createConnection()) {
            // Tắt auto-commit để thực hiện transaction
            connection.setAutoCommit(false);

            // Xóa trong ProductDetail trước
            try (PreparedStatement stmtDetail = connection.prepareStatement(deleteProductDetailSQL)) {
                stmtDetail.setInt(1, p.getProductID());
                stmtDetail.executeUpdate();
            }

            // Sau đó xóa trong Product
            try (PreparedStatement stmtProduct = connection.prepareStatement(deleteProductSQL)) {
                stmtProduct.setInt(1, p.getProductID());
                stmtProduct.executeUpdate();
            }

            // Commit nếu cả hai xóa thành công
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa sản phẩm và chi tiết sản phẩm: " + e.getMessage(), e);
        }
    }

    public int getProductIDByNameProduct(String nameProduct) {
        String sql = "SELECT ProductID FROM Product\n" +
                "WHERE Name = ?";
        try (Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nameProduct);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ProductID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
