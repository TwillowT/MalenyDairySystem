package com.malenydairysystem.controller;

// Imports
import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.Admin;
import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.User;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages main application view and navigation based on user roles.
 */
public class MainViewController
{

    // Declaration for Client object
    private Client client;

    // Declaration for User object
    private User user;

    // Declaration for FXML elements
    @FXML
    private BorderPane contentArea;// Main content area of the UI
    @FXML
    private Button productButton; // Button to navigate to product view
    @FXML
    private Button scheduleButton;// Button to navigate to schedule view
    @FXML
    private Button orderButton;// Button to navigate to order view
    @FXML
    private Button adminButton;// Button to navigate to admin view

    // Constructor for MainViewController
    public MainViewController(Client client, User user)
    {
        // Set the Client Object
        this.client = client;

        // Set the User Object
        this.user = user;
    }

    // Initializes the controller
    @FXML
    private void initialize()
    {
        // Hide buttons based on User Type
        if (user instanceof Admin)
        {
            // Disabling buttons for Admin
            productButton.setDisable(true);
            scheduleButton.setDisable(true);
            orderButton.setDisable(true);

            // Loading the AdminView
            try
            {
                // Load the AdminView
                loadAdminView(new ActionEvent());
            }
            catch (IOException e)
            {
                // Print the stack trace
                e.printStackTrace();
            }
        }
        else if (user instanceof Customer)
        {
            // Disabling buttons for Customer
            adminButton.setDisable(true);

            // Loading the ProductView
            try
            {
                // Load the ProductView
                loadProductView(new ActionEvent());
            }
            catch (IOException e)
            {
                // Print the stack trace
                e.printStackTrace();
            }
        }
    }

    // Method to load the ProductView
    @FXML
    private void loadProductView(ActionEvent event) throws IOException
    {
        // Switch to the ProductView
        ProductViewController controller = new ProductViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/ProductView.fxml", controller);
    }

    // Method to load the ScheduleView
    @FXML
    private void loadScheduleView(ActionEvent event) throws IOException
    {
        // Switch to the ScheduleView
        ScheduleViewController controller = new ScheduleViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/ScheduleView.fxml", controller);
    }

    // Method to load the OrderView
    @FXML
    private void loadOrderView(ActionEvent event) throws IOException
    {
        // Switch to the OrderView
        OrderViewController controller = new OrderViewController(client, user);
        Utilities.switchPane(contentArea, "com/malenydairysystem/OrderView.fxml", controller);
    }

    // Method to load the AdminView
    @FXML
    private void loadAdminView(ActionEvent event) throws IOException
    {
        // Switch to the AdminView
        AdminViewController controller = new AdminViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/AdminView.fxml", controller);
    }

    // Method to handle logging out
    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException
    {
        // Create an alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Logging out will end your session.");
        
        //Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            // Switch to the InitialView if user confirms
            InitialViewController controller = new InitialViewController(client);
            Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
        }else{
            // User canclled logout
            alert.close();
        }
        
    }
}
