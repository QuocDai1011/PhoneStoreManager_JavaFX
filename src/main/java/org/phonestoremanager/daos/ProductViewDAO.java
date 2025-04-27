package org.phonestoremanager.daos;

import org.phonestoremanager.models.ProductViewModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProductViewDAO implements DAOInterface<ProductViewModel> {
    public static ProductViewDAO getInstance() {
        return new ProductViewDAO();
    }

    @Override
    public ArrayList<ProductViewModel> selectAll() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
        Set<Integer> productIDs = new HashSet<>();  // Set to keep track of ProductIDs that have already been added

        try(Connection conn = databaseConnection.connectionData()) {
            Statement statement = conn.createStatement();

            String sql = "SELECT * FROM Product p\n" +
                    "JOIN ProductDetail pd ON p.ProductID = pd.ProductID;";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                int productID = resultSet.getInt("ProductID");  // Get the ProductID

                // If the ProductID has already been processed, skip this record
                if (productIDs.contains(productID)) {
                    continue;
                }

                // Otherwise, add the ProductID to the set
                productIDs.add(productID);

                String image = resultSet.getString("image");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                ProductViewModel productViewModel = new ProductViewModel(image, name, price);

                productViewModels.add(productViewModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productViewModels;
    }
}
