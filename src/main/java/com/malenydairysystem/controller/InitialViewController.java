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
    Description:    Manages the intial welcome screen with sign in and sign up options, directing users to the appropriate 
                    authentication processes. 
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
        this.client = client;
    }

    @FXML
    private void handleSignin(ActionEvent event)
    {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        User authenticatedUser = client.authenticateUser(username, password);

        if (authenticatedUser != null)
        {
            try
            {
                Utilities.showInformation("Successful Login. Welcome back!");

                MainViewController controller = new MainViewController(client, authenticatedUser);
                Utilities.switchScene(event, "com/malenydairysystem/MainView.fxml", controller);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Utilities.showError("Incorrect username or password. Please try again.");
        }
    }

    // Switch to the RegistrationView 
    @FXML
    private void handleRegisterLink(ActionEvent event) throws IOException
    {
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
