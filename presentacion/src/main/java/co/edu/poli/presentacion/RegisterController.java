package co.edu.poli.presentacion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import co.edu.poli.negocio.ManagerCrearUsuario; // Importa tu Manager de Creación
import co.edu.poli.database.Usuario;
import java.io.IOException;

public class RegisterController {

    // Inyección de campos FXML
    @FXML private TextField regCedulaField;
    @FXML private TextField regNombreField;
    @FXML private TextField regCorreoField;
    @FXML private TextField regPasswordField;
    @FXML private Label regMessageLabel;

    // Inicialización del Manager de Creación de Usuarios
    private final ManagerCrearUsuario crearUsuarioManager = new ManagerCrearUsuario();

    /**
     * Maneja el clic en el botón 'CREAR CUENTA' para registrar un nuevo usuario.
     */
    @FXML
    private void onFinalRegisterAction() {
        // 1. Obtener y validar datos
        String cedulaStr = regCedulaField.getText();
        String nombre = regNombreField.getText();
        String correo = regCorreoField.getText();
        String password = regPasswordField.getText();
        
        if (cedulaStr.isEmpty() || nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            regMessageLabel.setText("❌ Todos los campos son obligatorios.");
            return;
        }

        try {
            int cedula = Integer.parseInt(cedulaStr);
            
            // 2. Llama a la lógica de negocio (ManagerCrearUsuario)
            // Asumimos que el rol por defecto es "Cliente"
            Usuario nuevoUsuario = crearUsuarioManager.crearNuevoCliente(
                cedula, nombre, correo, password, "Cliente" 
            );

            if (nuevoUsuario != null) {
                regMessageLabel.setText("✅ Cuenta creada con éxito. Vuelve a Iniciar Sesión.");
                // Limpiar campos después del registro exitoso
                regCedulaField.clear();
                regNombreField.clear();
                regCorreoField.clear();
                regPasswordField.clear();
            } else {
                // El manager ya imprime un error si la cédula existe, solo mostramos el mensaje.
                regMessageLabel.setText("❌ Error al crear cuenta. Cédula o Correo ya registrados.");
            }

        } catch (NumberFormatException e) {
            regMessageLabel.setText("❌ La cédula debe ser un número entero.");
        }
    }

    /**
     * Maneja el clic en el botón 'VOLVER A INICIO' para regresar a la vista de Login.
     */
    @FXML
    private void onGoBackToLogin() {
        try {
            MainFX.setRoot("primary"); // Carga el FXML de login
        } catch (IOException e) {
            System.err.println("❌ No se pudo cargar la vista de login: " + e.getMessage());
        }
    }
}