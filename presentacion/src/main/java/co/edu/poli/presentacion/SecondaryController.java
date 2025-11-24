package co.edu.poli.presentacion;
import java.io.IOException; // Resuelve 'IOException cannot be resolved to a type'
import javafx.fxml.FXMLLoader; // Resuelve 'FXMLLoader cannot be resolved to a type'
import javafx.scene.Parent; // Resuelve 'Parent cannot be resolved to a type'
import javafx.scene.Scene; // Resuelve 'Scene cannot be resolved to a type'
import javafx.stage.Stage; // Aunque 'Stage' no tiene error de "cannot be resolved", a menudo se usa para el cambio de escena.
import co.edu.poli.database.Usuario; 
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private VBox adminMenu; // El VBox que contiene las opciones de administrador

    // Objeto para almacenar la información del usuario/administrador
    private Object loggedUser; 

    /**
     * Inicializa los datos de la escena secundaria.
     */
    public void initData(Object user) {
        this.loggedUser = user;
        
        // Lógica de presentación basada en el tipo de usuario (Usuario o Administrador)
        if (user instanceof Administrador) {
             Administrador a = (Administrador) user;
             welcomeLabel.setText("¡Bienvenido, Administrador " + a.getNombre() + "!");
             detailsLabel.setText("Cédula: " + a.getCedula() + " | Rango: " + a.getRango());
             adminMenu.setVisible(true); // Mostrar menú de administrador
        } else if (user instanceof Usuario) {
            Usuario u = (Usuario) user;
            welcomeLabel.setText("¡Bienvenido, " + u.getNombre() + "!");
            detailsLabel.setText("Rol: " + u.getRol() + " | Cédula: " + u.getCedula());
            adminMenu.setVisible(false); // Ocultar menú de administrador
        } else {
             welcomeLabel.setText("Error de autenticación.");
             detailsLabel.setText("Usuario no reconocido.");
             adminMenu.setVisible(false);
        }
    }
    
    /**
     * Maneja el evento de CERRAR SESIÓN.
     */
    @FXML
    private void logoutAction() {
        try {
            // Obtener el Stage actual
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            
            // Recargar la escena de login (primary.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/presentacion/primary.fxml"));
            Parent root = loader.load();
            
            stage.setScene(new Scene(root, 400, 300)); // Usar el tamaño original de login
            stage.setTitle("INICIO DE SESIÓN");
            stage.show();

            System.out.println("Sesión cerrada y regresando a la pantalla de login.");
        } catch (IOException e) {
            System.err.println("❌ Error al recargar la escena de login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}