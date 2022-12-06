package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.DAO.AppointmentDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomersOrAppointmentsController implements Initializable {
    public ZoneId zoneID = ZoneId.systemDefault();
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Date utcNowDateTime;
    public ResultSet userAppointments;
    public boolean newAppt = false;
    TimeZone timeZone = TimeZone.getDefault();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    if(LoginController.firstLogin == false){
        try {
            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime fifteenMins = LocalDateTime.now().plusMinutes(15);
            String fifteenMinsTime = dtf.format(fifteenMins);
            String nowDateTime = dtf.format(now);
            DateFormat utcFormat = new SimpleDateFormat(DATE_FORMAT);
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat localFormat = new SimpleDateFormat(DATE_FORMAT);
            localFormat.setTimeZone(TimeZone.getTimeZone(timeZone.getID()));

            Date nowFinal = sdf.parse(nowDateTime);
            Date fifteenFinal = sdf.parse(fifteenMinsTime);
            userAppointments = AppointmentDAO.selectByUserID(LoginController.loggedInUserID);
            while(userAppointments.next()){
                Date date = utcFormat.parse(userAppointments.getString(6));
                String apptDate = localFormat.format(date);
                Date apptFinal = sdf.parse(apptDate);
                if(apptFinal.before(fifteenFinal) && apptFinal.after(nowFinal)){
                    newAppt = true;
                    Alert apptAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment within 15 Minutes \n Details: \n" + "Appointment ID - "+ userAppointments.getInt(1) + "\n" + apptFinal);
                    Optional<ButtonType> apptAlertResult = apptAlert.showAndWait();
                }
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            }
        if(newAppt == false){
            Alert noAppts = new Alert(Alert.AlertType.INFORMATION, "No Appointments within 15 minutes");
            Optional<ButtonType> noApptsResult = noAppts.showAndWait();
        }

        }
    }

    public void toCustomers(ActionEvent actionEvent) throws IOException {
        LoginController.firstLogin = true;
        Parent root = FXMLLoader.load(getClass().getResource("../View/AllCustomers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }


    public void toAppointments(ActionEvent actionEvent) throws IOException {
        LoginController.firstLogin = true;
        Parent root = FXMLLoader.load(getClass().getResource("../View/AllAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 800);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public static String convertDateTime(String dt, String timezone) throws ParseException {
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfOriginal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = sdfOriginal.parse(dt);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(calendar.getTime());
    }




}
