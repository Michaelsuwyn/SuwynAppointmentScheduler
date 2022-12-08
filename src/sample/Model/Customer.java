package sample.Model;

/**
 * Customer Class
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;

    private int divisionID;

    /**
     * Constructor for customer class
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * get the ID of customer
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * set the Id of customer
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * get the name of customer
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * set the name of customer
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * get the address of customer
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * set the address of customer
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get the postal code of customer
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * set the postal code of customer
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * get the phone number of customer
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * set the phone number of customer
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * get the first level division ID of customer
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * set the first level division ID of customer
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
