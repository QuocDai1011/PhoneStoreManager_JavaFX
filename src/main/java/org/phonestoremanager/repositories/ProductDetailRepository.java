package org.phonestoremanager.repositories;

import org.phonestoremanager.models.ProductDetailModel;
import org.phonestoremanager.models.OrderUpdateModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailRepository {
    public static ProductDetailRepository getInstance() {
        return new ProductDetailRepository();
    }

    public int getProductDetailIDByProductIDAndColorIDAndRom(int productID, int colorID, int rom) {
        String sql = "SELECT ProductDetailID FROM ProductDetail WHERE ProductID = ? AND ColorID = ? AND Rom = ?";

        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, colorID);
            preparedStatement.setInt(3, rom);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("ProductDetailID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Không tìm thấy
    }



    public ProductDetailModel getProductDetailModelSelected(ProductDetailModel pd) {
        if (pd == null || pd.getProductDetailID() == 0) {
            return null;
        }
        String sql = "SELECT * FROM ProductDetail WHERE ProductDetailID = ?";
        try (Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pd.getProductDetailID());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                ProductDetailModel productDetail = new ProductDetailModel();
                productDetail.setProductDetailID(rs.getInt("ProductDetailID"));
                productDetail.setProductID(rs.getInt("ProductID"));
                productDetail.setRom(rs.getInt("ROM"));
                productDetail.setColorID(rs.getInt("ColorID"));
                // thêm các field khác nếu cần
                return productDetail;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteProductDetailByProductDetailID(int productDetailID) {
        String sql = "DELETE FROM ProductDetail WHERE ProductDetailID = ?";
        try (Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productDetailID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductDetailByProductID(ProductDetailModel pd) {
        String sql = "UPDATE PD\n" +
                "SET\n" +
                "      [Ram] = ?,\n" +
                "      [Rom] = ?,\n" +
                "      [Chip] = ?,\n" +
                "      [ScreenSize] = ?,\n" +
                "      [ScreenParameters] = ?,\n" +
                "      [BatteryCapacity] = ?,\n" +
                "      [Image] = ?,\n" +
                "      [Description] = ?,\n" +
                "      [Price] = ?,\n" +
                "      [StockQuantity] = ?,\n" +
                "      [CameraFront] = ?,\n" +
                "      [CameraRear] = ?,\n" +
                "      [ScreenTechnology] = ?,\n" +
                "      [ScanFrequency] = ?,\n" +
                "      [ColorID] = ?\n" +
                "FROM ProductDetail PD\n" +
                "WHERE ProductDetailID = ?;";

        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, pd.getRam());
            preparedStatement.setInt(2, pd.getRom());
            preparedStatement.setNString(3, pd.getChip());
            preparedStatement.setFloat(4, pd.getScreenSize());
            preparedStatement.setNString(5, pd.getScreenParameters());
            preparedStatement.setFloat(6, pd.getBatteryCapacity());
            preparedStatement.setNString(7, pd.getImage());
            preparedStatement.setNString(8, pd.getDescription());
            preparedStatement.setDouble(9, pd.getPrice());
            preparedStatement.setInt(10, pd.getStockQuantity());
            preparedStatement.setNString(11, pd.getCameraFront());
            preparedStatement.setNString(12, pd.getCameraRear());
            preparedStatement.setNString(13, pd.getScreenTechnology());
            preparedStatement.setNString(14, pd.getScanFrequency());
            preparedStatement.setInt(15, pd.getColorID());
            preparedStatement.setInt(16, pd.getProductDetailID());  // WHERE ProductID = ?

            System.out.println("Đang cập nhật ProductDetailID = " + pd.getProductDetailID());
            System.out.println("Đường dẫn ảnh mới = " + pd.getImage());
            System.out.println("Giá mới = " + pd.getPrice());
            System.out.println("ColorID mới = " + pd.getColorID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật ProductDetail: " + e.getMessage(), e);
        }
    }


    public void insertProductDetailByProductDetailModel(ProductDetailModel detail) {
        String sql = "INSERT INTO ProductDetail ([ProductID], [Ram], [Rom], [Chip], [ScreenSize], " +
                "[ScreenParameters], [BatteryCapacity], [Image], [Description], [Price], [StockQuantity], [CameraFront], " +
                "[CameraRear], [ScreenTechnology], [ScanFrequency], [ColorID]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, detail.getProductID());
            preparedStatement.setInt(2, detail.getRam());
            preparedStatement.setInt(3, detail.getRom());
            preparedStatement.setString(4, detail.getChip());
            preparedStatement.setFloat(5, detail.getScreenSize());
            preparedStatement.setString(6, detail.getScreenParameters());
            preparedStatement.setFloat(7, detail.getBatteryCapacity());
            preparedStatement.setString(8, detail.getImage());
            preparedStatement.setString(9, detail.getDescription());
            preparedStatement.setDouble(10, detail.getPrice());
            preparedStatement.setInt(11, detail.getStockQuantity());
            preparedStatement.setString(12, detail.getCameraFront());
            preparedStatement.setString(13, detail.getCameraRear());
            preparedStatement.setString(14, detail.getScreenTechnology());
            preparedStatement.setNString(15, detail.getScanFrequency());
            preparedStatement.setInt(16, detail.getColorID());

            preparedStatement.executeUpdate();
            System.out.println("Đã chèn ProductDetail thành công!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(detail.getColorID());
            throw new RuntimeException("Lỗi không chèn được ProductDetail", e);
        }
    }


    public ProductDetailModel getDetailByProductIDAndROM(int productID, int ROM) {
        ProductDetailModel spec = null;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "SELECT * FROM Product p\n" +
                "JOIN Brand b ON b.BrandID = p.BrandID\n" +
                "JOIN ProductDetail pd ON pd.ProductID = p.ProductID\n" +
                "WHERE p.ProductID = ? AND pd.ROM = ?";

        try (Connection conn = databaseConnection.connectionData();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, ROM);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                spec = new ProductDetailModel();
                spec.setProductDetailID(resultSet.getInt("ProductDetailID"));
                spec.setBrandID(resultSet.getInt("BrandID"));
                spec.setChip(resultSet.getString("Chip"));
                spec.setRam(Integer.parseInt(resultSet.getString("Ram")));
                spec.setRom(Integer.parseInt(resultSet.getString("Rom")));
                spec.setScreenSize(Float.parseFloat(resultSet.getString("ScreenSize")));
                spec.setScanFrequency(resultSet.getString("ScanFrequency"));
                spec.setCameraFront(resultSet.getString("CameraFront"));
                spec.setCameraRear(resultSet.getString("CameraRear"));
                spec.setBatteryCapacity(Float.parseFloat(resultSet.getString("BatteryCapacity")));
                spec.setPrice(resultSet.getDouble("Price"));
                spec.setColorID(resultSet.getInt("ColorID"));
                spec.setStockQuantity(resultSet.getInt("StockQuantity"));
                spec.setDescription(resultSet.getString("Description"));
                spec.setScreenParameters(resultSet.getString("ScreenParameters"));
                spec.setScreenTechnology(resultSet.getString("ScreenTechnology"));
                System.out.println("Price in resultSet: " + resultSet.getDouble("Price"));
                System.out.println("CameraRear in resultSet: " + resultSet.getString("CameraRear"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return spec;
    }

    public boolean isEmptyProductDetail(int productID) {
        String sql = "SELECT COUNT(*) FROM ProductDetail WHERE ProductID = ?";
        try(Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // Trả về true nếu không có bản ghi
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true; // Trông trươờng hợp lỗi, giả định là rỗng
    }

    public void deleteProduct(int productID) {
        String sql = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO
    public int getProductDetailIDByInfomation(OrderUpdateModel orderUpdateModel) {
        int id = -1;
        String sql = "SELECT pd.ProductDetailID\n" +
                "FROM Product p\n" +
                "JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n" +
                "JOIN ColorOfProduct c ON pd.ColorID = c.ColorOfProductID\n" +
                "WHERE p.Name = ? AND pd.Ram = ? AND pd.Rom = ? AND c.NameColor = ?;";

        try(Connection con = DatabaseConnection.createConnection()) {
            assert con != null;
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, orderUpdateModel.getProductName());
            st.setInt(2, orderUpdateModel.getRam());
            st.setInt(3, orderUpdateModel.getRom());
            st.setString(4, orderUpdateModel.getColor());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ProductDetailID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
