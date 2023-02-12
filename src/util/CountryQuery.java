package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {
    public static ObservableList<Country> getAllCountries()
    {
        try
        {
            String sqlStatement = "SELECT * FROM countries;";

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

    public static Country getCountryFromName(String name)
    {
        try {
            String sqlStatement = "SELECT * FROM countries WHERE country = ?";

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

    public static Country getCountryFromDivisionID(int division)
    {
        try {
            String sqlStatement = "SELECT * FROM countries WHERE Country_ID = ?";

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
