package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.CountryDAO;
import sample.DAO.CustomerDAO;
import sample.DAO.FirstLevelDivisionDAO;
import sample.Model.Customer;
import sample.Model.CustomerReceiver;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {
    public TextField editCustomerPhone;
    public TextField editCustomerPostal;
    public TextField editCustomerAddress;
    public TextField editCustomerName;
    public TextField editCustomerID;
    public ComboBox editCustomerCountry;
    public ComboBox editCustomerDivision;
    public String country;
    int country_id = 0;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet countriesSet = CountryDAO.selectAll();
            while(countriesSet.next()){
                editCustomerCountry.getItems().add(countriesSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CustomerReceiver customer = AllCustomerController.selectedCustomer;

        try {
           ResultSet divisionSet = FirstLevelDivisionDAO.getFirstLevelByName(customer.getDivision().toString());
            if(divisionSet.next()){
                if(divisionSet.getInt(7) == 1){
                    editCustomerCountry.setValue("U.S");
                    country_id = 1;
                }
                else if(divisionSet.getInt(7) == 2){
                    editCustomerCountry.setValue("UK");
                    country_id = 2;
                }
                else if (divisionSet.getInt(7) == 3){
                    editCustomerCountry.setValue("Canada");
                    country_id =3;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet divisionsSet = null;
        try {
            divisionsSet = FirstLevelDivisionDAO.getFirstLevelByCountry(country_id);
            while(divisionsSet.next()){
                editCustomerDivision.getItems().add(divisionsSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        editCustomerID.setText(String.valueOf(customer.getCustomerReceiverID()));
        editCustomerName.setText(customer.getName());
        editCustomerAddress.setText(customer.getAddress());
        editCustomerPostal.setText(customer.getPostal());
        editCustomerPhone.setText(customer.getPhone());
        editCustomerDivision.setValue(customer.getDivision());
    }

    public void toAllCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AllCustomers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void countrySelection(ActionEvent actionEvent) throws SQLException {
        editCustomerDivision.getItems().clear();
        editCustomerDivision.setValue("");
        Object countrySelected = editCustomerCountry.getValue();
        if(countrySelected.toString().equals("U.S")){
            country_id = 1;
        }
        else if (countrySelected.toString().equals("UK")){
            country_id = 2;
        }
        else if (countrySelected.toString().equals("Canada")){
            country_id = 3;
        }
        ResultSet divisionsSet = FirstLevelDivisionDAO.getFirstLevelByCountry(country_id);
        while(divisionsSet.next()){
            editCustomerDivision.getItems().add(divisionsSet.getString(2));
        }
    }

    public void updateCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        ResultSet firstLevelSet = FirstLevelDivisionDAO.getFirstLevelByName(editCustomerDivision.getValue().toString());
        if(firstLevelSet.next()){
            CustomerDAO.updateCustomer(Integer.parseInt(editCustomerID.getText()), editCustomerName.getText().toString(), editCustomerAddress.getText().toString(), editCustomerPostal.getText().toString(), editCustomerPhone.getText().toString(), firstLevelSet.getInt(1));

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer has been updated");
            Optional<ButtonType> result = alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("../View/AllCustomers.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        }

    }
}
