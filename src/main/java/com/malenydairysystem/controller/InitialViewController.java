/*
    Students: Tina Losin (10569238)
    Description: Handles the intial welcome screen with sign in and sign up options, directing users to the appropriate authentication processes. 
 */
package com.malenydairysystem.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InitialViewController
{

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
