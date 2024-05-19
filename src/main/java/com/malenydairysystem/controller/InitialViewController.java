/*
    Students: Joshua White (12196075), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description: Manages the intial welcome screen with sign in and sign up options, directing users to the appropriate authentication processes. 
 */
package com.malenydairysystem.controller;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    
    // Temporary method to switch between the InitialView and the MainView
    @FXML
    private void handleTemporaryMainViewButton(ActionEvent event) throws IOException{
        URL fxmlLocation = getClass().getClassLoader().getResource("com/malenydairysystem/MainView.fxml");  // Load file using ClassLoader 
            
        FXMLLoader loader = new FXMLLoader(fxmlLocation); // Create FXMLLoader instance 
        Parent root = loader.load(); // Load file into Parent object
        
        // Get the current stage and close it   
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 
        currentStage.close();        
        
        Stage stage = new Stage(); // Open a new stage for the new scene
        stage.setScene(new Scene(root)); // Set scene with the loaded Parent object
        stage.show(); // Show new stage  
    }
    
    // Method to switch between the InitialView and the RegistrationView 
    @FXML 
    private void handleRegisterLink(ActionEvent event) throws IOException { 
        URL fxmlLocation = getClass().getClassLoader().getResource("com/malenydairysystem/RegistrationView.fxml");  // Load file using ClassLoader 
            
        FXMLLoader loader = new FXMLLoader(fxmlLocation); // Create FXMLLoader instance 
        Parent root = loader.load(); // Load file into Parent object
        
        // Get the current stage and close it   
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 
        currentStage.close();        
        
        Stage stage = new Stage(); // Open a new stage for the new scene
        stage.setScene(new Scene(root)); // Set scene with the loaded Parent object
        stage.show(); // Show new stage      
    }   
     
    @FXML
    private void handleEmailInput(){
        
    }
    
    @FXML
    private void handlePasswordInput(){
        
    }
    
    
}
