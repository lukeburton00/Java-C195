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
import util.AppointmentQuery;
import util.ContactQuery;
import util.FlashMessage;
import util.Time;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

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
    public Button viewReportsButton;
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
        String name;
        for (Contact contact : contacts) {
            name = contact.getName();
            contactNames.add(name);
        }
        contactReportBox.setItems(contactNames);

    }


    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        updatingAppointment = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointment_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

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

    public void onDeleteAppointment(ActionEvent actionEvent) {
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

    public void onLogOut(ActionEvent actionEvent) throws IOException {
        checkedForAppointment = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/log_in_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    public void onViewCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 959,461));
        stage.show();
    }

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

    public void onSelectWeekRadio(ActionEvent actionEvent) {
        appointments = AppointmentQuery.getAllAppointmentsForThisWeek();
        updateAppointmentsView();
        monthBox.setVisible(false);
        monthBox.setValue(null);
        monthBox.setDisable(true);
    }

    public void onSelectMonthRadio(ActionEvent actionEvent) {

        monthBox.setVisible(true);
        monthBox.setDisable(false);
        monthBox.setValue(null);
    }

    public void onClearFilter(ActionEvent actionEvent) {
        filterGroup.selectToggle(null);

        appointments = AppointmentQuery.getAllAppointments();
        updateAppointmentsView();

        monthBox.setDisable(true);
        monthBox.setVisible(false);
        monthBox.setValue(null);
    }

    public void onSelectMonthBox(ActionEvent actionEvent) {
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

    public void onViewTypeMonthReport(ActionEvent actionEvent)
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

    public void onViewContactReport(ActionEvent actionEvent)
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
        content = "See table for information on reported appointments.";
        FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.INFORMATION);
        message.display();
    }

    public void setMonthBoxes()
    {
        ObservableList<String> months = FXCollections.observableArrayList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        monthBox.setItems(months);
        monthReportBox.setItems(months);
    }
}
