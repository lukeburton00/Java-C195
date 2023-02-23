package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CustomerQuery is the utility through which the app queries the Customers table.
 */
public abstract class CustomerQuery
{

    /**
     * addCustomer pushes a customer object to the database.
     * @param customer the customer to add.
     */
    public static void addCustomer(Customer customer)
    {
        try
        {
            String sqlStatement = "insert into Customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                    "values (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(customer.getID()));
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setInt(6, customer.getDivisionID());

            System.out.println("Customer successfully added.");
            preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
    }

    /**
     * getAllCustomers returns a list of all customers in the database via query.
     * @return the list of customers. ObservableList
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        try
        {
            String sqlStatement = "select * from customers;";

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

    /**
     * getAllCustomersForCountry returns a list of all customers associated with a given country via a join
     * query through the FirstLevelDivisions table.
     * @param countryID the ID of the country to query.
     * @return the list of customers. ObservableList
     */
    public static ObservableList<Customer> getAllCustomersForCountry(int countryID)
    {
        try
        {
            String sqlStatement = "select customers.* from customers\n" +
                    "inner join first_level_divisions \n" +
                    "on first_level_divisions.Division_ID = customers.Division_ID\n" +
                    "where first_level_divisions.Country_ID = ?;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(countryID));

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

    /**
     * deleteCustomer deletes a selected customer from the database.
     * @param customer the customer to delete.
     */
    public static void deleteCustomer(Customer customer)
    {
        try
        {
            String sqlStatement = "delete from customers where Customer_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(customer.getID()));

            preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
    }

    /**
     * getCurrentMaxID finds the highest ID in the customers table. This is used to auto-increment new customer Ids
     * upon creation.
     * @return the id. Integer
     */
    public static int getCurrentMaxID()
    {
        try
        {
            String sqlStatement = "select Customer_ID from Customers order by Customer_ID desc limit 1;";

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
