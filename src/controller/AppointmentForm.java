package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Contact;
import util.ContactQuery;

import java.io.IOException;
import java.util.Objects;

public class AppointmentForm {
    public ObservableList<String> timeSlots;
    ObservableList<Contact> contacts;
    public ObservableList<String> contactNames;
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

    public void initialize()
    {
        try {
            System.out.println("Appointment form initialized.");
            contacts = ContactQuery.getAllContacts();
            contactNames = FXCollections.observableArrayList();
            String name;
            for (int i = 0; i < contacts.size(); i++) {
                name = contacts.get(i).getName();
                System.out.println(name);
                contactNames.add(name);
            }

            contactBox.setItems(contactNames);
        }
        catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }


    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 959,491));
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) {
    }
}
