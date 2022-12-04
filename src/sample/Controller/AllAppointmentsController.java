package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
    public TableView allAppointmentsTable;
    public String contact;
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ZoneId zoneID = ZoneId.systemDefault();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void toAppOrCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomersOrAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Customers or Appointments");
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
