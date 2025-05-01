package org.phonestoremanager.daos;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageProductDAO {
    public static ImageProductDAO getInstance() {
        return new ImageProductDAO();
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

    public List<String> getImageByProductIDColorAndROM(int productID, String color, String rom) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<String> imageList = new ArrayList<>();
        String query = "SELECT Image FROM ProductDetail\n" +
                "WHERE ProductID = ? AND Color = ? AND Rom = ?;";

        try (Connection conn = databaseConnection.connectionData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productID);
            stmt.setString(2, color);  // Không cần dấu nháy đơn quanh "color"
            stmt.setString(3, rom);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                imageList.add(rs.getString("Image"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        };

        return imageList;
    }


}
