package co.edu.poli.presentacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/co/edu/poli/presentacion/primary.fxml"));
        stage.setTitle("Prueba JavaFX");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
}
