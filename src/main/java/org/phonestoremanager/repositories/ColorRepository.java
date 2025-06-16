package org.phonestoremanager.repositories;

import org.phonestoremanager.models.ColorModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColorRepository implements IRepository<ColorModel> {
    public static ColorRepository getInstance() {
        return new ColorRepository();
    }

    public String getColorNameByColorID(int colorID) {
        String sql = "SELECT NameColor FROM ColorOfProduct WHERE ColorOfProductID = ?";
        try (Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, colorID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getNString("NameColor");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//    public String getColorNameByColorIDAndProductDetailID(int productDetailID, int colorID) {
//        String sql = ""
//    }

// Hàm chuẩn hóa dữ liệu trước khi truyền vào database
    public String normalizeColorName(String input) {
        if(input.isEmpty()) return "";

        input = input.trim().replaceAll("\\s", " ");

        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for(String word : words) {
            if(!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
            }
        }

        return result.toString().trim();
    }

    public boolean isColorExists(String colorName) {
        colorName = normalizeColorName(colorName);

        List<String> allColors = getNameColorFromDB()
                .stream()
                .map(this::normalizeColorName) // chuẩn hóa dữ liệu từ DB luôn
                .toList();

        String finalColorName = colorName;
        return allColors.stream().anyMatch(name -> name.equalsIgnoreCase(finalColorName));
    }


    public void addColor(String nameColor) {
        nameColor = normalizeColorName(nameColor);
        String sql = "INSERT INTO ColorOfProduct(NameColor)\n" +
                "VALUES (?)";
        try(Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setNString(1, nameColor);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getNameColorFromDB() {
        List<String> colorNames = new ArrayList<>();
        String sql = "SELECT NameColor FROM ColorOfProduct";

        try(Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                colorNames.add(resultSet.getNString("NameColor"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return colorNames;
    }

    @Override
    public ArrayList<ColorModel> selectAll() {
        ArrayList<ColorModel> colorModels = new ArrayList<>();
        String sql = "SELECT * FROM ColorOfProduct";

        try (Connection connection = DatabaseConnection.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("ColorOfProductID");
                String name = resultSet.getNString("NameColor");

                colorModels.add(new ColorModel(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return colorModels;
    }

    @Override
    public ArrayList<ColorModel> selectDetailById() {
        return null;
    }
}
