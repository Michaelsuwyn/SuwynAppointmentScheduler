package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DAO.AppointmentDAO;
import sample.Model.Appointment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class ContactReportController implements Initializable {
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptType;
    public TableColumn apptDesc;
    public TableColumn apptStart;
    public TableColumn apptEnd;
    public TableColumn apptCustomerID;
    public ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public TableView contactTable;
    public RadioButton anikaRadio;
    public RadioButton danielRadio;
    public RadioButton leeRadio;
    public ZoneId zoneID = ZoneId.systemDefault();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void anikaChoice(ActionEvent actionEvent) {
        appointmentList.clear();
        if(anikaRadio.isSelected() == true){
            danielRadio.setSelected(false);
            leeRadio.setSelected(false);
            updateContactReport(1);
        }
    }

    public void danielChoice(ActionEvent actionEvent) {
        appointmentList.clear();
        if(danielRadio.isSelected() == true){
            anikaRadio.setSelected(false);
            leeRadio.setSelected(false);
            updateContactReport(2);
        }
    }

    public void leeChoice(ActionEvent actionEvent) {
        appointmentList.clear();
        if(leeRadio.isSelected() == true){
            anikaRadio.setSelected(false);
            danielRadio.setSelected(false);
            updateContactReport(3);
        }
    }

    public void updateContactReport(int contactID){
        try {
            ResultSet rs = AppointmentDAO.selectByContactID(contactID);
            while(rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String type = rs.getString(5);
                String description = rs.getString(3);
                String startDate = rs.getString(6);
                String endDate = rs.getString(7);
                int customerID = rs.getInt(13);

                String localStartTime = convertDateTime(startDate, zoneID.toString());
                String localEndTime = convertDateTime(endDate, zoneID.toString());

                Appointment appointment = new Appointment(id, title, description, "",type, localStartTime, localEndTime,customerID, 0, null);
                appointmentList.add(appointment);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        contactTable.setItems(appointmentList);
        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
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
