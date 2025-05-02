package org.phonestoremanager.repositories;

import org.phonestoremanager.models.EmployeeModel;
import org.phonestoremanager.services.EmployeeService;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

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
            st.setInt(1, AccountRepository.getAccountIdByUserName(userName));
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

    public static List<EmployeeModel> getAll() {
        List<EmployeeModel> list = new ArrayList<>();
        String sql = "SELECT * FROM EmployeeProfile;";

        try(Connection con = DatabaseConnection.createConnection()) {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                EmployeeModel employeeModel = EmployeeService.createByResultSet(rs);
                list.add(employeeModel);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
        return list;
    }


}
