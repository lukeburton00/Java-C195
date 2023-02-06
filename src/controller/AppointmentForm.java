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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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
    public static int appointmentID;

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

        int id = AppointmentQuery.getCurrentMaxID();
        System.out.println(id);

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

        LocalTime firstTimeSlot = LocalTime.parse("08:00");
        LocalTime lastTimeSlot = LocalTime.parse("22:00");

        timeSlots = FXCollections.observableArrayList();
        do
        {
            timeSlots.add(String.valueOf(firstTimeSlot));
            firstTimeSlot = firstTimeSlot.plusMinutes(15);

        }
        while (firstTimeSlot.isBefore(lastTimeSlot));

        startTimeBox.setItems(timeSlots);
        endTimeBox.setItems(timeSlots);

        if(!Appointments.updatingAppointment)
        {
            idField.setText(String.valueOf(id + 1));
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

        if (Appointments.updatingAppointment)
        {
            AppointmentQuery.deleteAppointment(Appointments.selectedAppointment);
        }

        AppointmentQuery.addAppointment(appointment);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 959,491));
        stage.show();
    }
}
