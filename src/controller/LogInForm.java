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
import org.w3c.dom.Text;
import util.FlashMessage;
import util.UserQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

public class LogInForm {
    public TextField userNameField;
    public PasswordField passwordField;
    public Button signInButton;
    public Label locationLabel;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label locationMarkerLabel;
    String systemLanguage;

    public void initialize()
    {
        System.out.println("Login form initialized.");

        locationLabel.setText(String.valueOf(ZoneId.systemDefault()));
        Locale systemLocale = Locale.getDefault();
        systemLanguage = systemLocale.getLanguage();

        if (systemLanguage.equals("fr"))
        {
            usernameLabel.setText("Nom d'utilisateur: ");
            passwordLabel.setText("Mot de passe: ");
            locationMarkerLabel.setText("Localisation: ");
            signInButton.setText("Se Connecter");
        }

        else
        {
            usernameLabel.setText("Username: ");
            passwordLabel.setText("Password: ");
            locationMarkerLabel.setText("Location: ");
            signInButton.setText("Sign In");
        }
    }

    public void onSignIn(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println("Sign in button pressed.");

        if (UserQuery.authenticate(userNameField.getText(), passwordField.getText())) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Appointments");
            stage.setScene(new Scene(root, 959, 626));
            stage.show();
        } else {
            String title;
            String header;
            String content;
            if (systemLanguage.equals("fr"))
            {
                title = "Erreur";
                header = "Utilisateur non trouvé";
                content = "Nom d'utilisateur ou mot de passe incorrect.";
            }

            else
            {
                title = "Error";
                header = "User not found";
                content = "Username or password incorrect.";
            }
            System.out.println("User not found.");
            FlashMessage message = new FlashMessage(title, header, content, Alert.AlertType.ERROR);
            message.display();
        }
    }
}
