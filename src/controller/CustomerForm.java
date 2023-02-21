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
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import util.*;

import java.io.IOException;
import java.util.Objects;

public class CustomerForm {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField nameField;
    public ComboBox<String> countryComboBox;
    public ComboBox<String> divisionComboBox;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public ObservableList<ComboBox<String>> comboBoxes;
    public ObservableList<TextField> textFields;

    public void initialize()
    {
        System.out.println("Customer form initialized.");

        textFields = FXCollections.observableArrayList();
        comboBoxes = FXCollections.observableArrayList();

        textFields.add(idField);
        textFields.add(nameField);
        textFields.add(addressField);
        textFields.add(postalField);
        textFields.add(phoneField);
        comboBoxes.add(countryComboBox);
        comboBoxes.add(divisionComboBox);

        ObservableList<Country> countries = CountryQuery.getAllCountries();
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        for (Country country : countries)
        {
            countryNames.add(country.getCountry());
        }

        countryComboBox.setItems(countryNames);

        //Using a single-item list here so that the first combobox item is our default string.
        ObservableList<String> divisionDefault = FXCollections.observableArrayList();
        divisionDefault.add("Select a country.");
        divisionComboBox.setItems(divisionDefault);

        if (!Customers.updatingCustomer)
        {
            idField.setText(String.valueOf(CustomerQuery.getCurrentMaxID() + 1));
            return;
        }

        Customer customer = Customers.selectedCustomer;
        idField.setText(String.valueOf(customer.getID()));
        nameField.setText(customer.getName());
        addressField.setText(customer.getAddress());
        postalField.setText(customer.getPostalCode());
        phoneField.setText(customer.getPhone());
        countryComboBox.setValue(CountryQuery.getCountryFromDivisionID(customer.getDivisionID()).getCountry());
        divisionComboBox.setValue(DivisionQuery.getDivisionFromID(customer.getDivisionID()).getDivision());
        onSelectCountry();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 959,461));
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

        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String address = addressField.getText();
        String postal = postalField.getText();
        String phone = phoneField.getText();
        int divisionID = DivisionQuery.getDivisionFromName(divisionComboBox.getValue()).getID();



        if (Customers.updatingCustomer)
        {
            CustomerQuery.deleteCustomer(Customers.selectedCustomer);
        }

        Customer customer = new Customer(id, divisionID, name, address, postal, phone);
        CustomerQuery.addCustomer(customer);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 959,461));
        stage.show();

    }

    public void onSelectCountry() {
        String countryName = countryComboBox.getValue();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> divisions =  DivisionQuery.getAllDivisionsForCountry(CountryQuery.getCountryFromName(countryName));

       divisions.forEach( division -> divisionNames.add(division.getDivision()));


        divisionComboBox.setItems(divisionNames);
    }
}
