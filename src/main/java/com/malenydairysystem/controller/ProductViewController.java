package com.malenydairysystem.controller;

import java.util.List;

import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.Product;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages product interactions in the application.
 */
public class ProductViewController
{
    // Declaration for Client object
    private Client client;
    
    @FXML
    private TableView<Product> productTable; // Table to display products 
    @FXML
    private TableColumn<Product, Integer> idColumn; // Column to display product ID
    @FXML
    private TableColumn<Product, String> nameColumn; // Column to display name
    @FXML
    private TableColumn<Product, Integer> quantityColumn; // Column to display quantity
    @FXML
    private TableColumn<Product, String> unitColumn; // Column to display unit
    @FXML
    private TableColumn<Product, Double> unitPriceColumn; // Column to display unit price
    @FXML
    private TableColumn<Product, String> ingredientsColumn; // Column to display ingredients

    // Constructor for ProductViewController
    public ProductViewController(Client client)
    {
        this.client = client;
    }
    
    // Method to initialize the table
    @FXML
    public void initialize()
    {
        // Set cell value factory for each column using property names from the Product class
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        
       loadProducts(); // Load products into the table
    }
    
    // Method to fetch Products from the server 
    private void loadProducts(){
        List<Product> products = client.getAllProducts(); // Retieve list of products from the client
        System.out.println("Products retrieved from database: " + products.size()); // Print number of product retrieved 
        productTable.getItems().setAll(products); // Set the itesm in the product table to retrieved products       
    }  
    
    
}
