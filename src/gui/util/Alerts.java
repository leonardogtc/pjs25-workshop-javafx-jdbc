package gui.util;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

    /**
     * Shows an alert with the specified title, header, content, and type.
     *
     * @param title   The title of the alert.
     * @param header  The header text of the alert.
     * @param content The content text of the alert.
     * @param type    The type of the alert (e.g., INFORMATION, WARNING, ERROR).
     */
    public static void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    /**
     * Shows a confirmation dialog with the specified title, header, and content.
     *
     * @param title   The title of the confirmation dialog.
     * @param header  The header text of the confirmation dialog.
     * @param content The content text of the confirmation dialog.
     * @return An Optional containing the ButtonType that was clicked, or empty if the dialog was closed without a selection.
     */
    public static Optional<ButtonType> showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
        
}
