package org.phonestoremanager.repositories;

//import org.graalvm.collections.EconomicMap;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.utils.DatabaseConnection;
import org.phonestoremanager.utils.DateUtil;
import org.phonestoremanager.utils.PasswordEncrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountRepository {
    public static AccountModel getAccountByUserNameAndPassword(String userName, String passWord) {
        String sql = "SELECT * FROM Account\n" +
                "WHERE Username = ?";

        try (Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("Password");
                String decryptedPassword = PasswordEncrypt.decryptAES(encryptedPassword);

                if (passWord.equals(decryptedPassword)) {
                    AccountModel account = new AccountModel();
                    account.setAccountID(rs.getInt("AccountID"));
                    account.setUserName(rs.getString("UserName"));
                    account.setPassword(rs.getString("Password")); // vẫn lưu dạng mã hóa
                    account.setRoleID(rs.getInt("RoleID"));
                    return account;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

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

            st.setString(3, PasswordEncrypt.encryptAES(accountModel.getPassword()));

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

    public static int update(AccountModel accountModel) {
        String sql = "UPDATE [dbo].[Account]\n" +
                "   SET [RoleID] = ?\n" +
                "      ,[Password] = ?\n" +
                "      ,[UpdateAt] = ?\n" +
                " WHERE AccountID = ?;";

        int accountID = AccountRepository.getAccountIdByUserName(accountModel.getUserName());

        try (Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, accountModel.getRoleID());
            st.setString(2, PasswordEncrypt.encryptAES(accountModel.getPassword()));
            st.setString(3, DateUtil.getCurrentDate());
            st.setInt(4, accountID);
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int delete(String username) {
        String sql = "DELETE FROM [dbo].[Account]\n" +
                "      WHERE AccountID = ?;";

        int accountID = AccountRepository.getAccountIdByUserName(username);

        try (Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, accountID);
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
