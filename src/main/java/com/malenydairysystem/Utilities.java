package com.malenydairysystem;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    
    // Method to encrypt passwords using SHA-256
    public static String encryptPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

    // Switch Scene Method
    public static void switchScene(ActionEvent event, String fxmlPath, Object controller) throws IOException
    {
        // Load the FXML File
        URL fxmlLocation = Utilities.class.getClassLoader().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        // Set the Controller
        loader.setController(controller);

        // Load the Parent
        Parent root = loader.load();

        // Close the Current Stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Create a New Stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Switch Pane Method
    public static void switchPane(BorderPane contentArea, String fxmlPath, Object controller) throws IOException
    {
        // Load the FXML File
        URL fxmlLocation = Utilities.class.getClassLoader().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        // Set the Controller
        loader.setController(controller);

        // Load the Pane
        Pane pane = loader.load();

        // Set the Pane
        contentArea.getChildren().setAll(pane);
    }

    // Show Error Method
    public static void showError(String errorText)
    {
        // Create an Alert of type Error
        Alert alert = new Alert(Alert.AlertType.ERROR, errorText);
        alert.setHeaderText("Error");
        alert.showAndWait();
    }

    // Show Information Method
    public static void showInformation(String informationText)
    {
        // Create an Alert of type Information
        Alert alert = new Alert(Alert.AlertType.INFORMATION, informationText);
        alert.setHeaderText("Information");
        alert.showAndWait();
    }

    // Show Warning Method
    public static boolean showWarning(String warningText)
    {
        // Create an Alert of type Confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, warningText);
        alert.setHeaderText("Warning");
        alert.showAndWait();

        // Return true if the user clicks OK
        return alert.getResult().getText().equals("OK");
    }
}
