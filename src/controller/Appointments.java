package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Customer;
import util.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Appointments controller serves the primary purpose of displaying appointments from the database
 * according to filter criteria, including simply displaying all appointments. This controller also routes the user to
 * other relevant pages on request. Reports are also displayed per project requirements.
 */
public class Appointments
{

    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn<Appointment, String> appointmentTitleColumn;
    public TableColumn<Appointment, String> appointmentDescriptionColumn;
    public TableColumn<Appointment, String> appointmentLocationColumn;
    public TableColumn<Appointment, String> appointmentTypeColumn;
    public TableColumn<Appointment, LocalDateTime> appointmentStartColumn;
    public TableColumn<Appointment, LocalDateTime> appointmentEndColumn;
    public TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    public TableColumn<Appointment, Integer> appointmentUserIDColumn;
    public TableColumn<Appointment, Integer> appointmentContactIDColumn;
    public Button addAppointmentButton;
    public Button updateAppointmentButton;
    public Button deleteAppointmentButton;
    public Button logOutButton;
    public Button viewCustomersButton;
    public Button clearFilterButton;
    public RadioButton weekRadioButton;
    public RadioButton monthRadioButton;
    public ToggleGroup filterGroup;
    public ComboBox<String> monthBox;

    public static ObservableList<Appointment> appointments;
    public static Appointment selectedAppointment;
    public static boolean updatingAppointment;
    public static boolean checkedForAppointment;
    public ComboBox<String> appointmentReportBox;
    public ComboBox<String> monthReportBox;
    public Button viewTypeMonthReportButton;
    public ComboBox<String> contactReportBox;
    public Button viewContactReportButton;
    public ComboBox<String> countryReportBox;
    public Button viewCountryReportButton;

    public static ObservableList<Customer> reportCustomers;
    public static boolean viewingCustomerReport;


    /** <strong>This function utilizes a Lambda expression per project requirements.</strong>
     * initialize builds the appointments tableview and fills with database data. Comboboxes are also
     * populated with their respective required data to be used for filtering table data. Initialize is also
     * responsible for calling checkForUpcomingAppointment(), which alerts the user of any upcoming appointments.
     * <strong>Lambda expressions are used to fill ObservableLists for Comboboxes. These lambda expressions
     * simplify the loop and improve readability.</strong>
     */
    public void initialize() {
        System.out.println("Appointment view initialized.");

        appointments = AppointmentQuery.getAllAppointments();
        updateAppointmentsView();

        if(!checkedForAppointment)
        {
            checkForUpcomingAppointment();
            checkedForAppointment = true;
        }

        filterGroup = new ToggleGroup();
        weekRadioButton.setToggleGroup(filterGroup);
        monthRadioButton.setToggleGroup(filterGroup);
        setMonthBoxes();

        ObservableList<String> types = AppointmentQuery.getAllAppointmentTypes();
        appointmentReportBox.setItems(types);

        ObservableList<Contact> contacts = ContactQuery.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();

        ObservableList<Country> countries = CountryQuery.getAllCountries();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        
        contacts.forEach(contact -> contactNames.add(contact.getName()));
        countries.forEach(country -> countryNames.add(country.getCountry()));

        contactReportBox.setItems(contactNames);
        countryReportBox.setItems(countryNames);

        viewingCustomerReport = false;
        reportCustomers = FXCollections.observableArrayList();
    }


    /**
     * onAddAppointment routes the user to appointmentForm upon request.
     * @param actionEvent the event triggerd by the button.
     * @throws IOException the exception thrown in case of page loading error.
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        updatingAppointment = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointment_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

    /**
     * onUpdateAppointment grabs the selected appointment from the tableview and routes the user
     * to the appointmentForm along with the appointment data. If no appointment is selected, an error appears.
     * @param actionEvent the event triggered by the update appointment button.
     * @throws IOException the exception thrown in case of a page laoding error.
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null)
        {
            String title = "Error";
            String header = "No appointment selected.";
            String content = "An appointment must be selected in order to be updated.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }

        updatingAppointment = true;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointment_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Update Appointment");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

    /**
     * onDeleteAppointment checks if an appointment is selected, and displays error if not. Them, the appointment
     * is deleted. A message displaying the canceled appointment info is displayed. The view is updated.
     */
    public void onDeleteAppointment() {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        String title;
        String header;
        String content;

        if (selectedAppointment == null)
        {
            title = "Error";
            header = "No appointment selected.";
            content = "An appointment must be selected in order to be deleted.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }


        AppointmentQuery.deleteAppointment(selectedAppointment);

        appointments = AppointmentQuery.getAllAppointments();
        updateAppointmentsView();

        title = "Delete confirmed.";
        header = "Appointment " + selectedAppointment.getID() + " was deleted.";
        content = "Appointment type was " + selectedAppointment.getType() + ".";
        FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
        message.display();
    }

    /**
     * onLogOut routes the user to the login page.
     * @param actionEvent the event triggered by the log out button.
     * @throws IOException the exception thrown in case of a page loading error.
     */
    public void onLogOut(ActionEvent actionEvent) throws IOException {
        checkedForAppointment = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/log_in_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    /**
     * onViewCustomers routes the user to the customers view.
     * @param actionEvent the event triggerd by the View Customers button.
     * @throws IOException the exception thrown in case of a page loading error.
     */
    public void onViewCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 959,461));
        stage.show();
    }

    /**
     * updateAppointmentsView takes the member variable appointments and converts
     * all start and end times to system time. The appointments are then displayed in the view.
     */
    private void updateAppointmentsView()
    {
        for (Appointment appointment : appointments)
        {
            LocalDateTime UTCStart = appointment.getStart();
            LocalDateTime UTCEnd = appointment.getEnd();

            appointment.setStart(Time.UTCToSystem(UTCStart));
            appointment.setEnd(Time.UTCToSystem(UTCEnd));
        }

        appointmentsTable.setItems(appointments);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        appointmentContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
    }

    /**
     * checkForUpcomingAppointments queries the database for appointments that start
     * within 15 minutes of login. A message is displayed with the result.
     */
    private void checkForUpcomingAppointment()
    {

        String title;
        String header;
        String content;
        for (Appointment appointment : appointments)
        {
            LocalDateTime start = appointment.getStart();
            LocalDateTime now = LocalDateTime.now();
            boolean isWithin15Minutes = start.isAfter(now) && (now.plusMinutes(15).isEqual(start) || now.plusMinutes(15).isAfter(start));

            if (isWithin15Minutes)
            {
                title = "Alert: ";
                header = "User " + appointment.getUserID() + " has an upcoming appointment.";
                content = "Appointment " + appointment.getID() + " starts at " + appointment.getStart() + ".";
                FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.WARNING);
                message.display();
                return;
            }
        }

        title = "Information:";
        header = "You have no upcoming appointments";
        content = "";
        FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.INFORMATION);
        message.display();
    }

    /**
     * onSelectWeekRadio gets appointments for the current week from the database and updates
     * the appointment view. The combobox for the other filter is disabled.
     */
    public void onSelectWeekRadio() {
        appointments = AppointmentQuery.getAllAppointmentsForThisWeek();
        updateAppointmentsView();
        monthBox.setVisible(false);
        monthBox.setValue(null);
        monthBox.setDisable(true);
    }

    /**
     * onSelectMonthRadio enables the combobox for filtering by month.
     */
    public void onSelectMonthRadio() {

        monthBox.setVisible(true);
        monthBox.setDisable(false);
        monthBox.setValue(null);
    }

    /**
     * onClearFilter updates the appointment view with all appointments, undoing any filter.
     */
    public void onClearFilter() {
        filterGroup.selectToggle(null);

        appointments = AppointmentQuery.getAllAppointments();
        updateAppointmentsView();

        monthBox.setDisable(true);
        monthBox.setVisible(false);
        monthBox.setValue(null);
    }

    /**
     * onSelectMonthBox filters the appointment view based on the selected month.
     */
    public void onSelectMonthBox() {
        String month = monthBox.getValue();
        System.out.println(month);

        if (monthBox.getValue() == null)
        {
            System.out.println("No month value was selected. Skipping query.");
            return;
        }
        appointments = AppointmentQuery.getAllAppointmentsForMonth(month);
        updateAppointmentsView();

    }

    /**
     * onViewTypeMonthReport displays the appointment report based on appointment type and month selected
     * in the appropriate comboboxes.
     */
    public void onViewTypeMonthReport()
    {
        String type = appointmentReportBox.getValue();
        String month = monthReportBox.getValue();

        String title;
        String header;
        String content;

        if (type == null || month == null)
        {
            title = "Error";
            header = "Value not selected:";
            content = "To view this report, you must select both Type and Month above.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }
        appointments = AppointmentQuery.getAllAppointmentsForTypeAndMonth(type, month);
        updateAppointmentsView();

        title = "Report";
        header = "Total appointments for selected Type and Month: " +  appointments.size();
        content = "See table for information on reported appointments.";
        FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.INFORMATION);
        message.display();

    }

    /**
     * onViewContactReport displays the schedule for the selected contact.
     */
    public void onViewContactReport()
    {
        String contactName = contactReportBox.getValue();

        String title;
        String header;
        String content;

        if (contactName == null)
        {
            title = "Error";
            header = "Value not selected:";
            content = "To view this report, you must select a Contact above.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }

        int contactID = ContactQuery.getContactIDFromName(contactName);
        appointments = AppointmentQuery.getAllAppointmentsForContactID(contactID);
        updateAppointmentsView();

        title = "Report";
        header = "Total appointments for selected Contact: " +  appointments.size();
        content = "The schedule for this contact is displayed in the table.";
        FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.INFORMATION);
        message.display();
    }

    /**
     * setMonthBoxes populates the month comboboxes.
     */
    public void setMonthBoxes()
    {
        ObservableList<String> months = FXCollections.observableArrayList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        monthBox.setItems(months);
        monthReportBox.setItems(months);
    }

    /**
     * onViewCountryReportBox routes the user to the Customer view and displays the report showing
     * the total number of customers for the selected country.
     * @param actionEvent the event triggerd by the button.
     * @throws IOException the exception thrown in case of page loading error.
     */
    public void onViewCountryReport(ActionEvent actionEvent) throws IOException {
        String countryName = countryReportBox.getValue();

        String title;
        String header;
        String content;

        if (countryName == null)
        {
            title = "Error";
            header = "Value not selected:";
            content = "To view this report, you must select a Country above.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }

        int countryID = CountryQuery.getCountryFromName(countryName).getID();
        ObservableList<Customer> customers = CustomerQuery.getAllCustomersForCountry(countryID);

        if (customers != null)
        {
            viewingCustomerReport = true;
            reportCustomers = customers;
            onViewCustomers(actionEvent);
            title = "Report";
            header = "Total customers for  " +  countryName + ": " + customers.size();
            content = "All customers for " + countryName + " are displayed in the table.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.INFORMATION);
            message.display();
        }

        else
        {
            title = "Report";
            header = "There are no registered customers from + " + countryName + ".";
            content = "";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.INFORMATION);
            message.display();
        }
    }
}
