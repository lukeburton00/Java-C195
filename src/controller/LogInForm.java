package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.FlashMessage;
import util.Time;
import util.UserQuery;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

/**
 * LogInForm controller handles log in attempts and routes the user to the appointments view
 * upon successful log in.
 */
public class LogInForm {
    public TextField userNameField;
    public PasswordField passwordField;
    public Button signInButton;
    public Label locationLabel;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label locationMarkerLabel;
    String systemLanguage;

    /**
     * initialize has the main responsibility of translating the interface based on the user
     * system language settings.
     */
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

        System.out.println(Time.systemToUTC(LocalDateTime.now()));
    }

    /**
     * onSignIn validates the username and password fields using a database query. If the validation is
     * successful, the user is routed to the appointments view. If not, an error message is displayed.
     * Either way, the result is logged to login_activity.txt
     * @param actionEvent the event triggered by the sign in button.
     * @throws IOException the exception thrown in case of page loading error.
     */
    public void onSignIn(ActionEvent actionEvent) throws IOException
    {
        if (UserQuery.authenticate(userNameField.getText(), passwordField.getText())) {
            try {
                System.out.println("Attempting to write to file...\n");
                FileWriter writer = new FileWriter("login_activity.txt", true);
                writer.write("Log-in attempt SUCCESS: " + LocalDateTime.now() + " Local Time.\n");
                writer.close();
            }

            catch (IOException e)
            {
                System.out.println("File write error occurred: ");
                e.printStackTrace();
            }



            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointments.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Appointments");
            stage.setScene(new Scene(root, 959, 710));
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

            try {
                FileWriter writer = new FileWriter("login_activity.txt", true);
                writer.write("Log-in attempt FAILED: " + LocalDateTime.now() + " Local Time.\n");
                writer.close();
            }

            catch (IOException e)
            {
                System.out.println("File write error occurred: ");
                e.printStackTrace();
            }
        }
    }
}
