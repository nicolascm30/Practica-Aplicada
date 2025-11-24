package co.edu.poli.presentacion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException; // Necesario para manejar la carga de FXML

import co.edu.poli.database.Usuario;
import co.edu.poli.negocio.ManegerSeguridad; // Importa tu Manager de Seguridad

public class PrimaryController {
    
    // Inyección de campos FXML
    @FXML private TextField cedulaField;
    @FXML private TextField passwordField; // Lo mejor es usar PasswordField
    @FXML private Label messageLabel;

    // Inicialización del Manager de Seguridad
    private final ManegerSeguridad seguridadManager = new ManegerSeguridad();

    /**
     * Maneja el clic en el botón 'INICIAR SESIÓN'.
     */
    @FXML
    private void onLoginAction() {
        // Validación de entrada
        String cedulaStr = cedulaField.getText();
        String password = passwordField.getText();
        
        if (cedulaStr.isEmpty() || password.isEmpty()) {
            messageLabel.setText("❌ Cédula y contraseña son requeridos.");
            return;
        }

        try {
            int cedula = Integer.parseInt(cedulaStr);
            
            // Llama a la lógica de negocio (ManegerSeguridad)
            Usuario usuarioLogeado = seguridadManager.loginUsuario(cedula, password);

            if (usuarioLogeado != null) {
                messageLabel.setText("✅ ¡Bienvenido, " + usuarioLogeado.getNombre() + "!");
                // Aquí iría la lógica para cargar la vista principal del usuario/cliente
                System.out.println("Navegando a la vista principal...");
            } else {
                // Intenta si es administrador
                // Esto es una simplificación, en un app real se podría hacer una sola consulta
                // o usar campos diferentes para Admin/Usuario
                messageLabel.setText("❌ Credenciales incorrectas."); 
            }
            
        } catch (NumberFormatException e) {
            messageLabel.setText("❌ La cédula debe ser un número.");
        }
    }

    /**
     * Maneja el clic en el botón 'REGISTRARSE' para cambiar de vista.
     */
    @FXML
    private void onRegisterAction() {
        try {
            // Llama al método setRoot modificado en MainFX (ver paso 5)
            MainFX.setRoot("register"); // Carga el nuevo FXML: register.fxml
        } catch (IOException e) {
            System.err.println("❌ No se pudo cargar la vista de registro: " + e.getMessage());
        }
    }
}