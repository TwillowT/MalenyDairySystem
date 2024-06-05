package com.malenydairysystem.controller;

import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.Product;
import com.malenydairysystem.model.Order;
import com.malenydairysystem.model.OrderLine;
import com.malenydairysystem.model.User;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages order interactions in the application.
 */
public class OrderViewController {

    // Declaration for Client object
    private Client client;
    
    private Customer customer;

    // Variables
    private Order order;
    private List<Product> products;
    private List<OrderLine> orderLines;

    // FXML References
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameProductColumn;
    @FXML
    private TableColumn<Product, String> unitProductColumn;
    @FXML
    private TableColumn<Product, Integer> quantityProductColumn;
    @FXML
    private TableColumn<Product, Double> unitPriceProductColumn;
    @FXML
    private TableColumn<Product, String> ingredientsProductColumn;

    @FXML
    private TableView<OrderLine> orderTable;
    @FXML
    private TableColumn<OrderLine, String> nameOrderColumn;
    @FXML
    private TableColumn<OrderLine, Integer> quantityOrderColumn;
    @FXML
    private TableColumn<OrderLine, Double> unitPriceOrderColumn;
    @FXML
    private TableColumn<OrderLine, Double> totalOrderColumn;
    @FXML
    private TableColumn<OrderLine, Double> gstOrderColumn;
    @FXML
    private TableColumn<OrderLine, Double> totalGSTOrderColumn;

    @FXML
    private ChoiceBox<String> choicePostcode;

    // Constructor for OrderViewController
    public OrderViewController(Client client, User user) {
        this.client = client;
        customer = (Customer) user;
    }

    // Method to initialize the table
    @FXML
    public void initialize() {
        // Set cell value factory for each column using property names from the Product class
        nameProductColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitProductColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityProductColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceProductColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ingredientsProductColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        nameOrderColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityOrderColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceOrderColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalOrderColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        gstOrderColumn.setCellValueFactory(new PropertyValueFactory<>("gst"));
        totalGSTOrderColumn.setCellValueFactory(new PropertyValueFactory<>("totalGST"));
        

        quantityOrderColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityOrderColumn.setOnEditCommit(new EventHandler<CellEditEvent<OrderLine, Integer>>() {
            @Override
            public void handle(CellEditEvent<OrderLine, Integer> event) {
                OrderLine od = event.getRowValue();
                od.setQuantity(event.getNewValue());
                orderTable.getItems().setAll(orderLines);
            }
        });

        order = new Order();
        orderLines = new ArrayList<>();

        loadProducts(); // Load products into the system
        

        List<Integer> postcodes = client.getDeliveryPostcodes();
        for (Integer postcode : postcodes) {
            choicePostcode.getItems().add(postcode.toString());
        }
        choicePostcode.setValue(postcodes.get(0).toString());

        //List<String> postcodes = client.getAllPostcodes();
       // for (String postcode : postcodes) {
        //    choicePostcode.getItems().add(postcode);
       // }
        //deliveryCosts = client.getAllDeliveryCosts();
       // choicePostcode.setValue(postcodes.get(0));

    }

    // Method to fetch Products from the server 
    private void loadProducts() {
        products = client.getAllProducts(); // Retieve list of products from the client
        System.out.println("Products retrieved from database: " + products.size()); // Print number of product retrieved 
        productTable.getItems().setAll(products); // Set the itesm in the product table to retrieved products
    }

    @FXML
    private void btnAddOnClick(ActionEvent event) {
        if (productTable.getSelectionModel().getSelectedItem() != null) {
            Product product = productTable.getSelectionModel().getSelectedItem();

            // Test to check for existing product 
            boolean contains = false;
            for (OrderLine od : orderLines) {
                if (od.getProductID() == product.getProductID()) {
                    contains = true;
                }
            }

            // Deny if it contains the product
            if (!contains) {
                OrderLine od = new OrderLine(0, product.getProductID(), 1, product.getPrice(), 0);
                od.setProductName(product.getProductName());
                orderLines.add(od);

                orderTable.getItems().setAll(orderLines);
            }
        }
    }

    @FXML
    private void btnRemoveOnClick(ActionEvent event) {
        if (orderTable.getSelectionModel().getSelectedItem() != null) {
            OrderLine od = orderTable.getSelectionModel().getSelectedItem();
            orderLines.remove(od);
            orderTable.getItems().setAll(orderLines);
        }
    }

    @FXML
    private void btnPlaceOrderOnClick(ActionEvent event) {
        if (!orderLines.isEmpty()) {
            // Determine cost
            double deliveryCost = client.getDeliveryCost(Integer.parseInt(choicePostcode.getValue()));
            double total = deliveryCost;
            for (OrderLine od : orderLines) {
                total += od.getTotal();
            }
            
            DecimalFormat df = new DecimalFormat("0.00");
            total = Double.parseDouble(df.format(total * 1.1)); // Add gst and round to 2 decimal places
            
            // Set order values
            order.setCustomerID(customer.getCustomerID());
            order.setTotalPrice(total);
            
            // SQL Date Setup
            long millis=System.currentTimeMillis();  
            order.setOrderDate(new Date(millis));
            
            // Update Order
            order = client.addOrder(order);
            
            for (OrderLine od : orderLines) {
                od.setOrderID(order.getOrderID());
                client.addOrderLine(od);
            }
            
            clearData();
        }
    }
    
    private void clearData() {
        order = new Order();
        orderLines.clear();
        orderTable.getItems().setAll(orderLines);
    }
}
