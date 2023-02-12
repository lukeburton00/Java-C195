package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery
{

    public static int addCustomer(Customer customer)
    {
        try
        {
            String sqlStatement = "INSERT INTO Customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(customer.getID()));
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setInt(6, customer.getDivisionID());

            System.out.println("Customer successfully added.");
            return preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
        }
    }
    public static ObservableList<Customer> getAllCustomers()
    {
        try
        {
            String sqlStatement = "SELECT * FROM customers;";

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

    public static int deleteCustomer(Customer customer)
    {
        try
        {
            String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(customer.getID()));

            return preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
        }
    }

    public static int getCurrentMaxID()
    {
        try
        {
            String sqlStatement = "select Customer_ID from Customers ORDER BY Customer_ID desc LIMIT 1;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results =  preparedStatement.executeQuery();
            results.next();
            return results.getInt("Customer_ID");
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
        }
    }
}
