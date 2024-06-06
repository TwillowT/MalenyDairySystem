package com.malenydairysystem.controller;

import com.malenydairysystem.Utilities;
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
    Description:    Manages the Order View in the application for customers to place Orders.
 */
public class OrderViewController
{

    // Declaration for Client object
    private Client client;

    // Declaration for Customer object
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

    // FXML References
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

    // FXML References
    @FXML
    private ChoiceBox<String> choicePostcode;

    // Constructor for OrderViewController
    public OrderViewController(Client client, User user)
    {
        // Set the Client Object
        this.client = client;

        // Set the Customer Object
        customer = (Customer) user;
    }

    // Method to initialize the table
    @FXML
    public void initialize()
    {
        // Set cell value factory for each column using property names from the Product class
        nameProductColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitProductColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityProductColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceProductColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ingredientsProductColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        // Set cell value factory for each column using property names from the OrderLine class
        nameOrderColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityOrderColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceOrderColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalOrderColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        gstOrderColumn.setCellValueFactory(new PropertyValueFactory<>("gst"));
        totalGSTOrderColumn.setCellValueFactory(new PropertyValueFactory<>("totalGST"));

        // Set cell factory for quantity column to allow editing
        quantityOrderColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityOrderColumn.setOnEditCommit(new EventHandler<CellEditEvent<OrderLine, Integer>>()
        {
            // Method to handle the event
            @Override
            public void handle(CellEditEvent<OrderLine, Integer> event)
            {
                // Get the row value
                OrderLine od = event.getRowValue();

                // Set the new value
                od.setQuantity(event.getNewValue());

                // Update the total
                orderTable.getItems().setAll(orderLines);
            }
        });

        // Set the order and orderLines
        order = new Order();
        orderLines = new ArrayList<>();

        // Load the products
        loadProducts();

        // Load the postcodes
        List<Integer> postcodes = client.getDeliveryPostcodes();

        // Add the postcodes to the choice box
        for (Integer postcode : postcodes)
        {
            // Add the postcodes to the choice box
            choicePostcode.getItems().add(postcode.toString());
        }

        // Set the first postcode as the default
        choicePostcode.setValue(postcodes.get(0).toString());
    }

    // Method to fetch Products from the server 
    private void loadProducts()
    {
        // Retieve list of products from the client
        products = client.getAllProducts();

        // Print number of product retrieved 
        System.out.println("Products retrieved from database: " + products.size());

        // Set the itesm in the product table to retrieved products
        productTable.getItems().setAll(products);
    }

    // Method to handle the Add button
    @FXML
    private void btnAddOnClick(ActionEvent event)
    {
        // Check if a product is selected
        if (productTable.getSelectionModel().getSelectedItem() != null)
        {
            // Get the selected product
            Product product = productTable.getSelectionModel().getSelectedItem();

            // Test to check for existing product 
            boolean contains = false;

            for (OrderLine od : orderLines)
            {
                // Check if the product is already in the order
                if (od.getProductID() == product.getProductID())
                {
                    contains = true;
                }
            }

            // If the product is not in the order
            if (!contains)
            {
                // Create a new order line
                OrderLine od = new OrderLine(0, product.getProductID(), 1, product.getPrice(), 0);
                od.setProductName(product.getProductName());
                orderLines.add(od);

                // Set the items in the order table
                orderTable.getItems().setAll(orderLines);
            }
        }
    }

    // Method to handle the Remove button
    @FXML
    private void btnRemoveOnClick(ActionEvent event)
    {
        // Check if an order line is selected
        if (orderTable.getSelectionModel().getSelectedItem() != null)
        {
            // Get the selected order line
            OrderLine od = orderTable.getSelectionModel().getSelectedItem();

            // Remove the order line
            orderLines.remove(od);
            orderTable.getItems().setAll(orderLines);
        }
    }

    // Method to handle the Place Order button
    @FXML
    private void btnPlaceOrderOnClick(ActionEvent event)
    {
        if (!orderLines.isEmpty())
        {
            // Determine cost
            double deliveryCost = client.getDeliveryCost(Integer.parseInt(choicePostcode.getValue()));
            double total = deliveryCost;
            for (OrderLine od : orderLines)
            {
                total += od.getTotal();
            }

            DecimalFormat df = new DecimalFormat("0.00");
            total = Double.parseDouble(df.format(total * 1.1)); // Add gst and round to 2 decimal places

            // Set order values
            order.setCustomerID(customer.getCustomerID());
            order.setTotalPrice(total);

            // SQL Date Setup
            long millis = System.currentTimeMillis();
            order.setOrderDate(new Date(millis));

            // Show the Order Summary and allow the Customer to accept it
            boolean acceptOrder = Utilities.showOrderSummary(order, orderLines, deliveryCost);

            // If the Customer accepts the Order
            if (acceptOrder)
            {
                // Add the Order
                order = client.addOrder(order);

                // Add the Order Lines
                for (OrderLine od : orderLines)
                {
                    od.setOrderID(order.getOrderID());
                    client.addOrderLine(od);
                }

                    // Show Information Alert
                    Utilities.showInformation("Order placed Successfully.");
            }
            else
            {
                // Show Error Alert
                Utilities.showError("Your Order has not been placed.");
            }

            clearData();
        }
    }

    // Method to handle the Clear button
    private void clearData()
    {
        // Clear the order and orderLines
        order = new Order();
        orderLines.clear();
        orderTable.getItems().setAll(orderLines);
    }
}
