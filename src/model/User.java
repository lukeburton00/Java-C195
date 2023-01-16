package model;

public class User
{
    private int mID;
    private String mName;
    private String mPassword;

    public User(int id,String name,String password)
    {
        mID = id;
        mName = name;
        mPassword = password;
    }

    public int getID()
    {
        return mID;
    }

    public void setID(int ID)
    {
        mID = ID;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public void setPassword(String password)
    {
        mPassword = password;
    }






}