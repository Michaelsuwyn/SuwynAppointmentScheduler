package sample.Model;

/**
 * Contact class
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Constructor for contact class
     * @param contactID
     * @param contactName
     * @param email
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * get contact ID of contact
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * set contact ID of contact
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * get contact name
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * set contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * get email of contact
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email of contact
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
