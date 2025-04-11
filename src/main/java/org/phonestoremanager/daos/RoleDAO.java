package org.phonestoremanager.daos;

import org.phonestoremanager.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
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
}
