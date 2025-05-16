package org.phonestoremanager.repositories;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageProductRepository {
    public static ImageProductRepository getInstance() {
        return new ImageProductRepository();
    }

    public List<String> getImageByProductID(int productID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<String> imageList = new ArrayList<>();

        String sql = "SELECT Image FROM ProductDetail\n" +
                "WHERE ProductID = ?";

        try(Connection conn = databaseConnection.connectionData();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                imageList.add(resultSet.getString("image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imageList;
    }

    public List<String> getImageByProductIDColorIDAndROM(int productID, int colorID, String rom) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<String> imageList = new ArrayList<>();
        String query = "SELECT Image FROM ProductDetail pd\n" +
                "JOIN ColorOfProduct cp ON cp.ColorOfProductID = pd.ColorID\n" +
                "WHERE pd.ProductID = ? AND pd.Rom = ? AND cp.ColorOfProductID = ?;";

        try (Connection conn = databaseConnection.connectionData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productID);
            stmt.setString(2, rom);
            stmt.setInt(3, colorID);  // Không cần dấu nháy đơn quanh "color"

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                imageList.add(rs.getString("Image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imageList;
    }
}
