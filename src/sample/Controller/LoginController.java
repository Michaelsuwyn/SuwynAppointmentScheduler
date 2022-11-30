package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
import java.sql.SQLException;

public class LoginController {
    public Button logIn;
    public TextField userName;
    public TextField passWord;
    public Label invalidMessage;


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
            invalidMessage.setText("INVALID CREDENTIALS");
        }
    }
}
