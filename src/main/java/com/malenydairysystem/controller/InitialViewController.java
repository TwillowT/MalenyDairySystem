package com.malenydairysystem.controller;

import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the intial welcome screen with sign in and sign up options, directing users to the appropriate 
                    authentication processes. 
 */
public class InitialViewController
{
    // Declaration of Client object
    private Client client;

    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;

    // Constructor for the InitialViewController
    public InitialViewController(Client client)
    {
        this.client = client;
    }

    @FXML
    private void handleSignin(ActionEvent event){
        String email = emailInput.getText();
        String password = passwordInput.getText();
              
        boolean authenticated = client.authenticateCustomer(email, password); // send encrypted password
        
        if(authenticated){ 
            // Navigate to MainView
            try{
                MainViewController controller = new MainViewController(client);
                Utilities.switchScene(event, "com/malenydairysystem/MainView.fxml", controller);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
                showError("Incorrect email or password. Please try again.");
        }        
    }
    
    // Method to show alert 
    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    // Switch to the MainView
    @FXML
    private void handleTemporaryMainViewButton(ActionEvent event) throws IOException
    {
        //MainViewController controller = new MainViewController(client);
       // Utilities.switchScene(event, "com/malenydairysystem/MainView.fxml", controller);
    }

    // Switch to the RegistrationView 
    @FXML
    private void handleRegisterLink(ActionEvent event) throws IOException
    {
        RegistrationViewController controller = new RegistrationViewController(client);
        Utilities.switchScene(event, "com/malenydairysystem/RegistrationView.fxml", controller);
    }

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
}
