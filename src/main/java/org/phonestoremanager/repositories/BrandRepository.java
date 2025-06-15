package org.phonestoremanager.repositories;

import org.phonestoremanager.models.BrandModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class BrandRepository implements IRepository<BrandModel>{
    public static BrandRepository getInstance() {
        return  new BrandRepository();
    }

    @Override
    public ArrayList<BrandModel> selectAll() {
        ArrayList<BrandModel> brandModelList = new ArrayList<>();
        String sql = "SELECT * FROM Brand";
        try (Connection connection = DatabaseConnection.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("BrandID");
                String name = resultSet.getNString("Name");
                brandModelList.add(new BrandModel(id, name));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return brandModelList;
    }

    @Override
    public ArrayList<BrandModel> selectDetailById() {
        return null;
    }

    public String getBrandNameByID(int brandID) {
        String sql = "SELECT Name FROM Brand WHERE BrandID = ?";
        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, brandID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getNString("Name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
