package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserQuery is a utility for querying the User table in the database. Functionality is limited,
 * as the scope of the application does not involve the full CRUD suite of operations.
 */
public abstract class UserQuery {

    /**
     * authenticate returns true of a user exists with the given username and password
     * @param userName the username for the user query
     * @param password the password for the user query
     * @return true if user exists, false otherwise
     */
    public static boolean authenticate(String userName, String password)
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "SELECT * FROM users WHERE User_Name = ? AND password = ?";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            // Set variables in the SQL Statement
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet results = preparedStatement.executeQuery();
            results.next();

            return results.getString("User_Name").equals(userName) && results.getString("password").equals(password);
        }

        catch(SQLException e)
        {
            return false;
        }
    }
}
