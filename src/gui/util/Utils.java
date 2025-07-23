package gui.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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

    /**
     * Attempts to parse a string to an Integer.
     *
     * @param str the string to parse
     * @return the parsed Integer, or null if parsing fails
     */
    public static Integer tryParseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null; // or handle the error as needed
        }
    }

    /**
     * Formats a TableColumn to display Date objects in a specified format.
     *
     * @param TableColumn the TableColumn to format
     * @param format      the date format string
     * @param <T>         the type of the items in the TableColumn
     */
    public static <T> void formatTableColumn(TableColumn<T, Date> TableColumn, String format) {
        TableColumn.setCellFactory(column -> {
            TableCell<T, Date> cell = new TableCell<T, Date>() {
                private SimpleDateFormat sdf = new SimpleDateFormat(format);

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(sdf.format(item));
                    }
                }
            };
            return cell;
        });

    }

    /**
     * Formats a TableColumn to display Double values with a specified number of decimal places.
     *
     * @param TableColumn   the TableColumn to format
     * @param decimalPlaces the number of decimal places to display
     * @param <T>           the type of the items in the TableColumn
     */
    public static <T> void formatTableColumnDouble(TableColumn<T, Double> TableColumn, int decimalPlaces) {
        TableColumn.setCellFactory(column -> {
            TableCell<T, Double> cell = new TableCell<T, Double>() {

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        Locale.setDefault(Locale.US); // Ensure consistent formatting
                        setText(String.format("%." + decimalPlaces + "f", item));
                    }
                }
            };
            return cell;
        });
    }
}
