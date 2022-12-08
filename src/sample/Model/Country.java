package sample.Model;

/**
 * Class for country
 */
public class Country {

    private int countryID;
    private String countryName;

    /**
     * country class constructor
     * @param countryID
     * @param countryName
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * get the ID of country
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * set the Id of country
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * get the name of country
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * set the name of country
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
