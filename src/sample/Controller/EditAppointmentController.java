package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.AppointmentDAO;
import sample.Model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditAppointmentController implements Initializable {
    public TextField editApptIDField;
    public TextField editApptTitleField;
    public TextField editApptDescField;
    public TextField editApptLocationField;
    public ComboBox editTypeCombo;
    public ComboBox editStartTime;
    public ComboBox editEndTime;
    public DatePicker editStartDate;
    public DatePicker editEndDate;
    public TextField editCustomerIDField;
    public TextField editUserIDField;
    public ComboBox editContactCombo;
    public ArrayList<String> timeOptions = new ArrayList<String>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment appointment = AllAppointmentsController.selectedAppointment;
        String startDateTime[] = appointment.getStartDate().split(" ");
        String endDateTime[] = appointment.getEndDate().split(" ");

        timeOptions.add("00:00");
        timeOptions.add("01:00");
        timeOptions.add("02:00");
        timeOptions.add("03:00");
        timeOptions.add("04:00");
        timeOptions.add("05:00");
        timeOptions.add("06:00");
        timeOptions.add("07:00");
        timeOptions.add("08:00");
        timeOptions.add("09:00");
        timeOptions.add("10:00");
        timeOptions.add("11:00");
        timeOptions.add("12:00");
        timeOptions.add("13:00");
        timeOptions.add("14:00");
        timeOptions.add("15:00");
        timeOptions.add("16:00");
        timeOptions.add("17:00");
        timeOptions.add("18:00");
        timeOptions.add("19:00");
        timeOptions.add("20:00");
        timeOptions.add("21:00");
        timeOptions.add("22:00");
        timeOptions.add("23:00");
        timeOptions.add("24:00");

        editStartTime.getItems().addAll(timeOptions);
        editEndTime.getItems().addAll(timeOptions);

        editTypeCombo.getItems().addAll(
                "Planning Session",
                "De-Briefing",
                "Business Meeting",
                "Review",
                "Other"
        );

        editContactCombo.getItems().addAll(
                "Anika Costa",
                "Daniel Garcia",
                "Li Lee"
        );

        editApptIDField.setText(String.valueOf(appointment.getAppointmentID()));
        editApptTitleField.setText(appointment.getTitle());
        editApptDescField.setText(appointment.getDescription());
        editApptLocationField.setText(appointment.getLocation());
        editTypeCombo.setValue(appointment.getType());
        editStartDate.setValue(LocalDate.parse(startDateTime[0]));
        editStartTime.setValue(startDateTime[1].substring(0,5));
        editEndDate.setValue(LocalDate.parse(endDateTime[0]));
        editEndTime.setValue(endDateTime[1].substring(0,5));
        editCustomerIDField.setText(String.valueOf(appointment.getCustomerID()));
        editUserIDField.setText(String.valueOf(appointment.getUserID()));
        editContactCombo.setValue(appointment.getContactID());
    }

    public void toAllAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AllAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 800);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public String convertToUTC(String dateTime) throws ParseException {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
        ZonedDateTime systemZoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
        String utcTimeStamp = systemZoneDateTime.toInstant().toString().replace("T", " ");
        String finalUTCDateTime = utcTimeStamp.replace("Z", "");
        return finalUTCDateTime;
    }

    public void updateAppointment(ActionEvent actionEvent) throws ParseException, SQLException, IOException {
        int contactNum = 0;
        String contactName = editContactCombo.getValue().toString();
        if(contactName == "Anika Costa"){
            contactNum = 1;
        }
        else if(contactName == "Daniel Garcia"){
            contactNum = 2;
        }
        else if(contactName == "Li Lee"){
            contactNum = 3;
        }
        String startDateChoice = editStartDate.getValue().toString();
        String startTimeChoice = editStartTime.getValue().toString();
        String endDateChoice = editEndDate.getValue().toString();
        String endTimeChoice = editEndTime.getValue().toString();

        String localStartDateTime = startDateChoice + " " + startTimeChoice + ":00";
        String localEndDateTime =  endDateChoice + " " + endTimeChoice + ":00";

        String utcStart = convertToUTC(localStartDateTime);
        String utcEnd = convertToUTC(localEndDateTime);

        int apptID = Integer.parseInt(editApptIDField.getText());
        String apptTitle = editApptTitleField.getText().toString();
        String apptDesc = editApptDescField.getText().toString();
        String apptLoc = editApptLocationField.getText().toString();
        String apptType = editTypeCombo.getValue().toString();
        int apptCustID = Integer.parseInt(editCustomerIDField.getText());
        int apptUserID = Integer.parseInt(editUserIDField.getText());

        AppointmentDAO.updateAppointment(apptID, apptTitle, apptDesc, apptLoc,apptType, utcStart, utcEnd, apptCustID, apptUserID, contactNum);

        Parent root = FXMLLoader.load(getClass().getResource("../View/AllAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 800);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();

    }

}
