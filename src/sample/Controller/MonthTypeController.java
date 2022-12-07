package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DAO.AppointmentDAO;
import sample.Model.Appointment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MonthTypeController implements Initializable {
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn descCol;
    public TableColumn typeCol;
    public ComboBox monthCombo;
    public ComboBox typeCombo;
    public ObservableList<Appointment> allAppointmentList = FXCollections.observableArrayList();
    public ObservableList<Appointment> filteredList = FXCollections.observableArrayList();

    public TableView monthTypeTable;
    public TableColumn monthCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthCombo.setValue("");
        typeCombo.setValue("");
        try {
            ResultSet rs = AppointmentDAO.selectAll();
            while (rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String description = rs.getString(3);
                String start = rs.getString(6);
                String type = rs.getString(5);
                String monthNum = start.substring(5,7);
                String date = numToMonth(monthNum);


                Appointment appointment = new Appointment(id, title, description, "", type, date, "", 0,0,"");
                allAppointmentList.add(appointment);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        monthTypeTable.setItems(allAppointmentList);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        monthCombo.getItems().addAll("January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        typeCombo.getItems().addAll("Planning Session", "De-Briefing", "Business Meeting", "Review", "Other");

    }

    public String numToMonth(String monthNum){
        String month;
        switch (monthNum){
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + monthNum);
        }
        return month;
    }

    public void onMonthAction(ActionEvent actionEvent) {
        filteredList.clear();
       String monthChoice =  monthCombo.getValue().toString();
       String typeChoice = typeCombo.getValue().toString();
        for(int i = 0; i < allAppointmentList.size(); i++){
            if(allAppointmentList.get(i).getStartDate().equals(monthChoice) && allAppointmentList.get(i).getType().equals(typeChoice)){
                filteredList.add(allAppointmentList.get(i));
            }
        }
        monthTypeTable.setItems(filteredList);
    }

    public void onTypeAction(ActionEvent actionEvent) {
        filteredList.clear();
        String monthChoice =  monthCombo.getValue().toString();
        String typeChoice = typeCombo.getValue().toString();
        for(int i = 0; i < allAppointmentList.size(); i++){
            if(allAppointmentList.get(i).getStartDate().equals(monthChoice) && allAppointmentList.get(i).getType().equals(typeChoice)){
                filteredList.add(allAppointmentList.get(i));
            }
        }
        monthTypeTable.setItems(filteredList);
    }
}
