package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.CustomerDAO;
import sample.DAO.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {
    public Button logIn;
    public TextField userName;
    public TextField passWord;
    public Label invalidMessage;
    public Label locationLabel;
    public String language;
    public String locationText = "Location: ";
    public Label loginTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Locale.getDefault().getLanguage().contentEquals("fr")){
            language = "fr";
            locationText = "Emplacement: ";
            loginTitle.setText("Planificateur de rendez-vous Suwyn");
            userName.setPromptText("Nom d'utilisateur");
            passWord.setPromptText("le mot de passe");
            logIn.setText("Connexion");
        }

        ZoneId zoneID = ZoneId.systemDefault();
        locationLabel.setText(locationText + zoneID.toString());

    }


    public void logIn(ActionEvent actionEvent) throws SQLException, IOException {
        String name = userName.getText().toString();
        String pass = passWord.getText().toString();
        if(UserDAO.login(name, pass)){
            Parent root = FXMLLoader.load(getClass().getResource("../View/CustomersOrAppointments.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 600);
            stage.setTitle("Customers or Appointments");
            stage.setScene(scene);
            stage.show();
        }
        else {
            if(language =="fr"){
                invalidMessage.setText("Les informations d'identification invalides");
            }
            else {
                invalidMessage.setText("INVALID CREDENTIALS");
            }
        }
    }


}
