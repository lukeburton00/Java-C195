package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactQuery
{
    public static ObservableList<Contact> getAllContacts()
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "SELECT * FROM contacts;";

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
}
