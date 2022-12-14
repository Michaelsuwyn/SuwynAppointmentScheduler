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
import javafx.stage.Stage;
import sample.DAO.AppointmentDAO;
import sample.Model.Appointment;

import javax.xml.stream.events.StartDocument;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Class which contains the code relevant to the all appointments page ----> LAMBDA EXPRESSIONS UNDER thisWeekMethod and thisMonthMethod
 */
public class AllAppointmentsController implements Initializable {
    public TableColumn appID;
    public TableColumn appTitle;
    public TableColumn appDescription;
    public TableColumn appLocation;
    public TableColumn appContact;
    public TableColumn appType;
    public TableColumn appStart;
    public TableColumn appEnd;
    public TableColumn appCustomerID;
    public TableColumn appUserID;
    public ObservableList<Appointment> allAppointmentList = FXCollections.observableArrayList();
    public ObservableList<Appointment> thisWeekAppointmentsList = FXCollections.observableArrayList();
    public ObservableList<Appointment> thisMonthAppointmentsList = FXCollections.observableArrayList();

    public TableView allAppointmentsTable;
    public String contact;
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ZoneId zoneID = ZoneId.systemDefault();
    public static Appointment selectedAppointment;
    public RadioButton thisWeekID;
    public RadioButton thisMonthID;
    public RadioButton allApptsID;
    public Label filterLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allApptsID.setSelected(true);

        try {
            ResultSet rs = AppointmentDAO.selectAll();
            while(rs.next()){
                int ID = rs.getInt(1);
                String Title = rs.getString(2);
                String Description = rs.getString(3);
                String Location = rs.getString(4);
                int ContactID = rs.getInt(14);
                String Type = rs.getString(5);
                String Start = rs.getString(6); //startDate
                String End = rs.getString(7); //endDate
                int CustomerID = rs.getInt(12);
                int UserID = rs.getInt(13);

                String localStartTime = convertDateTime(Start, zoneID.toString());
                String localEndTime = convertDateTime(End, zoneID.toString());

                if(ContactID == 1){
                    contact = "Anika Costa";
                }
                else if(ContactID ==2){
                    contact = "Daniel Garcia";
                }
                else if(ContactID == 3){
                    contact = "Li Lee";
                }

                Appointment appointment = new Appointment(ID, Title, Description, Location, Type, localStartTime, localEndTime, CustomerID, UserID, contact);
                allAppointmentList.add(appointment);
            }

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        allAppointmentsTable.setItems(allAppointmentList);
        appID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        appCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));

    }

    /**
     * navigation to selection page
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

    /**
     * function for converting string date to time within timezone
     * @param dt
     * @param timezone
     * @return
     * @throws ParseException
     */
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


    /**
     * navigation to add appointment page
     * @param actionEvent
     * @throws IOException
     */
    public void toAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * function that will delete appointment on selection
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void deleteAppt(ActionEvent actionEvent) throws SQLException, IOException {
        if(allAppointmentsTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                selectedAppointment = (Appointment) allAppointmentsTable.getSelectionModel().getSelectedItem();
                AppointmentDAO.deleteByID(selectedAppointment.getAppointmentID());

                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment has been deleted \n Details: \n Appointment ID - " + selectedAppointment.getAppointmentID() + "\n Appointment Type - " + selectedAppointment.getType());
                Optional<ButtonType> deleteResult = deleteAlert.showAndWait();

                Parent root = FXMLLoader.load(getClass().getResource("../View/AllAppointments.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1100, 800);
                stage.setTitle("Customers");
                stage.setScene(scene);
                stage.show();
            }

        }
        else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "No Appointment has been selected");
            Optional<ButtonType> result = noSelection.showAndWait();
        }
    }

    /**
     * function to edit appointment on selection
     * @param actionEvent
     * @throws IOException
     */
    public void toEditAppointment(ActionEvent actionEvent) throws IOException {
        if(allAppointmentsTable.getSelectionModel().getSelectedItem() != null){
            selectedAppointment = (Appointment) allAppointmentsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("../View/EditAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 800);
            stage.setTitle("Edit Appointment");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment to edit");
            Optional<ButtonType> result = noSelection.showAndWait();
        }
    }

    /**
     * function to filter appointments by week ---> LAMBDA expression here
     * @param actionEvent
     */
    public void thisWeekMethod(ActionEvent actionEvent) {
        if(thisWeekID.isSelected() == true){
            allApptsID.setSelected(false);
            thisMonthID.setSelected(false);
            LocalDate monday = getStartOfWeek();
            LocalDate sunday = getEndOfWeek();
            filterLabel.setText(monday.toString() + " - " + sunday.toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            //Lambda Expression
            allAppointmentList.forEach((a) -> {
                String startDateTime = a.getStartDate();
                String startDate = startDateTime.substring(0,10);
                LocalDate apptDate = LocalDate.parse(startDate, formatter);
                if(apptDate.isBefore(sunday) && apptDate.isAfter(monday)){
                    thisWeekAppointmentsList.add(a);
                }
            });

            allAppointmentsTable.setItems(thisWeekAppointmentsList);
            thisMonthAppointmentsList.clear();
        }
        else {
            thisWeekAppointmentsList.clear();
        }
    }

    /**
     * function to filter appointments by month ---> LAMBDA expression here
     * @param actionEvent
     */
    public void thisMonthMethod(ActionEvent actionEvent) {
        if(thisMonthID.isSelected() == true){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            thisWeekID.setSelected(false);
            allApptsID.setSelected(false);
            LocalDate today = LocalDate.now();
            LocalDate startMonth = today.withDayOfMonth(1);
            LocalDate endMonth = today.withDayOfMonth(today.getMonth().length(today.isLeapYear()));
            filterLabel.setText(startMonth.toString() + " - " + endMonth.toString());

            //Lambda Expression
            allAppointmentList.forEach((a) -> {
                String startDateTime = a.getStartDate();
                String startDate = startDateTime.substring(0,10);
                LocalDate apptDate = LocalDate.parse(startDate, formatter);
                if(apptDate.isBefore(endMonth) && apptDate.isAfter(startMonth)){
                    thisMonthAppointmentsList.add(a);
                }
            });

            allAppointmentsTable.setItems(thisMonthAppointmentsList);
            thisWeekAppointmentsList.clear();
        }
        else {
            thisMonthAppointmentsList.clear();
        }
    }

    /**
     * function to filter all appointments
     * @param actionEvent
     */
    public void allAppointmentsMethod(ActionEvent actionEvent) {
        if(allApptsID.isSelected() == true){
            thisWeekID.setSelected(false);
            thisMonthID.setSelected(false);
            filterLabel.setText("");
            allAppointmentsTable.setItems(allAppointmentList);
            thisWeekAppointmentsList.clear();
            thisMonthAppointmentsList.clear();
        }
    }

    /**
     * function to get the start of week date
     * @return
     */
    public LocalDate getStartOfWeek(){
        LocalDate today = LocalDate.now();
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    /**
     * function to get the end of the week
     * @return
     */
    public LocalDate getEndOfWeek(){
        LocalDate today = LocalDate.now();

        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }
}

