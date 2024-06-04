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
    Description:    Manages the registration process, including collecting user data and interacting with the server to 
                    register new users.
 */
public class RegistrationViewController
{

    // Declaration of Client object
    private Client client;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;
   
    // Constructor for the RegistrationViewController
    public RegistrationViewController(Client client)
    {
        this.client = client; // declaration of Client object
    }

    // Method to handle new user registration
    @FXML
    private void handleRegistration(ActionEvent event)
    {
        // Get input from text fields
        String name = nameInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String email = emailInput.getText().trim();
        String password = passwordInput.getText().trim();
        String address = addressInput.getText().trim();
        
        // Check for empty fields
        if(name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()){
            showError("All fields must have an entry to register an account.");
            return;
        }
        
        // Validate inputs
        if (!validateFullName(name) || !validateAddress(address) || !validatePhone(phone) || !validateEmail(email)) {             
            showError("Please ensure all entries are valid.");
            return;  
        } 
        
        // Send registration request to server
         try {
             System.out.println("Sending registration request to server..."); // Debugging output
             boolean isRegistered = client.registerCustomer(name, phone, email, password, address); 
             System.out.println("Registration status: " + isRegistered); // Debugging output
             if (isRegistered) {
                 showConfirmation("Registration successful! You can now log in.");
                 InitialViewController controller = new InitialViewController(client);
                 Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
             } else {
                 showError("Registration failed. Please try again.");
             }
        } catch (IOException  e) {
            e.printStackTrace();
        } 
    }    
    
    // Method to show confirmation alert
    private void showConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
    
    // Method to show error alert 
    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    // Method to switch between the RegistrationView and the InitialView
    @FXML
    private void handleSigninLink(ActionEvent event) throws IOException
    {
        InitialViewController controller = new InitialViewController(client);
        Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
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
    
    // Validation methods
    private boolean validateFullName(String name){
        return name.matches("[a-zA-Z\\-\\s]+") && name.trim().split("\\s+").length >=2; // check if the name input contains only letters or "-"
    }
    
    private boolean validateAddress(String address){
        return address.matches("[a-zA-Z0-9\\-\\s]+"); // check if the address input only contains letters, numbers and "-"
    }
    
    private boolean validatePhone(String phone){
        return phone.matches("[0-9\\-\\+]+"); // check if the phone input only contains letters, "-" or "+"
    }
    
    private boolean validateEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"); // check if the email input valid
    }
}
