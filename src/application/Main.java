package application;

public class Main extends javafx.application.Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            javafx.scene.Parent root = loader.load();
            primaryStage.setTitle("JavaFX JDBC Example");
            primaryStage.setScene(new javafx.scene.Scene(root));
            primaryStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
