package org.phonestoremanager.repositories;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDetailRepository {
    public static int insert(int orderID, int productDetailID, int quantity, double unitPrice) {
        int row = 0;

        String sql = "INSERT INTO [dbo].[OrderDetail]\n" +
                "           ([OrderID]\n" +
                "           ,[ProductDetailID]\n" +
                "           ,[Quantity]\n" +
                "           ,[UnitPrice])\n" +
                "     VALUES\n" +
                "           (?, ?, ? , ?);";

        try(Connection con = DatabaseConnection.createConnection()) {
            assert con != null;
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, orderID);
            st.setInt(2, productDetailID);
            st.setInt(3, quantity);
            st.setDouble(4, unitPrice);
            row = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return row;
    }
}
