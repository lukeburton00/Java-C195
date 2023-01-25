package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import util.AppointmentQuery;
import util.CustomerQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Objects;

public class MainForm
{
    public ObservableList<Customer> customers;
    public TableView<Customer> customersTable;
    public TableColumn<Customer, Integer> customerIDColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerPostalColumn;
    public TableColumn<Customer, String> customerPhoneColumn;
    public TableColumn<Customer, Integer> customerDivisionColumn;

    public ObservableList<Appointment> appointments;
    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn<Appointment, String> appointmentTitleColumn;
    public TableColumn<Appointment, String> appointmentDescriptionColumn;
    public TableColumn<Appointment, String> appointmentLocationColumn;
    public TableColumn<Appointment, String> appointmentTypeColumn;
    public TableColumn<Appointment, Date> appointmentStartColumn;
    public TableColumn<Appointment, Date> appointmentEndColumn;
    public TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    public TableColumn<Appointment, Integer> appointmentUserIDColumn;
    public TableColumn<Appointment, Integer> appointmentContactIDColumn;
    public Button addCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button addAppointmentButton;
    public Button updateAppointmentButton;
    public Button deleteAppointmentButton;

    public void initialize() throws SQLException {
        System.out.println("Main form initialized.");

        customers = CustomerQuery.getAllCustomers();

        customersTable.setItems(customers);

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));

        appointments = AppointmentQuery.getAllAppointments();

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

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Update Customer");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointment_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointment_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Update Appointment");
        stage.setScene(new Scene(root, 959,626));
        stage.show();
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }
}
