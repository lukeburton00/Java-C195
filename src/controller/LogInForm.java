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
import util.FlashMessage;
import util.UserQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LogInForm {
    public ObservableList<String> language = FXCollections.observableArrayList("English", "French");
    public TextField userIdField;
    public TextField passwordField;
    public Button signInButton;
    public ChoiceBox<String> languageSelectionBox;
    public Label locationLabel;

    public void initialize()
    {
        System.out.println("Login form initialized.");
        languageSelectionBox.setValue("English");
        languageSelectionBox.setItems(language);
    }

    public void onSignIn(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println("Sign in button pressed.");

        if (UserQuery.authenticate(userIdField.getText(), passwordField.getText()))
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main_form.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root, 852,820));
            stage.show();
        }

        else
        {
            System.out.println("User not found.");
            FlashMessage message = new FlashMessage("Error", "User not found", "Username or Password incorrect.", Alert.AlertType.ERROR);
            message.display();
        }


    }
}
