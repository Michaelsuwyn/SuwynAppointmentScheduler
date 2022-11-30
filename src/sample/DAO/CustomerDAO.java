package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Customer;

import javax.xml.transform.Result;
import java.net.IDN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static void selectAll() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String userName = rs.getString("Customer_Name");
            System.out.println(userName);
        }
    }
    //Test to see if it is working
    public static ResultSet selectAllCustomersWithFirstDivision() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}


