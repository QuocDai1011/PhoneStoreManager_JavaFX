package org.phonestoremanager.repositories;

import org.phonestoremanager.models.CustomerModel;
import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {
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
            st.setInt(1, AccountRepository.getAccountIdByUserName(username));
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

    public static String getNameByCustomerID(int id) {
        String name = "";
        String sql = "SELECT CONCAT(LastName, ' ', FirstName) as FullName\n" +
                "  FROM CustomerProfile\n" +
                "  WHERE CustomerID = ?;";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                name = rs.getString("FullName");
                break;
            }
        } catch (Exception e) {
            return null;
        }

        return name;
    }

    public static CustomerModel getAllByID(int id) {
        String sql = "SELECT *\n" +
                "FROM CustomerProfile\n" +
                "WHERE CustomerID = ?;";

        CustomerModel customerModel = new CustomerModel();

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                customerModel.setCustomerID(id);
                customerModel.setFirstName(rs.getString("FirstName"));
                customerModel.setLastName(rs.getString("LastName"));
                customerModel.setEmail(rs.getString("Email"));
                customerModel.setPhoneNumber(rs.getString("PhoneNumber"));
                customerModel.setAddress(rs.getString("Address"));
            }
        }catch (Exception e) {
            System.out.println();
        }
        return customerModel;
    }

    public static CustomerModel getAllByIEmail(String email) {
        String sql = "SELECT *\n" +
                "FROM CustomerProfile\n" +
                "WHERE Email = ?;";

        CustomerModel customerModel = new CustomerModel();

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                customerModel.setCustomerID(rs.getInt("CustomerID"));
                customerModel.setFirstName(rs.getString("FirstName"));
                customerModel.setLastName(rs.getString("LastName"));
                customerModel.setEmail(rs.getString("Email"));
                customerModel.setPhoneNumber(rs.getString("PhoneNumber"));
                customerModel.setAddress(rs.getString("Address"));
            }
        }catch (Exception e) {
            System.out.println();
        }
        return customerModel;
    }
}
