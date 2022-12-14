package sample.Controller;

import com.mysql.cj.xdevapi.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//Class that contains the code for report option page
public class ReportsController implements Initializable {

    public ComboBox reportCombo;
    public AnchorPane reportAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        reportCombo.getItems().addAll(
                "Customer appointment type by Month",
                "Schedule for each contact",
                "Appointment Count"
        );

    }

    //Navigation to selection page
    public void toApptsOrCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomersOrAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Customers or Appointments");
        stage.setScene(scene);
        stage.show();
    }


    //Function to handle report selection
    public void reportSelection(ActionEvent actionEvent) throws IOException {
        Object reportSelected = reportCombo.getValue();
        if(reportSelected.toString().equals("Customer appointment type by Month")){
            Parent root = FXMLLoader.load(getClass().getResource("../View/MonthTypeReport.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 500);
            stage.setTitle("Appointments by Type and Month");
            stage.setScene(scene);
            stage.show();
        }
        else if(reportSelected.toString().equals("Schedule for each contact")){
            Parent root = FXMLLoader.load(getClass().getResource("../View/ContactReport.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 500);
            stage.setTitle("Contact Report");
            stage.setScene(scene);
            stage.show();

        }
        else if(reportSelected.toString().equals("Appointment Count")){
            Parent root = FXMLLoader.load(getClass().getResource("../View/MyReport.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 500);
            stage.setTitle("Appointment Count");
            stage.setScene(scene);
            stage.show();
        }
    }
}

