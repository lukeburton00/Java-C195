package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import util.AppointmentQuery;
import util.FlashMessage;
import util.Time;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    public static Appointment selectedAppointment;
    public static boolean updatingAppointment;
    public static boolean checkedForAppointment;

    public void initialize() throws SQLException {
        System.out.println("Appointment view initialized.");

        updateAppointmentsView();

        if(!checkedForAppointment)
        {
            checkForUpcomingAppointment();
            checkedForAppointment = true;
        }
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

        appointments = AppointmentQuery.getAllAppointments();

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
                title = "Alert!";
                header = "You have an upcoming appointment.";
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
}
