package co.edu.poli.presentacion;

// ✅ 1. IMPORTACIONES DE JAVAFX
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// ✅ 2. IMPORTACIONES DE JAVA ESTÁNDAR
import java.io.IOException;

// ✅ 3. IMPORTACIONES DE TU PROYECTO (Bases de datos y Negocio)
// Asegúrate de que estas clases existan en estos paquetes exactos
import co.edu.poli.database.Usuario;
import co.edu.poli.negocio.ManegerSeguridad; 

// Si Administrador está en 'presentacion' (según tus uploads anteriores), no requiere import.
// Pero si lo moviste a 'database', descomenta la siguiente línea:
// import co.edu.poli.database.Administrador; 

public class PrimaryController {

    @FXML
    private TextField cedulaField;
    
    @FXML 
    private TextField passwordField; 

    @FXML
    private Label messageLabel; 
    
    // Instancia del Manager
    private final ManegerSeguridad manegerSeguridad = new ManegerSeguridad();

    /**
     * Maneja el evento de INICIAR SESIÓN.
     */
    @FXML
    private void onLoginAction() {
        messageLabel.setText(""); 
        
        String idText = cedulaField.getText();
        String passwordText = passwordField.getText();
        
        if (idText.isEmpty() || passwordText.isEmpty()) {
            messageLabel.setText("Por favor, ingrese Cédula y Contraseña.");
            return;
        }

        try {
            int identificacion = Integer.parseInt(idText);
            Object loggedUser = null;

            // 1. Intentar Login como Usuario
            loggedUser = manegerSeguridad.loginUsuario(identificacion, passwordText);
            
            // 2. Si no es usuario, intentar como Administrador
            if (loggedUser == null) {
                // NOTA: Asegúrate de que la clase Administrador y el método loginAdministrador existan
                // Si Administrador está en el mismo paquete, esto funciona.
                // Si está en otro, asegúrate de importarlo arriba.
                Administrador admin = manegerSeguridad.loginAdministrador(1, identificacion); 
                if (admin != null) {
                    loggedUser = admin;
                }
            }

            if (loggedUser != null) {
                messageLabel.setText("✅ Login Exitoso. Redirigiendo...");
                loadSecondaryScene(loggedUser);
            } else {
                messageLabel.setText("❌ Cédula o Contraseña incorrecta.");
            }

        } catch (NumberFormatException e) {
            messageLabel.setText("La identificación debe ser un número.");
        } catch (Exception e) {
            messageLabel.setText("Error al cargar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Acción para ir al Registro.
     */
    @FXML
    private void onRegisterAction() {
        try {
            // Llama al método estático en MainFX
            MainFX.setRoot("register"); 
        } catch (IOException e) {
            messageLabel.setText("❌ Error al cargar la pantalla de registro.");
            System.err.println("Error cargando register.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga la escena secundaria.
     */
    private void loadSecondaryScene(Object loggedUser) throws IOException {
        Stage stage = (Stage) cedulaField.getScene().getWindow();
        
        // Carga el FXML de secondary
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/presentacion/secondary.fxml"));
        Parent root = loader.load();

        // Obtiene el controlador y pasa los datos
        SecondaryController controller = loader.getController();
        controller.initData(loggedUser);
        
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
        stage.show();
    }
}