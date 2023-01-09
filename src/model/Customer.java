package model;

public class Customer
{
    private int mID;
    private int mDivisionID;
    private String mName;
    private String mAddress;
    private String mPostalCode;
    private String mPhone;

    public Customer(int id, int divisionID, String name, String address, String postalCode, String phone)
    {
        mID = id;
        mDivisionID = divisionID;
        mName = name;
        mAddress = address;
        mPostalCode = postalCode;
        mPhone = phone;

    }

    public int getID() { return mID; }

    public void setID(int ID) { mID = ID; }

    public int getDivisionID() { return mDivisionID; }

    public void setDivisionID(int DivisionID) { mDivisionID = DivisionID; }

    public String getName() { return mName; }

    public void setName(String Name) { mName = Name; }

    public String getAddress() { return mAddress; }

    public void setAddress(String Address) { mAddress = Address; }

    public String getPostalCode() { return mPostalCode; }

    public void setPostalCode(String PostalCode) { mPostalCode = PostalCode; }

    public String getPhone() { return mPhone; }

    public void setPhone(String Phone) { mPhone = Phone; }
}
