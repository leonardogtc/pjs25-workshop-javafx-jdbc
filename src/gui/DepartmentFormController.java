package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

    @FXML
    private TextField departmentIDField;

    @FXML
    private TextField departmentNameField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public void onSaveButtonAction() { System.out.println("onSaveButtonAction!"); }

    @FXML
    public void onCancelButtonAction() {
        System.out.println("onCancelButtonAction!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic here
        initializeNodes();
    }

    // Additional methods for handling form actions can be added here
    // For example, methods to save or cancel the form submission

    private void initializeNodes() {
        Constraints.setTextFieldInteger(departmentIDField);
        Constraints.setTextFieldMaxLength(departmentNameField, 30);
    }

}
