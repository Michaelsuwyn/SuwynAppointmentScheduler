package sample.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Appointment Data Access Object
 */
public class AppointmentDAO {

    /**
     * Selects all from appointments table
     * @return
     * @throws SQLException
     */
    public static ResultSet selectAll() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Insert record into appointment table
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDate
     * @param endDate
     * @param customerID
     * @param userID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static int insertAppointment(String title, String description, String location, String type, String startDate, String endDate, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, startDate);
        ps.setString(6, endDate);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**
     * Selects all appointments by customer ID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectByCustomerID(int customerID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects all appointments by User ID
     * @param userID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectByUserID(int userID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE User_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * selects all appointments by contact ID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectByContactID(int contactID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }


    /**
     * deletes appointment from the appointment table
     * @param apptID
     * @return
     * @throws SQLException
     */
    public static int deleteByID(int apptID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, apptID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * updates an appointment
     * @param apptID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDate
     * @param endDate
     * @param customerID
     * @param userID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static int updateAppointment(int apptID, String title, String description, String location, String type, String startDate, String endDate, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, startDate);
        ps.setString(6, endDate);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, apptID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**
     * custom join query for my report
     * @return
     * @throws SQLException
     */
    public static ResultSet queryForMyReport() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, COUNT(*) FROM appointments GROUP BY Location;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}

