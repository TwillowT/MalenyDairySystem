package com.malenydairysystem.controller;

import com.malenydairysystem.model.Delivery;
import com.malenydairysystem.client.Client;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages delivery schedule interactions in the application.
 */
public class ScheduleViewController {

    // Declaration for Client object
    private Client client;
    // Declaration of FXML Elements for Delivery Table
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

    public void initialize() {
        // Intialize the Delivery Table Columns
        deliveryIdColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryID"));
        deliveryPostcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        deliveryDayColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));
        deliveryCostColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));
    }

    // Constructor for ProductViewController
    public ScheduleViewController(Client client) {
        this.client = client;
    }

    @FXML
    private void handleViewCustomerDeliveries() {
        // Clear the Delivery Table
        clearCustomerDeliveryTable();

        // Get all Deliveries from the Database
        List<Delivery> deliveries = client.getAllDeliveries();

        // Set the Delivery Table with the Deliveries
        deliveryTable.getItems().setAll(deliveries);

    }

    public void clearCustomerDeliveryTable() {
        // Clear the Delivery Table of all Deliveries
        deliveryTable.getItems().clear();
    }
}
