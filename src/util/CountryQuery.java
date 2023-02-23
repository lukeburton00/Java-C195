package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CountryQuery is the utility through which the app queries the Countries table.
 */
public abstract class CountryQuery {

    /**
     * getAllCountries queries the database for a list of all countries
     * @return the list of countries. ObservableList
     */
    public static ObservableList<Country> getAllCountries()
    {
        try
        {
            String sqlStatement = "select * from countries;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Country> resultCountries = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Country_ID");
                String countryName = results.getString("Country");

                Country country = new Country(id, countryName);
                resultCountries.add(country);
            }
            return resultCountries;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }

    /**
     * getCountryFromName converts a country name to a country object via database query.
     * @param name the string name to convert
     * @return the country object.
     */
    public static Country getCountryFromName(String name)
    {
        try {
            String sqlStatement = "select * from countries where country = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, name);
            Country country = null;

            ResultSet results = preparedStatement.executeQuery();
            while (results.next())
            {
                int id = results.getInt("Country_ID");
                String countryName = results.getString("Country");
                country = new Country(id, countryName);
            }
            return country;
        }

        catch(SQLException e)
            {
                String errorMessage = e.getMessage();
                System.out.println(errorMessage);
                return null;
            }
        }

    /**
     * getCountryFromDivisionID converts a division id to a country object via database query.
     * @param division the id to convert.
     * @return the country object
     */
    public static Country getCountryFromDivisionID(int division)
    {
        try {
            String sqlStatement = "select * from countries where Country_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, DivisionQuery.getDivisionFromID(division).getCountryID());
            Country country = null;

            ResultSet results = preparedStatement.executeQuery();
            while (results.next())
            {
                int id = results.getInt("Country_ID");
                String countryName = results.getString("Country");
                country = new Country(id, countryName);
            }
            return country;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }
}
