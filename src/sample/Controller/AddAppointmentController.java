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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controller for the add appointment page
 */
public class AddAppointmentController implements Initializable {
    public TextField titleText;
    public TextField descriptionText;
    public TextField locationText;
    public TextField customerField;
    public TextField userField;
    public ComboBox typeCombo;
    public ComboBox contactCombo;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox startTimeCombo;
    public ComboBox endTimeCombo;
    public ArrayList<String> timeOptions = new ArrayList<String>();
    public boolean withinTimeZone = true;
    public boolean apptConflict = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        startTimeCombo.getItems().addAll(timeOptions);
        endTimeCombo.getItems().addAll(timeOptions);

        typeCombo.getItems().addAll(
                "Planning Session",
                "De-Briefing",
                "Business Meeting",
                "Review",
                "Other"
        );


        contactCombo.getItems().addAll(
                "Anika Costa",
                "Daniel Garcia",
                "Li Lee"
        );
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
     * converts a string date to UTC
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public String convertToUTC(String dateTime) throws ParseException{
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
        ZonedDateTime systemZoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
        String utcTimeStamp = systemZoneDateTime.toInstant().toString().replace("T", " ");
        String finalUTCDateTime = utcTimeStamp.replace("Z", "");
        return finalUTCDateTime;
    }

    /**
     * creates an appointment
     * @param actionEvent
     * @throws ParseException
     * @throws SQLException
     * @throws IOException
     */
    public void submitAppointment(ActionEvent actionEvent) throws ParseException, SQLException, IOException {
        int contactID = 0;
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();
        String location = locationText.getText().toString();
        String type = typeCombo.getValue().toString();
        String startDateChoice = startDatePicker.getValue().toString();
        String endDateChoice = endDatePicker.getValue().toString();
        String startTimeChoice = startTimeCombo.getValue().toString();
        String endTimeChoice = endTimeCombo.getValue().toString();
        int customerID = Integer.parseInt(customerField.getText());
        int userID = Integer.parseInt(userField.getText());
        String contactChoice = contactCombo.getValue().toString();
        if(contactChoice == "Anika Costa"){
            contactID = 1;
        }
        else if(contactChoice == "Daniel Garcia"){
            contactID = 2;
        }
        else if(contactChoice == "Li Lee"){
            contactID = 3;
        }

        String startDate = startDateChoice + " " + startTimeChoice + ":00";
        String endDate = endDateChoice + " " + endTimeChoice + ":00";

        String startUTC = convertToUTC(startDate);
        String endUTC = convertToUTC(endDate);

        withinTimeZone(startUTC.substring(11,13), endUTC.substring(11,13));


        if(withinTimeZone == true){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date finalStartDate = formatter.parse(startUTC);
            Date finalEndDate = formatter.parse(endUTC);

            ResultSet otherAppts = AppointmentDAO.selectByCustomerID(customerID);
            while(otherAppts.next()){
                String otherStart = otherAppts.getString(6);
                String otherEnd = otherAppts.getString(7);
                Date otherStartDate = formatter.parse(otherStart);
                Date otherEndDate = formatter.parse(otherEnd);
                System.out.println(finalStartDate.after(otherStartDate));
                System.out.println(finalStartDate.before(otherEndDate));

                if((finalStartDate.after(otherStartDate) && finalStartDate.before(otherEndDate)) || (finalEndDate.before(otherEndDate) && finalEndDate.after(otherStartDate)) || (finalStartDate.equals(otherStartDate) && finalEndDate.equals(otherEndDate)) || (finalStartDate.before(otherStartDate) && finalEndDate.equals(otherEndDate)) || (finalStartDate.equals(otherStartDate) && finalEndDate.after(otherEndDate))){
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
                AppointmentDAO.insertAppointment(title, description, location, type, startUTC, endUTC, customerID, userID,contactID);

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
     * checks to ensure start and end time are within frame
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
