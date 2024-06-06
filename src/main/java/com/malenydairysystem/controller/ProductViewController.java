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
    Description:    Manage the Product View in the application for users to view Products.
 */
public class ProductViewController
{

    // Declaration for Client object
    private Client client;

    // FXML References for Product Table
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> unitColumn;
    @FXML
    private TableColumn<Product, Double> unitPriceColumn;
    @FXML
    private TableColumn<Product, String> ingredientsColumn;

    // Constructor for ProductViewController
    public ProductViewController(Client client)
    {
        // Set the Client Object
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

        // Load products into the table
        loadProducts();
    }

    // Method to fetch Products from the server 
    private void loadProducts()
    {
        // Retieve list of products from the client
        List<Product> products = client.getAllProducts();

        // Print number of product retrieved 
        System.out.println("Products retrieved from database: " + products.size());

        // Set the itesm in the product table to retrieved products
        productTable.getItems().setAll(products);       
    }

}
