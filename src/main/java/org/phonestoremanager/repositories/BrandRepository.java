package org.phonestoremanager.repositories;

import org.phonestoremanager.models.BrandModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

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

    public int insert(String brandName) {
        String sql = "INSERT INTO [dbo].[Brand]\n" +
                "           ([BrandID]\n" +
                "           ,[Name])\n" +
                "     VALUES\n" +
                "           (?, ?);";

        int row = 0;
        ArrayList<BrandModel> list = BrandRepository.getInstance().selectAll();

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, list.size()+1);
            st.setString(2, brandName);
            row = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return row;
    }

    public boolean checkBrandNameExist(String brandName) {
        String sql = " SELECT Name\n" +
                "  FROM Brand\n" +
                "  WHERE Name = ?;";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, brandName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if(rs.getString("Name").equals(brandName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
