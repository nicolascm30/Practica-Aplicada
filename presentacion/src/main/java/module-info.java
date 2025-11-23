module co.edu.poli.presentacion {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.poli.presentacion to javafx.fxml;
    exports co.edu.poli.presentacion;
}
