package sample.Model;

public class CustomerReceiver {
    private int customerReceiverID;
    private String name;
    private String address;
    private String postal;
    private String division;
    private String phone;

    public CustomerReceiver(int customerReceiverID, String name, String address, String postal, String division, String phone) {
        this.customerReceiverID = customerReceiverID;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.division = division;
        this.phone = phone;
    }

    public int getCustomerReceiverID() {
        return customerReceiverID;
    }

    public void setCustomerReceiverID(int customerReceiverID) {
        this.customerReceiverID = customerReceiverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
