package org.phonestoremanager.daos;

import org.phonestoremanager.models.CustomerModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    public static int insert(CustomerModel customerModel, String username) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "INSERT INTO [dbo].[CustomerProfile]\n" +
                "           ([AccountID]\n" +
                "           ,[FirstName]\n" +
                "           ,[LastName]\n" +
                "           ,[Email]\n" +
                "           ,[PhoneNumber]\n" +
                "           ,[Address])\n" +
                "     VALUES\n" +
                "           (?, ?, ?, ?, ?, ?);";
        int row = 0;
        try (Connection conn = databaseConnection.connectionData()){
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, AccountDAO.getAccountIdByUserName(username));
            st.setString(2, customerModel.getFirstName());
            st.setString(3, customerModel.getLastName());
            st.setString(4, customerModel.getEmail());
            st.setString(5, customerModel.getPhoneNumber());
            st.setString(6, customerModel.getAddress());
            row = st.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }
}
