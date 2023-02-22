package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * FlashMessage is a thin wrapper around the JavaFX Alert class. This makes messages easier to work with.
 */
public class FlashMessage
{
    private Alert mMessage;

    /**
     * Constructor creates an Alert and assigns its parameters while instantiating a wrapper FlashMessage.
     * @param title the alert title. String
     * @param header the alert header. String
     * @param content the alert content. String
     * @param type the alert type. AlertType
     */
    public FlashMessage(String title, String header, String content, Alert.AlertType type)
    {
        mMessage = new Alert(type);
        mMessage.setTitle(title);
        mMessage.setHeaderText(header);
        mMessage.setContentText(content);
    }

    /**
     * display allows the developer to quickly display a created message
     */
    public void display()
    {
        mMessage.showAndWait();
    }

    /**
     * confirm wraps functionality for a confirmable alert.
     * @return true of confirmed, false otherwise
     */
    public boolean confirm()
    {
        if (mMessage.getAlertType() == Alert.AlertType.CONFIRMATION)
        {
            Optional<ButtonType> result = mMessage.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                return true;
            }

            return false;
        }

        System.out.println("Message is not confirmable.");
        return false;
    }

}
