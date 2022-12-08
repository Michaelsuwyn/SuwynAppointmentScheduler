package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.CountryDAO;
import sample.DAO.CustomerDAO;
import sample.DAO.FirstLevelDivisionDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the add customer page
 */
public class AddCustomerController implements Initializable {
    public TextField nameField;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public ComboBox countryCombo;
    public ComboBox stateCombo;
    int country_id = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
                ResultSet countriesSet = CountryDAO.selectAll();
                while(countriesSet.next()){
                    countryCombo.getItems().add(countriesSet.getString(2));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
        }

    }

    /**
     * Provides country ID on country selection
     * @param actionEvent
     * @throws SQLException
     */
    public void countrySelection(ActionEvent actionEvent) throws SQLException {
        stateCombo.getItems().clear();
        Object countrySelected = countryCombo.getValue();
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
            stateCombo.getItems().add(divisionsSet.getString(2));
        }
    }

    /**
     * submits a new customer to database
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void submitCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        ResultSet firstLevelSet = FirstLevelDivisionDAO.getFirstLevelByName(stateCombo.getValue().toString());
        if(firstLevelSet.next()){
            CustomerDAO.insertCustomer(nameField.getText().toString(), addressField.getText().toString(), postalField.getText().toString(), phoneField.getText().toString(), firstLevelSet.getInt(1));
            Parent root = FXMLLoader.load(getClass().getResource("../View/AllCustomers.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * navigation to all customers page
     * @param actionEvent
     * @throws IOException
     */
    public void toAllCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AllCustomers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
}
