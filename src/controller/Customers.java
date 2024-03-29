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
import model.Customer;
import util.AppointmentQuery;
import util.CustomerQuery;
import util.FlashMessage;

import java.io.IOException;
import java.util.Objects;

/**
 * Customers controller is responsible for displaying all customers and routing to
 * the form used for adding and updating customers. This controller also serves to display the
 * results of the additional defined report per project requirements.
 */
public class Customers
{
    public static ObservableList<Customer> customers;
    public TableView<Customer> customersTable;
    public TableColumn<Customer, Integer> customerIDColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerPostalColumn;
    public TableColumn<Customer, String> customerPhoneColumn;
    public TableColumn<Customer, Integer> customerDivisionColumn;

    public Button addCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button backButton;

    public static Customer selectedCustomer;
    public static boolean updatingCustomer;
    public Button clearReportButton;

    /**
     * initialize fills the customers tableview with customers queried from the database,
     * whether that be the full roster or the result of the report.
     */
    public void initialize() {
        System.out.println("Customer view initialized.");

        if (Appointments.viewingCustomerReport)
        {
            customers = Appointments.reportCustomers;

            clearReportButton.setVisible(true);
            clearReportButton.setDisable(false);
        }

        else
        {
            customers = CustomerQuery.getAllCustomers();
            clearReportButton.setVisible(false);
            clearReportButton.setDisable(true);
        }

        updateCustomersView();
    }

    /**
     * onAddCustomer routes the user to the customer form.
     * @param actionEvent the event triggered by the add customer button.
     * @throws IOException the exception thrown in case of page loading error.
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        updatingCustomer = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    /**
     * onUpdateCustomer routes the user to the customer form with the data from the selected customer.
     * @param actionEvent the event triggered by the update customer button.
     * @throws IOException the exception thrown in case of page loading error.
     */
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        updatingCustomer = true;
        selectedCustomer =  customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null)
        {
            String title = "Error";
            String header = "No customer selected.";
            String content = "A customer must be selected in order to be updated.";
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Update Customer");
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    /**
     * onDeleteCustomer asks for confirmation, then deletes the selected customer along with
     * all associated appointments.
     */
    public void onDeleteCustomer() {
        selectedCustomer =  customersTable.getSelectionModel().getSelectedItem();

        String title = "Confirmation";
        String header = "Are you sure you would like to delete this customer?";
        String content = "All appointments associated with this customer will be deleted.";

        FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.CONFIRMATION);

        if (selectedCustomer == null)
        {
            title = "Error";
            header = "No customer selected.";
            content = "A customer must be selected in order to be deleted.";
            message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
            return;
        }

        if (message.confirm())
        {
            ObservableList<Appointment> associatedAppointments = AppointmentQuery.getAllAppointmentsForCustomer(selectedCustomer);

            if (associatedAppointments != null)
            {
                for (Appointment appointment : associatedAppointments)
                {
                    AppointmentQuery.deleteAppointment(appointment);
                }
                CustomerQuery.deleteCustomer(selectedCustomer);
            }
        }
        customers = CustomerQuery.getAllCustomers();
        updateCustomersView();
    }

    /**
     * onBack routes the user to the appointments view.
     * @param actionEvent the event triggered by the back button.
     * @throws IOException the exception thrown in case of a page loading error.
     */
    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 959, 710));
        stage.show();
    }

    /**
     * updateCustomersView fills the customers tableview based on the customers member variable.
     */
    public void updateCustomersView()
    {
        customersTable.setItems(customers);
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
    }

    /**
     * onClearReport cancels the filter created by the report run on the appointments view.
     */
    public void onClearReport()
    {
        customers = CustomerQuery.getAllCustomers();
        updateCustomersView();
        clearReportButton.setVisible(false);
        clearReportButton.setDisable(true);
    }
}
