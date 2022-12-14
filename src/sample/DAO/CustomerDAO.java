package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Customer;

import javax.xml.transform.Result;
import java.net.IDN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customer data access object
 */
public class CustomerDAO {

    /**
     * function to return all customers
     * @throws SQLException
     */
    public static void selectAll() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String userName = rs.getString("Customer_Name");
            System.out.println(userName);
        }
    }

    /**
     * returns customers with first level division
     * @return
     * @throws SQLException
     */
    public static ResultSet selectAllCustomersWithFirstDivision() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * creates customer record on customer table
     * @param customerName
     * @param address
     * @param postal
     * @param phone
     * @param divisionID
     * @return
     * @throws SQLException
     */
        public static int insertCustomer(String customerName, String address, String postal, String phone, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * deletes a customer record
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static int deleteByID(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * updates a customer record
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int updateCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code =?, Phone = ?, Division_ID = ? WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}


