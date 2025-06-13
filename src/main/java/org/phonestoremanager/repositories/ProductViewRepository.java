package org.phonestoremanager.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.phonestoremanager.models.ProductViewModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductViewRepository implements IRepository<ProductViewModel> {
    public static ProductViewRepository getInstance() {
        return new ProductViewRepository();
    }

    public ArrayList<ProductViewModel> selectByBrand(String brand) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ProductViewModel> productViewModels = new ArrayList<>();
        Set<Integer> productIDs = new HashSet<>();  // Set to keep track of ProductIDs that have already been added

        String sql = "SELECT * FROM Product p\n" +
                "JOIN Brand b ON b.BrandID = p.BrandID\n" +
                "JOIN ProductDetail pd ON pd.ProductID = p.ProductID\n" +
                "WHERE b.Name = ?"; // Lọc theo nhãn hàng

        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");

                // Nếu sản phẩm đã có trong danh sách, bỏ qua
                if (productIDs.contains(productID)) {
                    continue;
                }

                productIDs.add(productID);

                String image = resultSet.getString("image");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                ProductViewModel productViewModel = new ProductViewModel(productID, image, name, price);
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
        Set<Integer> productIDs = new HashSet<>();  // Set to keep track of ProductIDs that have already been added

        try (Connection conn = databaseConnection.connectionData()) {
            Statement statement = conn.createStatement();

            // SQL query with LIKE for partial matching of product names
            String sql = "SELECT * FROM Product p " +
                    "JOIN ProductDetail pd ON p.ProductID = pd.ProductID " +
                    "WHERE p.name LIKE '%" + keyword + "%';";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
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

                ProductViewModel productViewModel = new ProductViewModel(productID, image, name, price);

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

                ProductViewModel productViewModel = new ProductViewModel(productID, image, name, price);


                productViewModels.add(productViewModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productViewModels;
    }

    public static List<String> getAllNameProducts() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT [Name]\n" +
                "  FROM [PHONE_STORE_MANAGER].[dbo].[Product]";

        try(Connection conn = DatabaseConnection.createConnection()) {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                list.add(rs.getString("Name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<Integer> getRamByProductName(String name) {
        String sql = "SELECT DISTINCT pd.Ram\n" +
                "FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n" +
                "WHERE p.Name = ?;";

        List<Integer> list = new ArrayList<>();

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("Ram"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(list);
    }

    public static ObservableList<Integer> getRomByProductName(String name) {
        String sql = "SELECT DISTINCT pd.Rom\n" +
                "FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n" +
                "WHERE p.Name = ?;";

        List<Integer> list = new ArrayList<>();

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                list.add(rs.getInt("Rom"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(list);
    }

    public static ObservableList<String> getColorByProductName(String name) {
        String sql = "SELECT DISTINCT c.NameColor\n" +
                "FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n" +
                "JOIN ColorOfProduct c ON pd.ColorID = c.ColorOfProductID\n" +
                "WHERE p.Name = ?;";

        List<String> list = new ArrayList<>();

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                list.add(rs.getString("NameColor"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(list);
    }

    public static double getUnitPriceByProductName(String name, int ram, int rom) {
        String sql = "SELECT DISTINCT pd.Price\n" +
                "FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n" +
                "WHERE p.Name = ? AND pd.Ram = ? AND pd.Rom = ?;";
        double unitPrice = 0;

        try(Connection con = DatabaseConnection.createConnection()) {
            assert con != null;
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, ram);
            st.setInt(3, rom);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                unitPrice = rs.getDouble("Price");
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unitPrice;
    }

    @Override
    public ArrayList<ProductViewModel> selectDetailById() {
        return null;
    }

}
