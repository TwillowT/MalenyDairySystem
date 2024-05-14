module com.malenydairysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.malenydairysystem.controller to javafx.fxml;
    exports com.malenydairysystem;
}
