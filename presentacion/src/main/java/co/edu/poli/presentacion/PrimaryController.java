package co.edu.poli.presentacion;

import co.edu.poli.database.Usuario; // Importamos el modelo Usuario
import co.edu.poli.negocio.ManegerSeguridad; // Importamos el Manager de Negocio

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField; // Importamos PasswordField

public class PrimaryController {

    // 1. Declaración de Controles de la Interfaz (deben coincidir con el fx:id en FXML)
    @FXML
    private TextField cedulaField; // Enlaza el campo de Cédula
    
    @FXML
    private PasswordField passwordField; // Enlaza el campo de Contraseña
    
    @FXML
    private Label resultLabel; // Enlaza la etiqueta de resultados

    // 2. Instancia de la Capa de Negocio
    private final ManegerSeguridad seguridadManager;

    /**
     * Constructor del controlador. Se ejecuta antes de inicializar la vista (FXML).
     * Aquí inicializamos las dependencias del Manager.
     */
    public PrimaryController() {
        this.seguridadManager = new ManegerSeguridad();
    }
    
    /**
     * Método invocado cuando el usuario hace clic en el botón "Iniciar Sesión".
     * @FXML conecta este método con el atributo onAction del botón en primary.fxml
     */
    @FXML
    private void onLoginAction() {
        resultLabel.setText("Verificando credenciales...");

        try {
            // 3. Obtener Datos de la Interfaz
            // Convertir la cédula a int
            int cedula = Integer.parseInt(cedulaField.getText()); 
            String contrasena = passwordField.getText();

            // 4. Llamar a la Lógica de Negocio (ManegerSeguridad)
            // Ya no usamos System.out.println, sino que capturamos el resultado del método
            Usuario usuario = seguridadManager.loginUsuario(cedula, contrasena);

            // 5. Mostrar el Resultado en la Interfaz
            if (usuario != null) {
                // Login Exitoso
                String mensaje = "✅ ¡Bienvenido, " + usuario.getNombre() + " (" + usuario.getRol() + ")!";
                resultLabel.setText(mensaje);
                resultLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                
                // *** Aquí debería ir la lógica para CAMBIAR de escena/vista ***
                // switchSceneToMainApplication(usuario);
                
            } else {
                // Login Fallido
                resultLabel.setText("❌ Error de Login: Cédula o Contraseña incorrecta.");
                resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
            
        } catch (NumberFormatException e) {
            // Error si la cédula no es un número válido
            resultLabel.setText("❌ Error: La cédula debe ser un número entero.");
            resultLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } catch (Exception e) {
            // Manejo de otros errores (ej. error de conexión a DB)
             resultLabel.setText("❌ Error en la aplicación: " + e.getMessage());
             resultLabel.setStyle("-fx-text-fill: darkred; -fx-font-weight: bold;");
        }
    }
}