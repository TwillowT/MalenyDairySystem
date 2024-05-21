package com.malenydairysystem;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Utilities Class to allow the Program to call repeated actions, such as changing a scene or changing
                    a pane in the GUI.
 */
public class Utilities
{

    public static void switchScene(ActionEvent event, String fxmlPath, Object controller) throws IOException
    {
        URL fxmlLocation = Utilities.class.getClassLoader().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setController(controller);

        Parent root = loader.load();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void switchPane(BorderPane contentArea, String fxmlPath, Object controller) throws IOException
    {
        URL fxmlLocation = Utilities.class.getClassLoader().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setController(controller);

        Pane pane = loader.load();

        contentArea.getChildren().setAll(pane);
    }
}
