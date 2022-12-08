package sample.Model;

import java.util.Date;

/**
 * Appointment Class
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String startDate;
    private String endDate;

    private int customerID;
    private int userID;
    private String contact;

    /**
     * Constructor for appointment class
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDate
     * @param endDate
     * @param customerID
     * @param userID
     * @param contact
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, String startDate, String endDate, int customerID, int userID, String contact) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.userID = userID;
        this.contact = contact;
    }

    /**
     * get appointment ID
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *  set appointment ID
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * get title of appointment
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * set title of appointment
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * get description of appointment
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description of appointment
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get location of appointment
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * set location of appointment
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get type of appointment
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set type of appointment
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get start date of appointment
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * set start date of appointment
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * get end date of appointment
     * @return
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * set end date of appointment
     * @param endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * get customer ID reference of appointment
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * set customer ID reference of appointment
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * get user ID reference of appointment
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set user ID reference of appointment
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get contact ID reference of appointment
     * @return
     */
    public String getContactID() {
        return contact;
    }

    /**
     * set contact ID reference of appointment
     * @param contact
     */
    public void setContactID(String contact) {
        this.contact = contact;
    }
}

