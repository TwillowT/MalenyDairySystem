module com.malenydairysystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.malenydairysystem.controller to javafx.fxml;
    exports com.malenydairysystem;
}
