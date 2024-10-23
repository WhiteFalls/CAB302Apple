package Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Popup {
    /**
     * Displays a popup message on the screen
     * @param message The message inside the popup window
     */
    public static void displayErrorPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an alert pop-up message
     * @param title The title of the alert message
     * @param message The message contained in the pop-up
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation popup on the screen
     * @param message The message to be shown in the popup
     * @return A boolean value based on the user's decision to confirm or deny the action
     */
    public static boolean displayConfirmPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            return true;
        }
        return false;
    }


}
