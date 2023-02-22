package model;

/**
 * Country is the object to be created when performing CRUD operations on the Country table
 * for the MySQL database. A Country object is meant to be temporary; it stores data pulled from
 * the database upon which the application operates.
 * The Country member variable is so named because the name of the country is stored
 * under the Country column in-database.
 */
public class Country {
    private int mID;
    private String mCountry;

    /**
     * Constructor initializes Country object and members.
     * @param id the id, Integer
     * @param country, the country Name, String
     */
    public Country(int id, String country)
    {
        mID = id;
        mCountry = country;
    }

    /**
     *
     * @return the id, Integer
     */
    public int getID()
    {
        return mID;
    }

    /**
     * set the id
     * @param id the value to be used, Integer
     */
    public void setID(int id)
    {
        mID = id;
    }

    /**
     *
     * @return the country Name, Integer
     */
    public String getCountry()
    {
        return mCountry;
    }
}
