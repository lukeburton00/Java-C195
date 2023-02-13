package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import util.AppointmentQuery;
import util.ContactQuery;
import util.FlashMessage;
import util.Time;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

public class AppointmentForm {
    public ObservableList<String> timeSlots;
    public ObservableList<Contact> contacts;
    public ObservableList<String> contactNames;
    public ObservableList<TextField> textFields;
    public ObservableList<DatePicker> datePickers;
    public ObservableList<ComboBox<String>> comboBoxes;
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField titleField;
    public DatePicker startDatePicker;
    public ComboBox<String> startTimeBox;
    public DatePicker endDatePicker;
    public ComboBox<String> endTimeBox;
    public TextField customerIDField;
    public ComboBox<String> contactBox;
    public TextField userIDField;

    public void initialize()
    {
        textFields = FXCollections.observableArrayList();
        datePickers = FXCollections.observableArrayList();
        comboBoxes = FXCollections.observableArrayList();

        textFields.add(idField);
        textFields.add(descriptionField);
        textFields.add(locationField);
        textFields.add(typeField);
        textFields.add(titleField);
        textFields.add(customerIDField);
        textFields.add(userIDField);

        datePickers.add(startDatePicker);
        datePickers.add(endDatePicker);

        comboBoxes.add(startTimeBox);
        comboBoxes.add(endTimeBox);
        comboBoxes.add(contactBox);


        try
        {
            System.out.println("Appointment form initialized.");

            contacts = ContactQuery.getAllContacts();
            contactNames = FXCollections.observableArrayList();
            String name;
            for (Contact contact : contacts) {
                name = contact.getName();
                contactNames.add(name);
            }
            contactBox.setItems(contactNames);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        LocalTime firstTimeSlot = LocalTime.parse("00:00");
        LocalTime lastTimeSlot = LocalTime.parse("23:00");

        timeSlots = FXCollections.observableArrayList();
        do
        {
            timeSlots.add(String.valueOf(firstTimeSlot));
            firstTimeSlot = firstTimeSlot.plusMinutes(15);

        }
        while (firstTimeSlot.isBefore(lastTimeSlot));

        startTimeBox.setItems(timeSlots);
        endTimeBox.setItems(timeSlots);

        if(!Appointments.updatingAppointment) {
            idField.setText(String.valueOf(AppointmentQuery.getCurrentMaxID() + 1));
            return;
        }
        Appointment appointment = Appointments.selectedAppointment;

        idField.setText(String.valueOf(appointment.getID()));
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());
        titleField.setText(appointment.getTitle());
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        endDatePicker.setValue(appointment.getEnd().toLocalDate());
        startTimeBox.setValue(String.valueOf(appointment.getStart().toLocalTime()));
        endTimeBox.setValue(String.valueOf(appointment.getEnd().toLocalTime()));
        customerIDField.setText(String.valueOf(appointment.getCustomerID()));
        contactBox.setValue(ContactQuery.getContactNameFromID(appointment.getContactID()));
        userIDField.setText(String.valueOf(appointment.getUserID()));
    }


    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 959,491));
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        for (TextField field : textFields)
        {
            if (field.getText().isEmpty())
            {
                String title = "Error";
                String header = "Field " + field.getId() + " is empty.";
                String content = "All fields are required.";
                FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return;
            }
        }

        for (DatePicker picker : datePickers)
        {
            if (picker.getValue() == null)
            {
                String title = "Error";
                String header = "Field " + picker.getId() + " is empty.";
                String content = "All fields are required.";
                FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return;
            }
        }

        for (ComboBox<String> box : comboBoxes)
        {
            if (box.getValue() == null)
            {
                String title = "Error";
                String header = "Field " + box.getId() + " is empty.";
                String content = "All fields are required.";
                FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return;
            }
        }

        int ID = Integer.parseInt(idField.getText());
        int customerID = Integer.parseInt(customerIDField.getText());
        int userID = Integer.parseInt(userIDField.getText());
        int contactID = ContactQuery.getContactIDFromName(contactBox.getValue());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        LocalDateTime start = Time.systemToUTC(startDatePicker.getValue().atTime(LocalTime.parse(startTimeBox.getValue())));
        LocalDateTime end = Time.systemToUTC(endDatePicker.getValue().atTime(LocalTime.parse(endTimeBox.getValue())));
        Appointment appointment = new Appointment(ID, title, description, location, type, start, end, customerID, userID, contactID);

        System.out.println(start);
        System.out.println(end);



        if (!validateDates(start, end, ID))
        {
           return;
        }

        if (Appointments.updatingAppointment)
        {
            // Because the selection model grabs data directly from the view where times are in
            // the system time zone, we must convert those values to UTC in order to be compared with our
            // newly created appointment object.
            LocalDateTime adjustedStart = Time.systemToUTC(Appointments.selectedAppointment.getStart());
            LocalDateTime adjustedEnd = Time.systemToUTC(Appointments.selectedAppointment.getEnd());

            Appointments.selectedAppointment.setStart(adjustedStart);
            Appointments.selectedAppointment.setEnd(adjustedEnd);

            // Appointment::equals has been overridden to compare all fields of two appointments
            if (appointment.equals(Appointments.selectedAppointment))
            {
                // This variable named messageTitle to resolve earlier appointment field named title
                String messageTitle = "Alert: ";
                String header = "No change";
                String content = "No fields in this appointment were changed.";
                FlashMessage message = new FlashMessage(messageTitle, header, content, Alert.AlertType.INFORMATION);
                message.display();
                return;
            }

            AppointmentQuery.deleteAppointment(Appointments.selectedAppointment);
        }

        AppointmentQuery.addAppointment(appointment);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 959,491));
        stage.show();
    }

    private boolean validateDates(LocalDateTime start, LocalDateTime end, int appointmentID)
    {
        FlashMessage message;
        String title = "Error";
        String header = "Appointment time error: ";
        String content;
        LocalDateTime startEST = Time.UTCtoEST(start);
        LocalDateTime endEST = Time.UTCtoEST(end);

        LocalDateTime now = LocalDateTime.now();

        LocalTime startESTHourMinutes = startEST.toLocalTime();
        LocalTime endESTHourMinutes = endEST.toLocalTime();

        LocalTime ESTBusinessHoursStart = LocalTime.parse("08:00");
        LocalTime ESTBusinessHoursEnd = LocalTime.parse("22:00");

        if (start.isAfter(end) || start.isEqual(end))
        {
            content = "Appointment start time must be before end time.";
            message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return false;
        }

        if (startEST.isBefore(now) || endEST.isBefore(now))
        {
            content = "Appointment must be scheduled for the future.";
            message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return false;
        }

        for (Appointment appointment : AppointmentQuery.getAllAppointments())
        {
            LocalDateTime appStart = appointment.getStart();
            LocalDateTime appEnd = appointment.getEnd();
            boolean isCurrentAppointment = appointmentID == appointment.getID();

            boolean isStartOverlapping = (start.isAfter(appStart) || start.isEqual(appStart)) && (start.isBefore(appEnd) || start.isEqual(appEnd));
            boolean isEndOverlapping = (end.isAfter(appStart) || end.isEqual(appStart)) && (end.isBefore(appEnd) || end.isEqual(appEnd));
            boolean hasAppointmentInside = (appStart.isAfter(start) && appEnd.isBefore(end));


            boolean isWithinBusinessHours = (startESTHourMinutes.isAfter(ESTBusinessHoursStart.minusMinutes(1)) && (endESTHourMinutes.isBefore(ESTBusinessHoursEnd.plusMinutes(1))));

            if (!isCurrentAppointment && isStartOverlapping)
            {
                content = "Appointment start falls within an existing appointment time.";
                message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return false;
            }

            if (!isCurrentAppointment && isEndOverlapping)
            {
                content = "Appointment end falls within an existing appointment time.";
                message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return false;
            }

            if (!isCurrentAppointment && hasAppointmentInside)
            {
                content = "An existing appointment falls within this one.";
                message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return false;
            }

            if(!isWithinBusinessHours)
            {
                content = "Appointment time does not fall within business hours (8:00am to 10:00pm EST)";
                message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
                message.display();
                return false;
            }
        }

        return true;
    }
}
