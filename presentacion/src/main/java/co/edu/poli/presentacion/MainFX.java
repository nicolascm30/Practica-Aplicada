package co.edu.poli.presentacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFX extends Application {

    // 💡 Hacemos el Stage estático para que el controlador pueda acceder a él
    private static Scene scene; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Inicializamos la escena con el FXML de Login
        scene = new Scene(loadFXML("primary"), 400, 450); // Le damos un tamaño más grande
        stage.setTitle("Billetera Digital - Login");
        stage.setScene(scene);
        stage.show();
    }
    
    // 💡 Método estático para que los controladores puedan cambiar de vista
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // 💡 Método auxiliar para cargar el FXML
    private static Parent loadFXML(String fxml) throws IOException {
        // La ruta debe ser relativa al paquete (co.edu.poli.presentacion)
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}