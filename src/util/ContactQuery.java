package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ContactQuery is the utility through which the app queries the Contacts table.
 */
public abstract class ContactQuery
{
    /**
     * getAllContacts queries the contacts table for all contacts
     * @return the list of contacts. ObservableList
     */
    public static ObservableList<Contact> getAllContacts()
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "select * from contacts;";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Contact> resultContacts = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Contact_ID");
                String name = results.getString("Contact_Name");
                String email = results.getString("Email");

                Contact contact = new Contact(id, name, email);
                resultContacts.add(contact);
            }
            return resultContacts;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }

    /**
     * getContactIDFromName converts a given string contact name to a corresponding ID via database query
     * @param name the name to convert
     * @return the id. Integer
     */
    public static int getContactIDFromName(String name)
    {
        try
        {
            String sqlStatement = "select Contact_ID from contacts where Contact_Name = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, name);

            ResultSet results = preparedStatement.executeQuery();
            results.next();
            return results.getInt("Contact_ID");
        }
        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
        }
    }

    /**
     * getContactNameFromID converts a given integer id to a corresponding name via database query
     * @param id the id to convert
     * @return the name. String
     */
    public static String getContactNameFromID(int id)
    {
        try
        {
            String sqlStatement = "select Contact_Name from contacts where Contact_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet results = preparedStatement.executeQuery();
            results.next();
            return results.getString("Contact_Name");
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return "Null";
        }
    }
}
