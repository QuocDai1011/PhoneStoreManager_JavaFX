package org.phonestoremanager.repositories;

import javafx.scene.chart.PieChart;
import org.phonestoremanager.models.ManageModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManageRepository implements IRepository{
    public static ManageRepository getInstance() {
        return new ManageRepository();
    }

    @Override
    public ArrayList<ManageModel> selectAll() {
        ArrayList<ManageModel> manageModels = new ArrayList<>();
        Set<Integer> manageIDs = new HashSet<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "SELECT p.ProductID, Name, Price, StockQuantity, Image FROM Product p " +
                "JOIN ProductDetail pd ON pd.ProductID = p.ProductID";

        try (Connection conn = databaseConnection.connectionData();
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");

                if (manageIDs.contains(productID))
                    continue;

                manageIDs.add(productID);

                String name = resultSet.getString("Name");
                double price = resultSet.getDouble("Price");
                int stock = resultSet.getInt("StockQuantity");
                String image = resultSet.getString("Image");

                // ✅ Dùng constructor mới có productID
                ManageModel manageModel = new ManageModel(productID, name, price, image, stock);

                // ✅ Gán ProductViewModel
                manageModel.setProductViewModel(
                        ProductViewRepository.getInstance().selectByID(productID)
                );

                manageModels.add(manageModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manageModels;
    }

    public List<ManageModel> searchByName(String value) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<ManageModel> manageModels = new ArrayList<>();
        Set<Integer> manageIDs = new HashSet<>();

        String sql = "SELECT p.ProductID, Name, Price, StockQuantity, Image FROM Product p " +
                "JOIN ProductDetail pd ON pd.ProductID = p.ProductID " +
                "WHERE p.Name LIKE ?";

        try (Connection conn = databaseConnection.connectionData();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");

                if (manageIDs.contains(productID))
                    continue;

                manageIDs.add(productID);

                String name = resultSet.getString("Name");
                double price = resultSet.getDouble("Price");
                String image = resultSet.getString("Image");
                int stock = resultSet.getInt("StockQuantity");

                // ✅ Dùng constructor có productID
                ManageModel manageModel = new ManageModel(productID, name, price, image, stock);

                // ✅ Gán ProductViewModel để tránh lỗi NullPointerException
                manageModel.setProductViewModel(
                        ProductViewRepository.getInstance().selectByID(productID)
                );

                manageModels.add(manageModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return manageModels;
    }

    @Override
    public ArrayList selectDetailById() {
        return null;
    }
}
