package sample.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * USER data access object
 */
public class UserDAO {

    /**
     * selects all users
     * @throws SQLException
     */
    public static void selectAll() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String userName = rs.getString("User_Name");
            System.out.println(userName);
        }
    }

    /**
     * selects user where name and pass match
     * @param name
     * @param pass
     * @return
     * @throws SQLException
     */
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

    /**
     * selects user where name and pass match
     * @param name
     * @param pass
     * @return
     * @throws SQLException
     */
    public static ResultSet loginData(String name, String pass) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
