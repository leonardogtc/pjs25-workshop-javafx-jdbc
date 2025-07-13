package application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

public class Main extends javafx.application.Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();

            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            Scene mainScene = new Scene(scrollPane);
            primaryStage.setTitle("JavaFX JDBC Example");
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
