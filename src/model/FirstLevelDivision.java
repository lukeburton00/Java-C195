package model;

public class FirstLevelDivision {
    private int mID, mCountryID;
    private String mDivision;

    public FirstLevelDivision(int id, int countryID, String division)
    {
        mID = id;
        mCountryID = countryID;
        mDivision = division;
    }

    public int getID()
    {
        return mID;
    }

    public void setID(int id)
    {
       mID = id;
    }

    public int getCountryID()
    {
        return mCountryID;
    }

    public void setCountryID(int countryID)
    {
        mCountryID = countryID;
    }

    public String getDivision()
    {
        return mDivision;
    }

    public void setDivision(String division)
    {
        mDivision = division;
    }
}
