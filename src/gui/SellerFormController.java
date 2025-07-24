package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField sellerIDField;

    @FXML
    private TextField sellerNameField;

    @FXML
    private TextField sellerEmailField;

    @FXML
    private DatePicker sellerDpBirthDateField;

    @FXML
    private TextField sellerBaseSalaryField;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Label errorMessageLabelEmail;

    @FXML
    private Label errorMessageLabelBirthDate;

    @FXML
    private Label errorMessageLabelBaseSalary;

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
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving Seller", null, e.getMessage(), AlertType.ERROR);
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    /**
     * Gets the form data from the text fields and creates a Seller object.
     *
     * @return a Seller object with the data from the form
     */
    private Seller getFormData() {
        Seller obj = new Seller();
        ValidationException exception = new ValidationException("Validation error");
       
        obj.setId(Utils.tryParseToInt(sellerIDField.getText()));

        if (sellerNameField.getText() == null || sellerNameField.getText().trim().isEmpty()) {
            exception.addError("name", "Field can't be empty");
        }

        obj.setName(sellerNameField.getText());

        if (sellerEmailField.getText() == null || sellerEmailField.getText().trim().isEmpty()) {
            exception.addError("email", "Field can't be empty");
        } else if (!sellerEmailField.getText().matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            exception.addError("email", "Invalid email format");
        }

        obj.setEmail(sellerEmailField.getText());

        if (sellerDpBirthDateField.getValue() == null) {
            exception.addError("birthDate", "Field can't be empty");
        } else {
            Instant instant = Instant.from(sellerDpBirthDateField.getValue().atStartOfDay(ZoneId.systemDefault()));
            if(instant.isAfter(Instant.now())) {
                exception.addError("birthDate", "Birth date cannot be in the future");
            }
            obj.setBirthDate(Date.from(instant));
        }

        if (sellerBaseSalaryField.getText() == null || sellerBaseSalaryField.getText().trim().isEmpty()) {
            exception.addError("baseSalary", "Field can't be empty");
        } else {
            try {
                obj.setBaseSalary(Utils.tryParseToDouble(sellerBaseSalaryField.getText()));
            } catch (NumberFormatException e) {
                exception.addError("baseSalary", "Invalid base salary format");
            }
        }

        obj.setDepartment(comboBoxDepartment.getValue());


        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onCancelButtonAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    private ObservableList<Department> obsList;

    /**
     * Sets the Seller entity for this controller.
     *
     * @param entity the Seller entity to be set
     */
    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    /**
     * Sets the SellerService for this controller.
     *
     * @param service the SellerService to be used by this controller
     */
    public void setServices(SellerService service, DepartmentService departmentService) {
        this.service = service;
        this.departmentService = departmentService;
    }

    /**
     * Adds a DataChangeListener to this controller.
     *
     * @param listener the DataChangeListener to be added
     */
    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
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
        Constraints.setTextFieldInteger(sellerIDField);
        Constraints.setTextFieldMaxLength(sellerNameField, 70);
        Constraints.setTextFieldMaxLength(sellerEmailField, 60);
        Constraints.setTextFieldDouble(sellerBaseSalaryField);
        Constraints.setTextFieldMaxLength(sellerBaseSalaryField, 10);
        Utils.formatDatePicker(sellerDpBirthDateField, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    /**
     * Updates the form data with the current entity's data.
     * This method should be called after setting the entity.
     */
    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        sellerIDField.setText(String.valueOf(entity.getId()));
        sellerNameField.setText(entity.getName());
        sellerEmailField.setText(entity.getEmail());
        if (entity.getBirthDate() != null) {
            sellerDpBirthDateField
                    .setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        } else {
            sellerDpBirthDateField.setValue(null);
        }
        Locale.setDefault(Locale.US);
        sellerBaseSalaryField.setText(String.format("%.2f", entity.getBaseSalary()));

        if(entity.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects() {
        if (departmentService == null) {
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Department> list = departmentService.findAll();
        obsList = javafx.collections.FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
        // Utils.formatComboBox(comboBoxDepartment, "Select a Department");

        if (entity != null && entity.getDepartment() != null) {
            comboBoxDepartment.setValue(entity.getDepartment());
        } else {
            comboBoxDepartment.setValue(null);
        }
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        
        if (fields.contains("name")) {
            errorMessageLabel.setText(errors.get("name"));
        } else {
            errorMessageLabel.setText("");
        }

        if (fields.contains("email")) {
            errorMessageLabelEmail.setText(errors.get("email"));
        } else {
            errorMessageLabelEmail.setText("");
        }

        if (fields.contains("baseSalary")) {
            errorMessageLabelBaseSalary.setText(errors.get("baseSalary"));
        } else {
            errorMessageLabelBaseSalary.setText("");
        }

        if (fields.contains("birthDate")) {
            errorMessageLabelBirthDate.setText(errors.get("birthDate"));
        } else {
            errorMessageLabelBirthDate.setText("");
        }
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
