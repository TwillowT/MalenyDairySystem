package com.malenydairysystem.controller;

import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private TextField passwordInput;
    @FXML
    private TextField reEnteredPasswordInput;

    // Constructor for the RegistrationViewController
    public RegistrationViewController(Client client)
    {
        this.client = client;
    }

    @FXML
    private void handleRegistration(ActionEvent event)
    {

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
}
