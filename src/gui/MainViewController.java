package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("Seller menu item clicked");
        // Logic to handle seller menu item action can be added here
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        System.out.println("Department menu item clicked");
        // Logic to handle department menu item action can be added here
    }

    @FXML
    public void onMenuItemAboutAction() {
        System.out.println("About menu item clicked");
        // Logic to handle about menu item action can be added here
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // Initialization logic can be added here if needed
    }

    // Additional methods for handling UI events can be added here
}
