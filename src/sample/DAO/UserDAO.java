package sample.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static void selectAll() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String userName = rs.getString("User_Name");
            System.out.println(userName);
        }
    }

    public static boolean login(String name, String pass) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }
}
