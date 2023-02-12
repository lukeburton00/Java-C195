package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class FlashMessage
{
    private Alert mMessage;

    public FlashMessage(String title, String header, String content, Alert.AlertType type)
    {
        mMessage = new Alert(type);
        mMessage.setTitle(title);
        mMessage.setHeaderText(header);
        mMessage.setContentText(content);
    }

    public void display()
    {
        mMessage.showAndWait();
    }

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
