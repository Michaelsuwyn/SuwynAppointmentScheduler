package sample.Model;

/**
 * User class
 */
public class User {
    private int userID;
    private String userName;
    private String password;

    /**
     * Constructor for user class
     * @param userID
     * @param userName
     * @param password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * get the ID of user
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set the ID of user
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get the name of user
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set the name of user
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get the password of user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * set the password of user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
