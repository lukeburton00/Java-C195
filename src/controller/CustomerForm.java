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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import util.CountryQuery;
import util.CustomerQuery;
import util.DivisionQuery;

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

    public void initialize()
    {
        System.out.println("Customer form initialized.");

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
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 959,461));
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) {
    }

    public void onSelectCountry(ActionEvent actionEvent) {
        String countryName = countryComboBox.getSelectionModel().getSelectedItem();


        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        for (FirstLevelDivision division : DivisionQuery.getAllDivisionsForCountry(CountryQuery.getCountryFromName(countryName)))
        {
            divisionNames.add(division.getDivision());
        }

        divisionComboBox.setItems(divisionNames);
    }
}
