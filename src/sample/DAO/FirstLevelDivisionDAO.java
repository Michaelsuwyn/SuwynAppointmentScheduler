package sample.DAO;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * First level division data access object
 */
public class FirstLevelDivisionDAO {
    /**
     * gets the first level divisions by country ID
     * @param country_id
     * @return
     * @throws SQLException
     */
    public static ResultSet getFirstLevelByCountry(int country_id) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, country_id);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * gets the first level divisions by name
     * @param name
     * @return
     * @throws SQLException
     */
    public static ResultSet getFirstLevelByName(String name) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}


