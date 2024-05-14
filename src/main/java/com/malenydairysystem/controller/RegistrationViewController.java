/*
    Students: Tina Losin (10569238)
    Description: Manages the registration process, including collecting user data and interacting with the server to register new users.
 */

package com.malenydairysystem.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class RegistrationViewController {
    
    @FXML
    private TextField nameInput;
     
    @FXML
    private TextField addressInput;
            
    @FXML
    private TextField phoneInput;
    
    @FXML
    private TextField emailInput;
    
    @FXML
    private TextField passwordInput;
            
    @FXML
    private TextField reEnteredPasswordInput;
    
    
    
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
    private void handleRegistration(ActionEvent event){
        
    }
    
    @FXML
    private void handleSigninLink(ActionEvent event){
        
    }
          
    @FXML
    private void handleNameInput(){
        
    }
    
    @FXML
    private void handleAddressInput(){
        
    }
    
    @FXML
    private void handlePhoneInput(){
        
    }
    
    @FXML
    private void handleEmailInput(){
        
    }
    
    @FXML
    private void handlePasswordInput(){
        
    }
    
    @FXML
    private void handleReEnteredPasswordInput(){
        
    }
    
 
}
