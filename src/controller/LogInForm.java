package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LogInForm {
    public ObservableList<String> language = FXCollections.observableArrayList("English", "French");
    public TextField userIdField;
    public TextField passwordField;
    public Button signInButton;
    public ChoiceBox<String> languageSelectionBox;
    public Label locationLabel;

    public void onSignIn(ActionEvent actionEvent)
    {
        System.out.println("Sign in button pressed.");
    }

    public void initialize()
    {
        System.out.println("Login form initialized.");
        languageSelectionBox.setValue("English");
        languageSelectionBox.setItems(language);
    }
}
