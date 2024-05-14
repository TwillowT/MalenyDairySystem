/*
    Students: Tina Losin (10569238)
    Description: Handles the intial welcome screen with sign in and sign up options, directing users to the appropriate authentication processes. 
 */
package com.malenydairysystem.controller;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitialViewController
{
    @FXML
    private TextField emailInput;
    
    @FXML
    private TextField passwordInput;
     

    @FXML
    private void clickExit()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Exit?");
        alert.showAndWait().ifPresent(response ->
        {
            if (response == ButtonType.OK)
            {
                Platform.exit();
            }
        });
    }
    
       
     @FXML
    private void handleSignin(){
        
    } 
    
    // not functioning correctly - I will fix this tomorrow
    @FXML
    private void handleRegisterLink() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    
    
    @FXML
    private void handleEmailInput(){
        
    }
    
    @FXML
    private void handlePasswordInput(){
        
    }
    
    
}
