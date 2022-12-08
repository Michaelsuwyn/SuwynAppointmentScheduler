package sample.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country Data Access Object
 */
public class CountryDAO {

    /**
     * returns all countries
     * @return
     * @throws SQLException
     */
    public static ResultSet selectAll() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
