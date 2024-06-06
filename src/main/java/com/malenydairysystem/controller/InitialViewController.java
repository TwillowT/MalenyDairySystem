package com.malenydairysystem.controller;

import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.User;

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
    Description:    Manages the Initital View in the application for users to sign in or register.
 */
public class InitialViewController
{

    // Declaration of Client object
    private Client client;

    // Declaration of FXML elements for Sign In
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    // Constructor for the InitialViewController
    public InitialViewController(Client client)
    {
        // Set the Client Object
        this.client = client;
    }

    // Handle the Sign In Button
    @FXML
    private void handleSignin(ActionEvent event)
    {
        // Get the Username and Password
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        // Authenticate the User
        User authenticatedUser = client.authenticateUser(username, password);

        // Check if the User is authenticated
        if (authenticatedUser != null)
        {
            try
            {
                // Show Information Alert
                Utilities.showInformation("Successful Login. Welcome back!");

                // Switch to the MainView
                MainViewController controller = new MainViewController(client, authenticatedUser);
                Utilities.switchScene(event, "com/malenydairysystem/MainView.fxml", controller);
            }
            catch (IOException e)
            {
                // Print Stack Trace
                e.printStackTrace();
            }
        }
        else
        {
            // Show Error Alert
            Utilities.showError("Incorrect username or password. Please try again.");
        }
    }

    // Switch to the RegistrationView 
    @FXML
    private void handleRegisterLink(ActionEvent event) throws IOException
    {
        // Switch to the RegistrationView
        RegistrationViewController controller = new RegistrationViewController(client);
        Utilities.switchScene(event, "com/malenydairysystem/RegistrationView.fxml", controller);
    }

    // Exit the Application
    @FXML
    private void clickExit()
    {
        // Confirmation Alert
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
