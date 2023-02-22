package model;

/**
 * Contact is the object to be created when performing CRUD operations on the Contacts table
 * for the MySQL database. A Contact object is meant to be temporary; it stores data pulled from
 * the database upon which the application operates.
 */
public class Contact {
    private int mID;
    private String mName, mEmail;

    /**
     * Constructor initializes Contact object and members.
     * @param id the id, Integer
     * @param name the name, Integer
     * @param email the email, Integer
     */
    public Contact(int id, String name, String email)
    {
        mID = id;
        mName = name;
        mEmail = email;
    }

    /**
     *
     * @return ID, Integer
     */
    public int getID()
    {
        return mID;
    }

    /**
     * set ID
     * @param id the value to be used, Integer
     */
    public void setID(int id)
    {
        mID = id;
    }

    /**
     *
     * @return name, String
     */
    public String getName()
    {
        return mName;
    }

    /**
     * set Name
     * @param name the value to be used, String
     */
    public void setName(String name)
    {
        mName = name;
    }
}
