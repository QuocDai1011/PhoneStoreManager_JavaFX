package org.phonestoremanager.repositories;

import org.phonestoremanager.models.CustomerModel;
import org.phonestoremanager.models.OrdersModel;
import org.phonestoremanager.services.OrdersService;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepository {
    public static List<OrdersModel> getAll() {
        List<OrdersModel> list = new ArrayList<>();
        String sql = " SELECT TOP (1000) \n" +
                "    [OrderID],\n" +
                "    [CustomerID],\n" +
                "    CONVERT(varchar, [OrderDate], 105) AS [OrderDate],  -- định dạng dd-mm-yyyy\n" +
                "    [TotalAmount],\n" +
                "    [Status],\n" +
                "    [ShippingAddress],\n" +
                "    [Note]\n" +
                "FROM [PHONE_STORE_MANAGER].[dbo].[Orders]";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrdersModel ordersModel = OrdersService.ordersModel(rs);
                list.add(ordersModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static int insert(CustomerModel customerModel, String status, double totalAmout) {
        int row = 0;
        String note = "Fragile items please handle with care";
        String sql = "INSERT INTO [dbo].[Orders]\n" +
                "           ([CustomerID]\n" +
                "           ,[TotalAmount]\n" +
                "           ,[Status]\n" +
                "           ,[ShippingAddress]\n" +
                "           ,[Note])\n" +
                "     VALUES\n" +
                "           (?, ?, ?, ?, ?, ?);";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, customerModel.getCustomerID());
            st.setDouble(2, totalAmout);
            if(status.equals("Chưa thanh toán")) {
                st.setInt(4, 0);
            }else {
                st.setInt(4, 1);
            }
            st.setString(5, customerModel.getAddress());
            st.setString(6, note);

            row = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public static int getOrderIDByInfomation(int customerID, Double total) {
        int id = 0;
        String sql = "  SELECT TOP 1 [OrderID]\n" +
                "  FROM [PHONE_STORE_MANAGER].[dbo].[Orders]\n" +
                "  WHERE CustomerID = ? AND TotalAmount = ?;";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, customerID);
            st.setDouble(2, total);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                id = rs.getInt("OrderID");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
