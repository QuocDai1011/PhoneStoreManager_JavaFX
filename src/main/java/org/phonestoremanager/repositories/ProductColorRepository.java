package org.phonestoremanager.repositories;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductColorRepository {
    public static ProductColorRepository getInstance() {
        return new ProductColorRepository();
    }

    public int getColorIDByNameColor(String nameColor) {
        String sql = "SELECT ColorOfProductID FROM ColorOfProduct\n" +
                "WHERE NameColor = ?";
        try (Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setNString(1, nameColor);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("ColorOfProductID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getColorIDByProductIDAndNameColor (int productID, String nameColor) {
        String sql = "SELECT ColorOfProductID FROM ColorOfProduct cp\n" +
                "JOIN ProductDetail pd ON pd.ColorID = cp.ColorOfProductID\n" +
                "WHERE pd.ProductID = ? AND cp.NameColor = ?;";
        try(Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            preparedStatement.setNString(2, nameColor);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ColorOfProductID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public List<String> getNameColorByProductID(int productID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<String> nameColors = new ArrayList<>();
        String sql = "SELECT NameColor FROM ColorOfProduct cp\n" +
                "JOIN ProductDetail pd ON pd.ColorID = cp.ColorOfProductID\n" +
                "WHERE ProductID = ?";

        try(Connection connection = databaseConnection.connectionData();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                nameColors.add(resultSet.getString("NameColor"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nameColors;
    }

    public int getColorIDByName(String name) {
        int id = 0;
        String sql = " SELECT TOP 1 ColorOfProductID\n" +
                " FROM ColorOfProduct\n" +
                " WHERE NameColor = ?;";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                id = rs.getInt("ColorOfProductID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

}
