package com.malenydairysystem.controller;

import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the main application window, serving as the primary interface for navigation between different 
                    sections of the system.
 */
public class MainViewController
{

    // Declaration for Client object
    private Client client;

    @FXML
    private BorderPane contentArea;

    // Constructor for MainViewController
    public MainViewController(Client client)
    {
        this.client = client;
    }

    // Method to load the ProductView
    @FXML
    private void loadProductView(ActionEvent event) throws IOException
    {
        ProductViewController controller = new ProductViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/ProductView.fxml", controller);
    }

    // Method to load the ScheduleView
    @FXML
    private void loadScheduleView(ActionEvent event) throws IOException
    {
        ScheduleViewController controller = new ScheduleViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/ScheduleView.fxml", controller);
    }

    // Method to load the OrderView
    @FXML
    private void loadOrderView(ActionEvent event) throws IOException
    {
        OrderViewController controller = new OrderViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/OrderView.fxml", controller);
    }

    // Method to load the AdminView
    @FXML
    private void loadAdminView(ActionEvent event) throws IOException
    {
        AdminViewController controller = new AdminViewController(client);
        Utilities.switchPane(contentArea, "com/malenydairysystem/AdminView.fxml", controller);
    }

    // Method to handle logging out
    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException
    {
        InitialViewController controller = new InitialViewController(client);
        Utilities.switchScene(event, "com/malenydairysystem/InitialView.fxml", controller);
    }


    @FXML
    private void handleContentArea(ActionEvent event) throws IOException
    {

    }
}
