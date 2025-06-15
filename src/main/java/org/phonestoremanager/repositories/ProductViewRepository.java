package org.phonestoremanager.repositories;

import org.phonestoremanager.models.ProductViewModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProductViewRepository implements IRepository<ProductViewModel> {
    public static ProductViewRepository getInstance() {
        return new ProductViewRepository();
    }

    public ArrayList<ProductViewModel> selectByBrand(String brand) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
        Set<Integer> productIDs = new HashSet<>();

        String sql = "SELECT * FROM Product p\n" +
                "JOIN Brand b ON b.BrandID = p.BrandID\n" +
                "JOIN ProductDetail pd ON pd.ProductID = p.ProductID\n" +
                "WHERE b.Name = ?";

        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                if (productIDs.contains(productID)) continue;
                productIDs.add(productID);

                int brandID = resultSet.getInt("BrandID");
                String name = resultSet.getString("Name");
                String description = resultSet.getNString("Description");
                String image = resultSet.getString("Image");
                double price = resultSet.getDouble("Price");

                ProductViewModel productViewModel = new ProductViewModel(productID, brandID, name, description, image, price);
                productViewModels.add(productViewModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productViewModels;
    }

    public ArrayList<ProductViewModel> searchByName(String keyword) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
        Set<Integer> productIDs = new HashSet<>();

        String sql = "SELECT * FROM Product p " +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID " +
                "WHERE p.name LIKE ?";

        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                if (productIDs.contains(productID)) continue;
                productIDs.add(productID);

                int brandID = resultSet.getInt("BrandID");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");
                String image = resultSet.getString("Image");
                double price = resultSet.getDouble("Price");

                ProductViewModel productViewModel = new ProductViewModel(productID, brandID, name, description, image, price);
                productViewModels.add(productViewModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productViewModels;
    }

    @Override
    public ArrayList<ProductViewModel> selectAll() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
        Set<Integer> productIDs = new HashSet<>();

        String sql = "SELECT * FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID";

        try (Connection conn = databaseConnection.connectionData()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                if (productIDs.contains(productID)) continue;
                productIDs.add(productID);

                int brandID = resultSet.getInt("BrandID");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");
                String image = resultSet.getString("Image");
                double price = resultSet.getDouble("Price");

                ProductViewModel productViewModel = new ProductViewModel(productID, brandID, name, description, image, price);
                productViewModels.add(productViewModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productViewModels;
    }

    @Override
    public ArrayList<ProductViewModel> selectDetailById() {
        return null;
    }
}
