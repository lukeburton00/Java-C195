package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserQuery {

    public static boolean authenticate(String userName, String password) throws SQLException
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
