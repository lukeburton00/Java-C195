package model;

public class Contact {
    public int mID;
    public String mName, mEmail;

    public Contact(int id, String name, String email)
    {
        mID = id;
        mName = name;
        mEmail = email;
    }

    public int getID()
    {
        return mID;
    }

    public void setID(int id)
    {
        mID = id;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public void setEmail(String email)
    {
        mEmail = email;
    }
}
