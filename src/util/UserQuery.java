package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UserQuery {

    public static int insert(String userName, String password) throws SQLException {
        // The SQL Statement to execute
        String sqlStatement = "INSERT INTO users (User_Name, Password) VALUES(?,?)";

        // Prepare the SQL Statement for setting variables
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

        // Set variables in the SQL Statement
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2,password);

        // Execute Statement and return number of rows affected
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected;
    }
}
