package model;

/**
 * First Level Division is the object to be created when performing CRUD operations on the
 * First Level Division table for the MySQL database. A First Level Division object is meant to be
 * temporary; it stores data pulled from
 * the database upon which the application operates.
 * The Division member variable is so named because the name of the First Level Division is stored
 * under the Division column in-database.
 */
public class FirstLevelDivision {
    private int mID, mCountryID;
    private String mDivision;

    /**
     * Constructor initializes First Level Division object and members.
     * @param id the id, Integer
     * @param countryID the id for the associated Country, Integer
     * @param division the division name, String
     */
    public FirstLevelDivision(int id, int countryID, String division)
    {
        mID = id;
        mCountryID = countryID;
        mDivision = division;
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
     * @return the id for the associated Country, Integer
     */
    public int getCountryID()
    {
        return mCountryID;
    }

    /**
     *
     * @return the division name, String
     */
    public String getDivision()
    {
        return mDivision;
    }
}
