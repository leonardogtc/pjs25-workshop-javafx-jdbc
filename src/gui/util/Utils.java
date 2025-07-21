package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
    /**
     * Returns the current stage from an ActionEvent.
     *
     * @param event the ActionEvent from which to get the current stage
     * @return the current Stage
     */
    public static Stage currentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
