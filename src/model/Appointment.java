package model;

import java.time.LocalDateTime;

/**
 * Appointment is the object to be created when performing CRUD operations on the Appointments table
 * for the MySQL database. An Appointment object is meant to be temporary; it stores data pulled from
 * the database upon which the application operates.
 */
public class Appointment
{
    private int mID, mCustomerID, mUserID, mContactID;
    private String mTitle, mDescription, mLocation, mType;
    private LocalDateTime mStart, mEnd;

    /**
     * Constructor initializes Appointment object and members.
     * @param id the id, Integer
     * @param title the title, String
     * @param description the description, String
     * @param location the location, String
     * @param type the type, String
     * @param start the start, LocalDateTime
     * @param end the end, LocalDateTime
     * @param customer_id the id for the Customer associated with this appointment in-database, Integer
     * @param user_id the id for the User associated with this appointment in-database, Integer
     * @param contact_id the id for the Contact associated with this appointment in-database, Integer
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id)
    {
        mID = id;
        mTitle = title;
        mDescription = description;
        mLocation = location;
        mType = type;
        mStart = start;
        mEnd = end;
        mCustomerID = customer_id;
        mUserID = user_id;
        mContactID = contact_id;
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
     * @param ID the value to be used, Integer
     */
    public void setID(int ID) { mID = ID; }

    /**
     *
     * @return ID for Customer associated with this Appointment, Integer
     */
    public int getCustomerID()
    {
        return mCustomerID;
    }

    /**
     *
     * @return ID for User associated with this Appointment, Integer
     */
    public int getUserID()
    {
        return mUserID;
    }

    /**
     *
     * @return ID for Contact associated with this Appointment, Integer
     */
    public int getContactID()
    {
        return mContactID;
    }

    /**
     *
     * @return Title, String
     */
    public String getTitle() { return mTitle; }

    /**
     *
     * @return Description, String
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     *
     * @return Location, String
     */
    public String getLocation() { return mLocation; }

    /**
     *
     * @return Type, String
     */
    public String getType()
    {
        return mType;
    }

    /**
     *
     * @return Start, LocalDateTime
     */
    public LocalDateTime getStart()
    {
        return mStart;
    }

    /**
     * set Start
     * @param start the value to be used, LocalDateTime
     */
    public void setStart(LocalDateTime start) { mStart = start; }

    /**
     *
     * @return End, LocalDateTime
     */
    public LocalDateTime getEnd()
    {
        return mEnd;
    }

    /**
     * set End
     * @param end the value to be used, LocalDateTime
     */
    public void setEnd(LocalDateTime end) { mEnd = end; }
}
