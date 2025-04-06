package org.phonestoremanager.daos;

import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.models.EmployeeModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;

public class EmployeeDAO {

    public static int insert(EmployeeModel employeeModel, String userName) {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        String sql = "INSERT INTO [dbo].[EmployeeProfile]\n" +
                "           ([AccountID]\n" +
                "           ,[FirstName]\n" +
                "           ,[LastName]\n" +
                "           ,[Gender]\n" +
                "           ,[Email]\n" +
                "           ,[PhoneNumber]\n" +
                "           ,[Address]\n" +
                "           ,[Position]\n" +
                "           ,[Salary])\n" +
                "     VALUES\n" +
                "           (?,?,?,?,?,?,?,?,?);";

        int row = 0;
        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, AccountDAO.getAccountIdByUserName(userName));
            st.setString(2, employeeModel.getFirstName());
            st.setString(3, employeeModel.getLastName());
            st.setInt(4, employeeModel.getGender());
            st.setString(5, employeeModel.getEmail());
            st.setString(6, employeeModel.getPhoneNumber());
            st.setString(7, employeeModel.getAddress());
            st.setString(8, employeeModel.getPosition());
            st.setDouble(9, employeeModel.getSalary());
            row = st.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

}
