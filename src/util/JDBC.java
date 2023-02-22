package util;

import javafx.scene.chart.ScatterChart;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC is a simple utility that creates and destroys a database connection.
 */
public abstract class JDBC
{
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";

    public static Connection connection;

    /**
     * openConnection gets a connection to a database based on the member variables.
     */
    public static void openConnection()
    {
        try
        {
            Class.forName(driver);
             connection = DriverManager.getConnection(jdbcUrl,userName,password);
             System.out.println("Connection to DB successful.");

        }

        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * closeConnection closes the existing database connection.
     */
    public static void closeConnection()
    {
        try
        {
            connection.close();
            System.out.println("Connection to DB closed.");
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
