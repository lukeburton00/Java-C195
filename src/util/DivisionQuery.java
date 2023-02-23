package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DivisionQuery is the utility through which the app queries the FirstLevelDivisions table.
 */
public abstract class DivisionQuery {
    /**
     * getAllDivisionsForCountry returns a list of all divisions for a given country via database query,
     * @param country the country for which to return the list.
     * @return the list of divisions. ObservableList
     */
    public static ObservableList<FirstLevelDivision> getAllDivisionsForCountry(Country country)
    {
        try
        {
            String sqlStatement = "select * from first_level_divisions where country_id = ?;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, country.getID());

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<FirstLevelDivision> resultDivisions = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Division_ID");
                String name = results.getString("Division");
                int countryId = results.getInt("Country_ID");

                FirstLevelDivision division = new FirstLevelDivision(id, countryId, name);
                resultDivisions.add(division);
            }
            return resultDivisions;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }

    /**
     * getDivisionFromID returns a FirstLevelDivision object from a given ID via database query.
     * @param division the division ID to query.
     * @return the FirstLevelDivision object.
     */
    public static FirstLevelDivision getDivisionFromID(int division) {
        try {
            String sqlStatement = "select * from first_level_divisions where Division_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, division);
            FirstLevelDivision result = null;

            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                int id = results.getInt("Division_ID");
                String name = results.getString("Division");
                int countryId = results.getInt("Country_ID");

                result = new FirstLevelDivision(id, countryId, name);
            }
            return result;
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }

    /**
     * getDivisionFromName returns a FirstLevelDivision object from a given String via database query.
     * @param division the name of the division to query.
     * @return the FirstLevelDivision object.
     */
    public static FirstLevelDivision getDivisionFromName(String division) {
        try {
            String sqlStatement = "select * from first_level_divisions where Division = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, division);
            FirstLevelDivision result = null;

            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                int id = results.getInt("Division_ID");
                String name = results.getString("Division");
                int countryId = results.getInt("Country_ID");

                result = new FirstLevelDivision(id, countryId, name);
            }
            return result;
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }
}
