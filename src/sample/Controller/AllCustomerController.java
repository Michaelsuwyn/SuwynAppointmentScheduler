package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.CustomerDAO;
import sample.Model.Customer;
import sample.Model.CustomerReceiver;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllCustomerController implements Initializable {
    public TableView allCustomers;
    public TableColumn customerIDField;
    public TableColumn customerNameField;
    public TableColumn customerAddressField;
    public TableColumn customerPostalField;
    public TableColumn customerDivisionField;
    public TableColumn customerPhoneField;
    public ObservableList<CustomerReceiver> allCustomerList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet rs = CustomerDAO.selectAllCustomersWithFirstDivision();

            while(rs.next()){
                Integer ID = rs.getInt(1);
                String Name = rs.getString(2);
                String Address = rs.getString(3);
                String Postal = rs.getString(4);
                String Division = rs.getString(12);
                String Phone = rs.getString(5);
                CustomerReceiver customerReceiver = new CustomerReceiver(ID, Name, Address, Postal, Division, Phone);
                allCustomerList.add(customerReceiver);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        allCustomers.setItems(allCustomerList);
        customerIDField.setCellValueFactory(new PropertyValueFactory<>("customerReceiverID"));
        customerNameField.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressField.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalField.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerDivisionField.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerPhoneField.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void toAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
}
