package com.malenydairysystem.controller;

import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.Admin;
import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.User;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manage the Main View in the application for users to navigate to different views.
 */
public class MainViewController
{

    // Declaration for Client object
    private Client client;

    // Declaration for User object
    private User user;

    // Declaration for FXML elements
    @FXML
    private BorderPane contentArea;
    @FXML
    private Button productButton;
    @FXML
    private Button scheduleButton;
    @FXML
    private Button orderButton;
    @FXML
    private Button adminButton;

    // Constructor for MainViewController
    public MainViewController(Client client, User user)
    {
        // Set the Client Object
        this.client = client;

        // Set the User Object
        this.user = user;
    }

    // Initialisation Method
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
        // Switch to the InitialView
        InitialViewController controller = new InitialViewController(client);
        Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
    }
}
