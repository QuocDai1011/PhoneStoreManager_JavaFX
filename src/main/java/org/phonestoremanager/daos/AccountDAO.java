package org.phonestoremanager.daos;

import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.utils.DatabaseConnection;
import org.phonestoremanager.utils.PasswordEncrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {
    public static int insert(AccountModel accountModel) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        int row = 0;

        String sql = "INSERT INTO [dbo].[Account]\n" +
                "           ([RoleID]\n" +
                "           ,[Username]\n" +
                "           ,[Password])\n" +
                "     VALUES\n" +
                "           (?,?,?);";

        try (Connection connection = databaseConnection.connectionData()) {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, accountModel.getRoleID());
            st.setString(2, accountModel.getUserName());
            st.setString(3, PasswordEncrypt.toSHA256(accountModel.getPassword()));
            row = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public static int getAccountIdByUserName(String userName) {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        String sql = "SELECT AccountID \n" +
                "FROM Account\n" +
                "WHERE Username = ?;";

        int accountID = 0;
        try (Connection connection = databaseConnection.connectionData()){
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                accountID = rs.getInt("AccountID");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return accountID;
    }

    public static ArrayList<String> getUserNameAndPasswordByUserName(String userName) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT Username, Password \n" +
                "FROM Account\n" +
                "WHERE Username = ?;";

        try (Connection connection = databaseConnection.connectionData()) {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                result.add(rs.getString("Username"));
                result.add(rs.getString("Password"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getRoleIDByUsername(String userName) {
        int roleId = 0;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "SELECT RoleID\n" +
                "FROM Account\n" +
                "WHERE Username = ?;";

        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                roleId = rs.getInt("RoleID");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return roleId;
    }

    public static boolean checkUsername(String username) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sql = "SELECT Username\n" +
                "FROM Account\n" +
                "WHERE Username = ?;";
        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                if(username.equals(rs.getString("Username"))) {
                    return true;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
