package org.phonestoremanager.repositories;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepository {
    public static int getRoleIDByRoleName(String name) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        int result = 0;

        String sql = "select RoleID " +
                "from [dbo].[Role]\n" +
                "where [RoleName] = ?;";

        try(Connection connection = databaseConnection.connectionData();) {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                result = rs.getInt("RoleID");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getRoleNameByRoleID(int roleID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String roleName = "";
        String sql = "SELECT r.RoleName\n" +
                "FROM Account as ac\n" +
                "JOIN Role as r ON ac.RoleID = r.RoleID\n" +
                "WHERE ac.RoleID = ?;";

        try (Connection conn = databaseConnection.connectionData()) {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, roleID);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                roleName = rs.getString("RoleName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleName;
    }
}
