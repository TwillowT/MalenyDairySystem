package com.malenydairysystem.controller;

// Imports
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
    Description:    Manage the Registration View in the application for users to register a new account.
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
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    // Constructor for the RegistrationViewController
    public RegistrationViewController(Client client)
    {
        // Set the Client Object
        this.client = client;
    }

    // Handle the Register Button action for Customer
    @FXML
    private void handleRegistration(ActionEvent event)
    {
        // Get Input from Text Fields
        String name = nameInput.getText().trim();
        String address = addressInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String email = emailInput.getText().trim();
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText().trim();

        // Check if the Customer Name is Empty
        if (name.isEmpty())
        {
            // Show error alert
            Utilities.showError("Please enter your Full Name.");
            return;
        }
        // Check if the Customer Address is Empty
        else if (address.isEmpty())
        {
            // Show error alert
            Utilities.showError("Please enter your Address.");
            return;
        }
        // Check if the Customer Phone Number is Empty
        else if (phone.isEmpty())
        {
            // Show error alert
            Utilities.showError("Please enter your Phone Number.");
            return;
        }
        // Check if the Customer Email is Empty
        else if (email.isEmpty())
        {
            // Show error alert
            Utilities.showError("Please enter your Email.");
            return;
        }
        // Check if the Customer Username is Empty
        else if (username.isEmpty())
        {
            // Show error alert
            Utilities.showError("Please enter your Username.");
            return;
        }
        // Check if the Customer Password is Empty
        else if (password.isEmpty())
        {
            // Show error alert
            Utilities.showError("Please enter your Password.");
            return;
        }

        // Add the Customer to the Database and get the result
        boolean result = client.addCustomer(name, address, phone, email, username, password);

        // Check if the Result is True
        if (result)
        {
            // Show Successful Registration Alert
            Utilities.showInformation("Registration successful. Please sign in.");

            try
            {
                // Switch to the InitialView
                InitialViewController controller = new InitialViewController(client);
                Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
            }
            catch (IOException e)
            {
                Utilities.showError("An error occurred. Please try again.");
            }
        }
        else
        {
            Utilities.showError("Registration failed. Please try again.");
        }
    }

    // Method to switch between the RegistrationView and the InitialView
    @FXML
    private void handleSigninLink(ActionEvent event) throws IOException
    {
        InitialViewController controller = new InitialViewController(client);
        Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
    }

    // Exit the Application
    @FXML
    private void clickExit()
    {
        // Show Confirmation Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Exit?");
        alert.showAndWait().ifPresent(response ->
        {
            // Check if the User Clicked OK
            if (response == ButtonType.OK)
            {
                // Exit the Application
                Platform.exit();
            }
        });
    }
}
