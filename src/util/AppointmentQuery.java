package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

/**
 * AppointmentQuery is the utility through which the app queries the Appointments table.
 */
public abstract class AppointmentQuery
{

    /**
     * addAppointment pushes a given appointment to the database.
     * @param appointment the appointment to add.
     */
    public static void addAppointment(Appointment appointment)
    {
        try
        {
            String sqlStatement = "INSERT INTO Appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(appointment.getID()));
            preparedStatement.setString(2, appointment.getTitle());
            preparedStatement.setString(3, appointment.getDescription());
            preparedStatement.setString(4, appointment.getLocation());
            preparedStatement.setString(5, appointment.getType());
            preparedStatement.setString(6, String.valueOf(appointment.getStart()));
            preparedStatement.setString(7, String.valueOf(appointment.getEnd()));
            preparedStatement.setString(8, Integer.toString(appointment.getCustomerID()));
            preparedStatement.setString(9, Integer.toString(appointment.getUserID()));
            preparedStatement.setString(10, Integer.toString(appointment.getContactID()));

            System.out.println("Appointment successfully added.");
            preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
    }

    /**
     * get all appointments in database.
     * @return a list of appointments. ObservableList
     */
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
                LocalDateTime start = Time.systemToUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.systemToUTC(results.getTimestamp("End").toLocalDateTime());
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

    /**
     * deleteAppointment removes a given appointment from the database.
     * @param appointment the appointment to delete
     */
    public static void deleteAppointment(Appointment appointment)
    {
        try
        {
            String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(appointment.getID()));

            preparedStatement.executeUpdate();
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
    }

    /**
     * getCurrentMaxID returns the highest ID currently held by an appointment in the database.
     * This is used to auto-increment the ID for new appointments.
     * @return the max ID. Integer
     */
    public static int getCurrentMaxID()
    {
        try
        {
            String sqlStatement = "select Appointment_ID from Appointments ORDER BY Appointment_ID desc LIMIT 1;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results =  preparedStatement.executeQuery();
            results.next();
            return results.getInt("Appointment_ID");
        }

        catch (SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
        }
    }

    /**
     * getAllAppointmentsForCustomer returns all appointmetns associated with a given customer.
     * @param customer the customer object for which to get appointments
     * @return the list of appointments. ObservableList
     */
    public static ObservableList<Appointment> getAllAppointmentsForCustomer(Customer customer)
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "SELECT * FROM appointments WHERE Customer_ID = ?";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Integer.toString(customer.getID()));

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Appointment> resultAppointments = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = Time.systemToUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.systemToUTC(results.getTimestamp("End").toLocalDateTime());
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

    /**
     * getAllAppointmentsForThisWeek queries for appointments that fall between now and one week
     * from now.
     * @return the list of appointments. ObservableList
     */
    public static ObservableList<Appointment> getAllAppointmentsForThisWeek()
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "select * from appointments where start between curdate() and date_add(curdate(), interval 1 week);";

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
                LocalDateTime start = Time.systemToUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.systemToUTC(results.getTimestamp("End").toLocalDateTime());
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

    /**
     * getAllAppointmentsForMonth queries for a list of appointments that fall within a given month.
     * @param month the month to check
     * @return the list of appointments. ObservableList
     */
    public static ObservableList<Appointment> getAllAppointmentsForMonth(String month)
    {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM");
        TemporalAccessor accessor = parser.parse(month);
        int monthAsInt = accessor.get(ChronoField.MONTH_OF_YEAR);

        try
        {
            // The SQL Statement to execute
            String sqlStatement = "select * from appointments where month(start) = ?;";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, monthAsInt);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Appointment> resultAppointments = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = Time.systemToUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.systemToUTC(results.getTimestamp("End").toLocalDateTime());
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

    /**
     * getAllAppointmentsForTypeAndMonth queries for a list of appointments filterd by a given type and
     * a given month.
     * @param type the type to filter
     * @param month the month to filter
     * @return the list of appointments. ObservableList
     */
    public static ObservableList<Appointment> getAllAppointmentsForTypeAndMonth(String type, String month)
    {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM");
        TemporalAccessor accessor = parser.parse(month);
        int monthAsInt = accessor.get(ChronoField.MONTH_OF_YEAR);

        try
        {
            // The SQL Statement to execute
            String sqlStatement = "select * from appointments where month(start) = ? and type = ?;";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, monthAsInt);
            preparedStatement.setString(2, type);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Appointment> resultAppointments = FXCollections.observableArrayList();

            while (results.next())
            {
                int id = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String appType = results.getString("Type");
                LocalDateTime start = Time.systemToUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.systemToUTC(results.getTimestamp("End").toLocalDateTime());
                int customer_id = results.getInt("Customer_ID");
                int user_id = results.getInt("User_ID");
                int contact_id = results.getInt("Contact_ID");

                Appointment appointment = new Appointment(id, title, description, location, appType, start, end, customer_id, user_id, contact_id);
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

    /**
     * getAllAppointmentsForContactID queries for appointments associated with a contact.
     * @param id the ID of the contact
     * @return the list of appointments. ObservableList
     */
    public static ObservableList<Appointment> getAllAppointmentsForContactID(int id)
    {

        try
        {
            // The SQL Statement to execute
            String sqlStatement = "select * from appointments where Contact_ID = ?";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<Appointment> resultAppointments = FXCollections.observableArrayList();

            while (results.next())
            {
                int appID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String appType = results.getString("Type");
                LocalDateTime start = Time.systemToUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.systemToUTC(results.getTimestamp("End").toLocalDateTime());
                int customer_id = results.getInt("Customer_ID");
                int user_id = results.getInt("User_ID");
                int contact_id = results.getInt("Contact_ID");

                Appointment appointment = new Appointment(appID, title, description, location, appType, start, end, customer_id, user_id, contact_id);
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

    /**
     * getAllAppointmentTypes queries for all types of appoiontments.
     * @return the list of types. ObservableList
     */
    public static ObservableList<String> getAllAppointmentTypes()
    {
        try
        {
            // The SQL Statement to execute
            String sqlStatement = "select Type from appointments;";

            // Prepare the SQL Statement for setting variables
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlStatement);

            ResultSet results = preparedStatement.executeQuery();
            ObservableList<String> resultTypes = FXCollections.observableArrayList();

            while (results.next())
            {
                String type = results.getString("Type");
                resultTypes.add(type);
            }
            return resultTypes;
        }

        catch(SQLException e)
        {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return null;
        }
    }

}
