package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery
{
    public static ObservableList<Customer> getAllCustomers() throws SQLException
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "SELECT * FROM customers";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Customer> resultCustomers = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Customer_ID");
                String name = results.getString("Customer_Name");
                String address = results.getString("Address");
                String postal = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                int divisionID = results.getInt("Division_ID");

                Customer customer = new Customer(id, divisionID, name, address, postal, phone);
                resultCustomers.add(customer);
            }
            return resultCustomers;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }
}
