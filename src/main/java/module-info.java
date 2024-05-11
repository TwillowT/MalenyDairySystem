module com.malenydairysystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.malenydairysystem to javafx.fxml;
    exports com.malenydairysystem;
}
