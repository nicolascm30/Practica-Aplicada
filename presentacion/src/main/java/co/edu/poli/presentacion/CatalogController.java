package co.edu.poli.presentacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador para la vista del Catálogo (catalog.fxml).
 * Se encarga de poblar la TableView con datos de muestra.
 */
public class CatalogController {

    // 1. Elementos inyectados desde catalog.fxml
    @FXML private TableView<CancionModelo> tablaCanciones;
    @FXML private TableColumn<CancionModelo, String> colTitulo;
    @FXML private TableColumn<CancionModelo, String> colArtista;
    @FXML private TableColumn<CancionModelo, String> colGenero;
    @FXML private TableColumn<CancionModelo, Double> colPrecio;

    /**
     * Se llama automáticamente después de que el FXML es cargado.
     * Aquí configuramos las columnas y cargamos los datos de muestra.
     */
    @FXML
    public void initialize() {
        // Configurar cómo se mapean los datos del objeto CancionModelo a las columnas
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Cargar 5 canciones de muestra (datos hardcoded)
        tablaCanciones.setItems(obtenerCancionesMuestra());
    }

    /**
     * Crea la lista observable con las canciones de muestra.
     */
    private ObservableList<CancionModelo> obtenerCancionesMuestra() {
        ObservableList<CancionModelo> lista = FXCollections.observableArrayList();
        lista.add(new CancionModelo("Bohemian Rhapsody", "Queen", "Rock", 1.99));
        lista.add(new CancionModelo("Billie Jean", "Michael Jackson", "Pop", 1.49));
        lista.add(new CancionModelo("Hotel California", "Eagles", "Rock", 1.80));
        lista.add(new CancionModelo("Shape of You", "Ed Sheeran", "Pop", 1.20));
        lista.add(new CancionModelo("Smells Like Teen Spirit", "Nirvana", "Grunge", 1.60));
        return lista;
    }

    /**
     * Maneja la acción del botón "Cerrar Catálogo".
     */
    @FXML
    private void cerrarCatalogo() {
        // Obtener la ventana (Stage) actual usando cualquier elemento de la escena y cerrarla
        Stage stage = (Stage) tablaCanciones.getScene().getWindow();
        stage.close();
    }

    // --- Clase interna simple (Modelo) para mostrar datos en la tabla ---
    /**
     * Clase modelo para representar una fila en la tabla de canciones.
     * Nota: Los nombres de los métodos getXyz deben coincidir con PropertyValueFactory.
     */
    public static class CancionModelo {
        private final String titulo;
        private final String artista;
        private final String genero;
        private final double precio;

        public CancionModelo(String titulo, String artista, String genero, double precio) {
            this.titulo = titulo;
            this.artista = artista;
            this.genero = genero;
            this.precio = precio;
        }

        // Getters requeridos por PropertyValueFactory
        public String getTitulo() { return titulo; }
        public String getArtista() { return artista; }
        public String getGenero() { return genero; }
        public double getPrecio() { return precio; }
    }
}