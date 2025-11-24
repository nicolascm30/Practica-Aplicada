package co.edu.poli.presentacion;

// Importaciones necesarias para manejo de archivos y excepciones
import java.io.IOException;

// Importaciones de tu modelo de base de datos
// (Asegúrate de que estas clases existen en tu paquete database)
import co.edu.poli.database.Usuario;
// import co.edu.poli.database.Administrador; // Descomenta si usas la clase Administrador

// Importaciones de JavaFX
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private VBox adminMenu; // El contenedor de opciones de administrador

    // Objeto para almacenar la información del usuario logueado
    private Object loggedUser; 

    /**
     * Inicializa los datos de la escena secundaria.
     * Este método es llamado desde PrimaryController al hacer login.
     */
    public void initData(Object user) {
        this.loggedUser = user;
        
        // Lógica de presentación basada en el tipo de usuario
        // NOTA: Ajusta la lógica de 'Administrador' según tu implementación real
        
        /* if (user instanceof Administrador) {
             Administrador a = (Administrador) user;
             welcomeLabel.setText("¡Bienvenido, Administrador " + a.getNombre() + "!");
             detailsLabel.setText("Rango: " + a.getRango());
             adminMenu.setVisible(true); // Mostrar menú de administrador
        } else 
        */
        
        if (user instanceof Usuario) {
            Usuario u = (Usuario) user;
            welcomeLabel.setText("¡Bienvenido, " + u.getNombre() + "!");
            detailsLabel.setText("Rol: " + u.getRol() + " | Cédula: " + u.getCedula());
            adminMenu.setVisible(false); // Ocultar menú de administrador para clientes
        } else {
             welcomeLabel.setText("Bienvenido");
             detailsLabel.setText("Usuario desconocido");
             adminMenu.setVisible(false);
        }
    }

    /**
     * ✅ NUEVO MÉTODO: Abre la ventana del Catálogo.
     * Vinculado al botón "Ver Catálogo" en secondary.fxml
     */
    @FXML
    private void onCatalogAction() {
        try {
            // Cargar el FXML del catálogo
            // Asegúrate de haber creado el archivo 'catalog.fxml' en el paquete presentacion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/presentacion/catalog.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana (Stage) independiente para el catálogo
            Stage stage = new Stage();
            stage.setTitle("Catálogo de Canciones");
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            System.err.println("❌ Error al abrir el catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de CERRAR SESIÓN.
     * Cierra la ventana actual y vuelve al login.
     */
    @FXML
    private void logoutAction() {
        try {
            // Obtener la ventana (Stage) actual para operar sobre ella
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            
            // Cargar de nuevo la vista de login (primary.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/presentacion/primary.fxml"));
            Parent root = loader.load();
            
            // Cambiar la escena en la misma ventana
            stage.setScene(new Scene(root, 400, 450)); 
            stage.setTitle("INICIO DE SESIÓN");
            stage.show();

            System.out.println("Sesión cerrada correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Error al cerrar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}