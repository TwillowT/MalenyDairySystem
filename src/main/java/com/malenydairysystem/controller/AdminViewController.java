package com.malenydairysystem.controller;

import java.util.List;

import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.Product;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the functions of the Admin GUI of the program, passing data to the client, server and database
                    and processing responses.
 */
public class AdminViewController
{

    // Declaration of Client object
    private Client client;

    @FXML
    private Button addProduct;
    @FXML
    private Button updateProduct;
    @FXML
    private Button removeProduct;
    @FXML
    private Button viewProduct;

    @FXML
    private TableView<Product> productsTable;
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

    // Constructor for the AdminViewController
    public AdminViewController(Client client)
    {
        this.client = client;
    }

    @FXML
    public void initialize()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
    }

    @FXML
    public void handleAddProductButtonAction()
    {
        // Add Product
    }

    @FXML
    public void handleUpdateProductButtonAction()
    {
        // Update Product
    }

    @FXML
    public void handleRemoveProductButtonAction()
    {
        // Remove Product
    }

    @FXML
    public void handleViewAllProductsButtonAction()
    {
        clearTable();

        List<Product> products = client.getAllProducts();
        productsTable.getItems().setAll(products);
    }

    @FXML
    public void clearTable()
    {
        productsTable.getItems().clear();
    }
}
