package model;

import java.sql.Date;

public class Appointment
{
    private int mID, mCustomerID, mUserID, mContactID;
    private String mTitle, mDescription, mLocation, mType;
    private Date mStart, mEnd;

    public Appointment(int id, String title, String description, String location, String type, Date start, Date end, int customer_id, int user_id, int contact_id)
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

    public int getID()
    {
        return mID;
    }

    public void setID(int ID) { mID = ID; }

    public int getCustomerID()
    {
        return mCustomerID;
    }

    public void setCustomerID(int customerID) { mCustomerID = customerID; }

    public int getUserID()
    {
        return mUserID;
    }

    public void setUserID(int userID) { mUserID = userID; }

    public int getContactID()
    {
        return mContactID;
    }

    public void setContactID(int contactID) { mContactID = contactID; }

    public String getTitle() { return mTitle; }

    public void setTitle(String title) { mTitle = title; }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description) { mDescription = description; }

    public String getLocation() { return mLocation; }

    public void setLocation(String location) { mLocation = location; }

    public String getType()
    {
        return mType;
    }

    public void setType(String type) { mType = type; }

    public Date getStart()
    {
        return mStart;
    }

    public void setStart(Date start) { mStart = start; }

    public Date getEnd()
    {
        return mEnd;
    }

    public void setEnd(Date end) { mEnd = end; }
}
