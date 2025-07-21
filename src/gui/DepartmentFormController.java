package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

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
    public void onSaveButtonAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error saving Department", null, e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Gets the form data from the text fields and creates a Department object.
     *
     * @return a Department object with the data from the form
     */
    private Department getFormData() {
        Department obj = new Department();
        obj.setId(Utils.tryParseToInt(departmentIDField.getText()));
        obj.setName(departmentNameField.getText());
        return obj;
    }

    @FXML
    public void onCancelButtonAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    /**
     * Sets the Department entity for this controller.
     *
     * @param entity the Department entity to be set
     */
    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    /**
     * Sets the DepartmentService for this controller.
     *
     * @param service the DepartmentService to be used by this controller
     */
    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic here
        initializeNodes();
    }

    /**
     * Initializes the nodes in the form.
     * Sets constraints on the text fields and other UI elements.
     */
    private void initializeNodes() {
        Constraints.setTextFieldInteger(departmentIDField);
        Constraints.setTextFieldMaxLength(departmentNameField, 30);
    }

    /**
     * Updates the form data with the current entity's data.
     * This method should be called after setting the entity.
     */
    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        departmentIDField.setText(String.valueOf(entity.getId()));
        departmentNameField.setText(entity.getName());
    }

}
