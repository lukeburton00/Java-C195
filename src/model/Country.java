package model;

public class Country {
    private int mID;
    private String mCountry;

    public Country(int id, String country)
    {
        mID = id;
        mCountry = country;
    }

    public int getID()
    {
        return mID;
    }

    public void setID(int id)
    {
        mID = id;
    }

    public String getCountry()
    {
        return mCountry;
    }

    public void setCountry(String country)
    {
        mCountry = country;
    }
}
