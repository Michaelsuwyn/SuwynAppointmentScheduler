package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DAO.AppointmentDAO;
import sample.Model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class which contains code for the edit appointment controller
 */
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
    public boolean withinTimeZone = true;
    public boolean apptConflict = false;



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

    /**
     * navigation to all appointments page
     * @param actionEvent
     * @throws IOException
     */
    public void toAllAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AllAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 800);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * function to convert string date to UTC
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public String convertToUTC(String dateTime) throws ParseException {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
        ZonedDateTime systemZoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
        String utcTimeStamp = systemZoneDateTime.toInstant().toString().replace("T", " ");
        String finalUTCDateTime = utcTimeStamp.replace("Z", "");
        return finalUTCDateTime;
    }

    /**
     * function to update the appointment
     * @param actionEvent
     * @throws ParseException
     * @throws SQLException
     * @throws IOException
     */
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

        withinTimeZone(utcStart.substring(11,13), utcEnd.substring(11,13));

        if(withinTimeZone == true){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date finalStartDate = formatter.parse(utcStart);
            Date finalEndDate = formatter.parse(utcEnd);

            ResultSet otherAppts = AppointmentDAO.selectByCustomerIDExcludeApptID(apptCustID , apptID);
            while(otherAppts.next()){
                String otherStart = otherAppts.getString(6);
                String otherEnd = otherAppts.getString(7);
                Date otherStartDate = formatter.parse(otherStart);
                Date otherEndDate = formatter.parse(otherEnd);
                if((finalStartDate.before(otherEndDate) && finalStartDate.after(otherStartDate)) || (finalEndDate.before(otherEndDate) && finalEndDate.after(otherStartDate)) || (finalStartDate.equals(otherStartDate) && finalEndDate.equals(otherEndDate)) || (finalStartDate.before(otherStartDate) && finalEndDate.equals(otherEndDate)) || (finalStartDate.equals(otherStartDate) && finalEndDate.after(otherEndDate)) ){
                    apptConflict = true;
                }
                else {
                    apptConflict = false;
                }
            }

            if(apptConflict == true){
                Alert alertAppt = new Alert(Alert.AlertType.ERROR, "Customer has overlapping appointment. Appointment must be outside other customer appointment windows.");
                Optional<ButtonType> resultAppt = alertAppt.showAndWait();            }
            else {
                AppointmentDAO.updateAppointment(apptID, apptTitle, apptDesc, apptLoc,apptType, utcStart, utcEnd, apptCustID, apptUserID, contactNum);

                Parent root = FXMLLoader.load(getClass().getResource("../View/AllAppointments.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1100, 800);
                stage.setTitle("Appointments");
                stage.setScene(scene);
                stage.show();
            }

        }


    }

    /**
     * function that checks if start and end time are within range
     * @param startTimeChoice
     * @param endTimeChoice
     */
    public void withinTimeZone(String startTimeChoice, String endTimeChoice){

        int startTimeInt = Integer.parseInt(startTimeChoice.substring(0,2));
        int endTimeInt =  Integer.parseInt(endTimeChoice.substring(0,2));

        if((startTimeInt > 2 && startTimeInt < 13) || (endTimeInt > 3 && endTimeInt < 14)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Time needs to be within 8:00am - 10:00pm EST");
            Optional<ButtonType> result = alert.showAndWait();
            withinTimeZone = false;
        }
        else {
            withinTimeZone = true;
        }

    }

}
