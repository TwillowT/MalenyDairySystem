package com.malenydairysystem.controller;

// Imports
import com.malenydairysystem.model.Delivery;
import com.malenydairysystem.client.Client;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages delivery schedule interactions in the application, displaying and managing delivery details.
 */
public class ScheduleViewController
{

    // Declaration for Client object
    private Client client;

    // FXML References for Delivery Table
    @FXML
    private TableView<Delivery> deliveryTable;
    @FXML
    private TableColumn<Delivery, Integer> deliveryIdColumn;
    @FXML
    private TableColumn<Delivery, Integer> deliveryPostcodeColumn;
    @FXML
    private TableColumn<Delivery, String> deliveryDayColumn;
    @FXML
    private TableColumn<Delivery, Double> deliveryCostColumn;

    // Constructor for ScheduleViewController
    public ScheduleViewController(Client client)
    {
        this.client = client;
    }

    // Initializes the Controller
    public void initialize()
    {
        // Intialize the Delivery Table Columns
        deliveryIdColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryID"));
        deliveryPostcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        deliveryDayColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));
        deliveryCostColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));

        // Load Deliveries into the Table
        loadDeliveries();
    }

    //Fetches and displays all deliveries from the database
    private void loadDeliveries()
    {
        // Get all Deliveries from the Database
        List<Delivery> deliveries = client.getAllDeliveries();

        // Set the Delivery Table with the Deliveries
        deliveryTable.getItems().setAll(deliveries);
    }
}
