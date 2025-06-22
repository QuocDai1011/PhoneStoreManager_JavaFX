package org.phonestoremanager.repositories;

import org.phonestoremanager.exeptions.PasswordValidation;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.models.EmployeeModel;
import org.phonestoremanager.services.EmployeeService;
import org.phonestoremanager.utils.DatabaseConnection;
import org.phonestoremanager.utils.DateUtil;
import org.phonestoremanager.utils.PasswordEncrypt;

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

    public static List<String> getEmployeeIDByInformation(String email, String phoneNumber, String gender, double salary) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT a.Username, a.Password\n" +
                "  FROM EmployeeProfile e\n" +
                "  JOIN Account a ON a.AccountID = e.AccountID\n" +
                "  WHERE e.EmployeeID = (SELECT EmployeeID\n" +
                "\t\t\t\t\t\tFROM EmployeeProfile\n" +
                "\t\t\t\t\t\tWHERE Email = ? AND \n" +
                "\t\t\t\t\t\tPhoneNumber = ? AND Gender = ? AND Salary = ?);";

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, phoneNumber);
            if(gender.equals("Nam")) {
                st.setInt(3, 1);
            }else {
                st.setInt(3, 0);
            }
            st.setDouble(4, salary);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Username"));
                String password = PasswordEncrypt.decryptAES(rs.getString("Password"));
                list.add(password);
                break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static int update(EmployeeModel employeeModel, String username) {
        String sql = "UPDATE [dbo].[EmployeeProfile]\n" +
                "   SET [FirstName] = ?\n" +
                "      ,[LastName] = ?\n" +
                "      ,[Gender] = ?\n" +
                "      ,[Email] = ?\n" +
                "      ,[PhoneNumber] = ?\n" +
                "      ,[Address] = ?\n" +
                "      ,[Position] = ?\n" +
                "      ,[Salary] = ?\n" +
                "      ,[UpdateAt] = ?\n" +
                " WHERE AccountID = ?;";

        int accountID = AccountRepository.getAccountIdByUserName(username);

        try(Connection connection = DatabaseConnection.createConnection()) {
            assert connection != null;
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, employeeModel.getFirstName());
            st.setString(2, employeeModel.getLastName());
            if(employeeModel.getGender() == 1) {
                st.setInt(3, 1);
            }else {
                st.setInt(3, 0);
            }
            st.setString(4, employeeModel.getEmail());
            st.setString(5, employeeModel.getPhoneNumber());
            st.setString(6, employeeModel.getAddress());
            st.setString(7, employeeModel.getPosition());
            st.setDouble(8, employeeModel.getSalary());
            st.setString(9, DateUtil.getCurrentDate());
            st.setInt(10, accountID);

            return st.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int delete(String username) {
        String sql = "DELETE FROM [dbo].[EmployeeProfile]\n" +
                "      WHERE AccountID = ?;";

        int accountID = AccountRepository.getAccountIdByUserName(username);

        try(Connection conn = DatabaseConnection.createConnection()) {
            assert conn != null;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, accountID);
            return st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static EmployeeModel getEmployeeProfileByAccountID(int accountID) {
        String sql = "SELECT [EmployeeID], [AccountID], [FirstName], [LastName], [Gender], " +
                "[Email], [PhoneNumber], [Address], [Position], [Salary], " +
                "[CreateAt], [UpdateAt], [Note], [DateOfBirth], [StartDate] " +
                "FROM EmployeeProfile WHERE AccountID = ?";

        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                EmployeeModel profile = new EmployeeModel();

                profile.setEmployeeID(rs.getInt("EmployeeID"));
                profile.setAccountID(rs.getInt("AccountID"));
                profile.setFirstName(rs.getString("FirstName"));
                profile.setLastName(rs.getString("LastName"));
                profile.setGender(rs.getInt("Gender"));
                profile.setEmail(rs.getString("Email"));
                profile.setPhoneNumber(rs.getString("PhoneNumber"));
                profile.setAddress(rs.getString("Address"));
                profile.setPosition(rs.getString("Position"));
                profile.setSalary(rs.getDouble("Salary"));
                profile.setCreateAt(rs.getTimestamp("CreateAt"));
                profile.setUpdateAt(rs.getTimestamp("UpdateAt"));
                profile.setNote(rs.getString("Note"));
                profile.setDateOfBirth(rs.getDate("DateOfBirth"));
                profile.setStartDate(rs.getDate("StartDate"));

                return profile;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null; // Không tìm thấy bản ghi
    }

    public static boolean updateEmployeeProfile(int accountId, String firstName, String lastName,
                                                String email, String phone, String address, String note) {
        String sql = """
        UPDATE EmployeeProfile
        SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, 
            Address = ?, Note = ?, UpdateAt = GETDATE()
        WHERE AccountID = ?
        """;

        try (Connection conn = DatabaseConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, note);
            stmt.setInt(7, accountId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
