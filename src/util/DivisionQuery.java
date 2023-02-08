package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionQuery {
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
}
