package controller;

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

import java.io.IOException;
import java.util.Objects;

public class CustomerForm {
    public Button cancelButton;
    public Button saveButton;
    public TextField idField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField titleField;
    public TextField nameField;
    public ComboBox<Country> divisionComboBox;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main_form.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 959, 626));
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) {
    }
}
