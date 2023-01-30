package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class AppointmentQuery
{
    public static ObservableList<Appointment> getAllAppointments()
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "SELECT * FROM appointments";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Appointment> resultAppointments = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = results.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = results.getTimestamp("End").toLocalDateTime();
                int customer_id = results.getInt("Customer_ID");
                int user_id = results.getInt("User_ID");
                int contact_id = results.getInt("Contact_ID");

                Appointment appointment = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                resultAppointments.add(appointment);
            }
            return resultAppointments;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }

    public static int deleteAppointment(Appointment appointment)
    {
        try
        {
            String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(appointment.getID()));

            return preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
        }
    }

}
