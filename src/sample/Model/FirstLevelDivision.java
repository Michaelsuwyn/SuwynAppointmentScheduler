package sample.Model;

/**
 * First level division class
 */
public class FirstLevelDivision {
    private int divisionID;
    private String divisionName;

    private int countryID;

    /**
     * Constructor for first level division
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public FirstLevelDivision(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * get ID of division
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * set ID of division
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * get name of division
     * @return
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * set name of division
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * get country ID reference of division
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * set country ID reference of division
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
