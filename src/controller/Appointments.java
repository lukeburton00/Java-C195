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
import util.AppointmentQuery;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class Appointments
{
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
    public Button addAppointmentButton;
    public Button updateAppointmentButton;
    public Button deleteAppointmentButton;
    public Button logOutButton;
    public Button viewCustomersButton;

    public void initialize() throws SQLException {
        System.out.println("Appointment view initialized.");

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

    public void onLogOut(ActionEvent actionEvent) throws IOException {
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
}
