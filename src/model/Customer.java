package model;

/**
 * Customer is the object to be created when performing CRUD operations on the Customer table
 * for the MySQL database. A Customer object is meant to be temporary; it stores data pulled from
 * the database upon which the application operates.
 */
public class Customer
{
    private int mID;
    private int mDivisionID;
    private String mName;
    private String mAddress;
    private String mPostalCode;
    private String mPhone;

    /**
     * Constructor initializes Customer object and members.
     * @param id the id, Integer
     * @param divisionID the id for the associated First Level Division, Integer
     * @param name the name, String
     * @param address the address, String
     * @param postalCode the postal code, String
     * @param phone the phone, String
     */
    public Customer(int id, int divisionID, String name, String address, String postalCode, String phone)
    {
        mID = id;
        mDivisionID = divisionID;
        mName = name;
        mAddress = address;
        mPostalCode = postalCode;
        mPhone = phone;

    }

    /**
     *
     * @return the id, Integer
     */
    public int getID() { return mID; }

    /**
     * set the id
     * @param ID the value to be used, Integer
     */
    public void setID(int ID) { mID = ID; }

    /**
     *
     * @return the id for the associated First Level Division, Integer
     */
    public int getDivisionID() { return mDivisionID; }

    /**
     *
     * @return the name, String
     */
    public String getName() { return mName; }

    /**
     * set the name
     * @param Name the value to be used, String
     */
    public void setName(String Name) { mName = Name; }

    /**
     *
     * @return the address, String
     */
    public String getAddress() { return mAddress; }

    /**
     *
     * @return the postal code, String
     */
    public String getPostalCode() { return mPostalCode; }

    /**
     *
     * @return the phone, String
     */
    public String getPhone() { return mPhone; }
}
