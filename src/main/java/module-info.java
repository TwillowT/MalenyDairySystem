module com.malenydairysystem{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.malenydairysystem.controller to javafx.fxml;
    opens com.malenydairysystem.model to javafx.base;
    
    exports com.malenydairysystem;
}
