package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.DAO.AppointmentDAO;
import sample.DAO.CustomerDAO;
import sample.Model.Customer;
import sample.Model.CustomerReceiver;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class which contains the code for all customer page
 */
public class AllCustomerController implements Initializable {
    public TableView allCustomers;
    public TableColumn customerIDField;
    public TableColumn customerNameField;
    public TableColumn customerAddressField;
    public TableColumn customerPostalField;
    public TableColumn customerDivisionField;
    public TableColumn customerPhoneField;
    public static CustomerReceiver selectedCustomer;
    public ObservableList<CustomerReceiver> allCustomerList = FXCollections.observableArrayList();
    public ObservableList<CustomerReceiver> searchedCustomerList = FXCollections.observableArrayList();
    public TextField searchField;
    public static Stage emailStage = new Stage();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet rs = CustomerDAO.selectAllCustomersWithFirstDivision();

            while(rs.next()){
                int ID = rs.getInt(1);
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

    /**
     * navigation to add customer page
     * @param actionEvent
     * @throws IOException
     */
    public void toAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * function to edit customer on selection
     * @param actionEvent
     * @throws IOException
     */
    public void editCustomer(ActionEvent actionEvent) throws IOException {
        if(allCustomers.getSelectionModel().getSelectedItem() != null){
            selectedCustomer = (CustomerReceiver) allCustomers.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("../View/EditCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 600);
            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select a customer to edit");
            Optional<ButtonType> result = noSelection.showAndWait();
        }
    }

    /**
     * function to delete customer on selection
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        if(allCustomers.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                selectedCustomer = (CustomerReceiver) allCustomers.getSelectionModel().getSelectedItem();
                ResultSet customerAppointments = AppointmentDAO.selectByCustomerID(selectedCustomer.getCustomerReceiverID());
                if(customerAppointments.next()){
                    Alert apptError = new Alert(Alert.AlertType.ERROR, "Customer still has scheduled appointments");
                    Optional<ButtonType> apptErrorResult = apptError.showAndWait();
                }
                else {
                    CustomerDAO.deleteByID(selectedCustomer.getCustomerReceiverID());

                    Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION, "Customer has been deleted");
                    Optional<ButtonType> deleteResult = deleteAlert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("../View/AllCustomers.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 800, 600);
                    stage.setTitle("Customers");
                    stage.setScene(scene);
                    stage.show();
                }

            }
        }
        else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "No Customer has been selected");
            Optional<ButtonType> result = noSelection.showAndWait();
        }
    }

    /**
     * navigation to choice page
     * @param actionEvent
     * @throws IOException
     */
    public void toAppOrCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomersOrAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Customers or Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void searchName(KeyEvent keyEvent) {
        searchedCustomerList.clear();
        String searchString = searchField.getText().toLowerCase();
        for(int i = 0; i < allCustomerList.size(); i++){
            String currentCustomer = allCustomerList.get(i).getName().toLowerCase();
            if(currentCustomer.contains(searchString)){
                searchedCustomerList.add(allCustomerList.get(i));
            }
        }
        allCustomers.setItems(searchedCustomerList);
    }

    public void emailCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/EmailCustomer.fxml"));
        Scene scene = new Scene(root, 800, 500);
        emailStage.setTitle("Email Customer");
        emailStage.setScene(scene);
        emailStage.show();

    }
}
