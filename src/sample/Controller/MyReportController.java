package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DAO.AppointmentDAO;
import sample.Model.Appointment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyReportController implements Initializable {
    public TableColumn locCol;
    public TableColumn countCol;
    public ObservableList<Appointment> allAppointmentList = FXCollections.observableArrayList();
    public TableView myReportTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet rs = AppointmentDAO.queryForMyReport();
            while (rs.next()){
                String location = rs.getString(4);
                int count = rs.getInt(5); //count will be customerID

                Appointment appointment = new Appointment(0, "", "", location, "","","",count, 0, "");
                allAppointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        myReportTable.setItems(allAppointmentList);
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }
}
