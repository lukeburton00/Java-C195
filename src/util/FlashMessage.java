package util;

import javafx.scene.control.Alert;

public class FlashMessage
{
    public String mTitle;
    public String mHeader;
    public String mContent;
    public Alert mMessage;

    public FlashMessage(String title, String header, String content, Alert.AlertType type)
    {
        mTitle = title;
        mHeader = header;
        mContent = content;
        mMessage = new Alert(type);

        mMessage.setTitle(title);
        mMessage.setHeaderText(header);
        mMessage.setContentText(content);
    }

    public void display()
    {
        mMessage.showAndWait();
    }

}
