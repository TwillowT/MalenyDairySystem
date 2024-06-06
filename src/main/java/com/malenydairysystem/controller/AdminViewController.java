package com.malenydairysystem.controller;

// Imports
import java.sql.Date;
import java.util.List;

import com.malenydairysystem.Utilities;
import com.malenydairysystem.client.Client;
import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.Delivery;
import com.malenydairysystem.model.Order;
import com.malenydairysystem.model.OrderLine;
import com.malenydairysystem.model.Product;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the Admin View in the application for Admins to manage Products, Deliveries, Customers, Orders and Order Lines.
 */
public class AdminViewController
{

    // Declaration of Client object
    private Client client;// Manages communication with server-side application

    // UI components for product details
    @FXML
    private TextField productName;
    @FXML
    private TextField productQuantity;
    @FXML
    private TextField productUnit;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productIngredients;

    // UI components for product table display
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;
    @FXML
    private TableColumn<Product, String> productUnitColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, String> productIngredientsColumn;

    // UI components for delivery management
    @FXML
    private ComboBox<Integer> deliveryPostcode;
    @FXML
    private ComboBox<String> deliveryDay;
    @FXML
    private TextField deliveryCost;

    // UI components for delivery table display
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

    // UI components for customer table display
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer, String> customerEmailColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    // UI components for order and order line management
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, Integer> orderCustomerColumn;
    @FXML
    private TableColumn<Order, Double> orderTotalColumn;
    @FXML
    private TableColumn<Order, Date> orderDateColumn;    
    @FXML
    private TableView<OrderLine> orderLineTable;
    @FXML
    private TableColumn<OrderLine, Integer> orderLineIdColumn;
    @FXML
    private TableColumn<OrderLine, Integer> orderLineProductColumn;
    @FXML
    private TableColumn<OrderLine, Integer> orderLineQuantityColumn;
    @FXML
    private TableColumn<OrderLine, Double> orderLinePriceColumn;
    @FXML
    private TableColumn<OrderLine, Double> orderLineTotalColumn;

    // Constructor initializes client
    public AdminViewController(Client client)
    {
        // Set the Client Object
        this.client = client;
    }

    // Initializes table column bindings and event listeners
    @FXML
    public void initialize()
    {
        // Intialize the Product Table Columns
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productUnitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIngredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        // Intialize the Delivery Table Columns
        deliveryIdColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryID"));
        deliveryPostcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        deliveryDayColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));
        deliveryCostColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));

        // Intialize the Customer Table Columns
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));

        // Intialize the Order Table Columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        // Intialize the OrderLine Table Columns
        orderLineIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderLineProductColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        orderLineQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderLinePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderLineTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Add a listener to the product table to update form fields when a product is selected
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            // Check if the Selection is not Null
            if (newSelection != null)
            {
                // Get the Selected Product from the Product Table
                Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
                productName.setText(selectedProduct.getProductName());
                productQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
                productUnit.setText(selectedProduct.getUnit());
                productPrice.setText(String.valueOf(selectedProduct.getPrice()));
                productIngredients.setText(selectedProduct.getIngredients());
            }
        });

        // Populate delivery postcode combobox with available postcodes
        List<Integer> postcodes = client.getDeliveryPostcodes();
        deliveryPostcode.getItems().setAll(postcodes);

        // Populate delivery day combobox with days of the week to update form fields when a delivery is selected
        deliveryDay.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        // Add a Listener to the Delivery Table
        deliveryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            // Check if the Selection is not Null
            if (newSelection != null)
            {
                // Get the Selected Delivery from the Delivery Table
                Delivery selectedDelivery = deliveryTable.getSelectionModel().getSelectedItem();
                deliveryPostcode.getSelectionModel().select(Integer.valueOf(selectedDelivery.getPostCode()));
                deliveryDay.getSelectionModel().select(selectedDelivery.getDeliveryDay());
                deliveryCost.setText(String.valueOf(selectedDelivery.getDeliveryCost()));
            }
        });

        // Add a Listener to the Delivery Postcode ComboBox to update delivery cost when postcode changes
        deliveryPostcode.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            // Check if the Selection is not Null
            if (newSelection != null)
            {
                // Handle the Get Delivery Cost Change Action
                handleGetDeliveryCostChangeAction();
            }
        });

        // Add a Listener to the Order Table to trigger viewing all order lines for the selected order
        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            // Check if the Selection is not Null
            if (newSelection != null)
            {
                // Handle the View All Order Lines Button Action
                handleViewAllOrderLinesButtonAction();
            }
        });
    }

    // Handle the Add Product Button Action for Product
    @FXML
    public void handleAddProductButtonAction()
    {
        // Check if the Product Name is Empty
        if (productName.getText().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Product Name is required.");
            return;
        }
        // Check if the Product Quantity is Empty or not a Number
        else if (productQuantity.getText().isEmpty() || !productQuantity.getText().matches("[0-9]+"))
        {
            // Show an Error Message
            Utilities.showError("Product Quantity is required and must be a Number.");
            return;
        }
        // Check if the Product Unit is Empty
        else if (productUnit.getText().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Product Unit is required.");
            return;
        }
        // Check if the Product Price is Empty or not a Decimal Number
        else if (productPrice.getText().isEmpty() || !productPrice.getText().matches("[0-9]+" + "." + "[0-9]+"))
        {
            // Show an Error Message
            Utilities.showError("Product Price is required and must be a Decimal Number.");
            return;
        }
        // Check if the Product Ingredients is Empty
        else if (productIngredients.getText().isEmpty())
        {
             // Display an error message if the Product Ingredients field is empty
            Utilities.showError("Product Ingredients is required.");
            return;
        }

        //  Create a new Product Object
        Product product = new Product();
        product.setProductName(productName.getText());
        product.setQuantity(Integer.parseInt(productQuantity.getText()));
        product.setUnit(productUnit.getText());
        product.setPrice(Double.parseDouble(productPrice.getText()));
        product.setIngredients(productIngredients.getText());

        // Add the Product to the Database and get the Result
        boolean result = client.addProduct(product);

        // Check if the Result is True
        if (result)
        {
            // Show that the Produt was added Successfully
            Utilities.showInformation("Product Added Successfully.");
            clearProductFields();
        }
        else
        {
            // Show that the Product was not added Successfully
            Utilities.showError("Error when adding a Product. Please try again.");
        }

        // Refresh the Product Table
        handleViewAllProductsButtonAction();
    }

    // Handle the Update Product Button Action for Product
    @FXML
    public void handleUpdateProductButtonAction()
    {
        // Get the Selected Product from the Product Table
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int productId = selectedProduct.getProductID();

        // Check if the Product Name is Empty
        if (productName.getText().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Product Name is required.");
            return;
        }
        // Check if the Product Quantity is Empty or not a Number        
        else if (productQuantity.getText().isEmpty() || !productQuantity.getText().matches("[0-9]+"))
        {
            // Show an Error Message
            Utilities.showError("Product Quantity is required and must be a Number.");
            return;
        }
        // Check if the Product Unit is Empty
        else if (productUnit.getText().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Product Unit is required.");
            return;
        }
        // Check if the Product Price is Empty or not a Decimal Number        
        else if (productPrice.getText().isEmpty() || !productPrice.getText().matches("[0-9]+" + "." + "[0-9]+"))
        {
            // Show an Error Message
            Utilities.showError("Product Price is required and must be a Decimal Number.");
            return;
        }
        // Check if the Product Ingredients is Empty
        else if (productIngredients.getText().isEmpty())
        {
            // Show an Error Message 
            Utilities.showError("Product Ingredients is required.");
            return;
        }

        // Create a new Product Object
        Product product = new Product();
        product.setProductID(productId);
        product.setProductName(productName.getText());
        product.setQuantity(Integer.parseInt(productQuantity.getText()));
        product.setUnit(productUnit.getText());
        product.setPrice(Double.parseDouble(productPrice.getText()));
        product.setIngredients(productIngredients.getText());

        // Update the Product in the Database and get the Result
        boolean result = client.updateProduct(product);

        // Check if the Result is True
        if (result)
        {
            // Show that the Produt was updated Successfully
            Utilities.showInformation("Product Updated Successfully.");
            clearProductFields();
        }
        // Else display an error message if the result is false
        else
        {
            // Show that the Product was not updated Successfully
            Utilities.showError("Error when updating a Product. Please try again.");
        }

        // Refresh the Product Table
        handleViewAllProductsButtonAction();
    }

    // Handle the Remove Product Button Action for Product
    @FXML
    public void handleRemoveProductButtonAction()
    {
        // Get the Selected Product from the Product Table
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int productId = selectedProduct.getProductID();

        // Show a Warning Message to Confirm the Removal of the Product
        boolean confirm = Utilities.showWarning("Are you sure you want to remove the Product?");

        // Check if the User Confirmed the Removal
        if (confirm)
        {
            // Remove the Product from the Database and get the Result
            boolean result = client.removeProduct(productId);

            // Check if the Result is True
            if (result)
            {
                // Show that the Product was removed Successfully
                Utilities.showInformation("Product Removed Successfully.");
                clearProductFields();
            }
            else
            {
                // Show that the Product was not removed Successfully
                Utilities.showError("Error when removing a Product. Please try again.");
            }
        }

        // Refresh the Product Table
        handleViewAllProductsButtonAction();
    }

    // Handle the View All Products Button Action for Product
    @FXML
    public void handleViewAllProductsButtonAction()
    {
        // Clear the Product Table
        clearProductTable();

        // Get all Products from the Database
        List<Product> products = client.getAllProducts();

        // Set the Product Table with the Products
        productTable.getItems().setAll(products);
    }

    // Handle the Add Delivery Button Action for Delivery
    @FXML
    public void handleAddDeliveryButtonAction()
    {
        // Check if the Delivery Postcode is Empty
        if (deliveryPostcode.getSelectionModel().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Delivery Postcode is required.");
            return;
        }
        // Check if the Delivery Day is Empty
        else if (deliveryDay.getSelectionModel().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Delivery Day is required.");
            return;
        }

        // Create a new Delivery Object
        Delivery delivery = new Delivery();
        delivery.setPostCode(deliveryPostcode.getSelectionModel().getSelectedItem());
        delivery.setDeliveryDay(deliveryDay.getSelectionModel().getSelectedItem());
        delivery.setDeliveryCost(Double.parseDouble(deliveryCost.getText()));

        // Add the Delivery to the Database and get the Result
        boolean result = client.addDelivery(delivery);

        // Check if the Result is True
        if (result)
        {
            // Show that the Delivery was added Successfully
            Utilities.showInformation("Delivery Schedule Added Successfully.");
            clearDeliveryFields();
        }
        else
        {
            // Show that the Delivery was not added Successfully
            Utilities.showError("Error when adding a Delivery Schedule. Please try again.");
        }

        // Refresh the Delivery Table
        handleViewAllDeliveriesButtonAction();
    }

    // Handle the Update Delivery Button Action for Delivery
    @FXML
    public void handleUpdateDeliveryButtonAction()
    {
        // Get the Selected Delivery from the Delivery Table
        Delivery selectedDelivery = deliveryTable.getSelectionModel().getSelectedItem();
        int deliveryId = selectedDelivery.getDeliveryID();

        // Check if the Delivery Postcode is Empty
        if (deliveryPostcode.getSelectionModel().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Delivery Postcode is required.");
            return;
        }
        // Check if the Delivery Day is Empty        
        else if (deliveryDay.getSelectionModel().isEmpty())
        {
            // Show an Error Message
            Utilities.showError("Delivery Day is required.");
            return;
        }

        // Create a new Delivery Object
        Delivery delivery = new Delivery();
        delivery.setDeliveryID(deliveryId);
        delivery.setPostCode(deliveryPostcode.getSelectionModel().getSelectedItem());
        delivery.setDeliveryDay(deliveryDay.getSelectionModel().getSelectedItem());
        delivery.setDeliveryCost(Double.parseDouble(deliveryCost.getText()));

        // Update the Delivery in the Database and get the Result
        boolean result = client.updateDelivery(delivery);

        // Check if the Result is True
        if (result)
        {
            // Show that the Delivery was updated Successfully
            Utilities.showInformation("Delivery Schedule Updated Successfully.");
            clearDeliveryFields();
        }
        else
        {
            // Show that the Delivery was not updated Successfully
            Utilities.showError("Error when updating a Delivery Schedule. Please try again.");
        }

        // Refresh the Delivery Table
        handleViewAllDeliveriesButtonAction();
    }

    // Handle the Remove Delivery Button Action for Delivery
    @FXML
    public void handleRemoveDeliveryButtonAction()
    {
        // Get the Selected Delivery from the Delivery Table
        Delivery selectedDelivery = deliveryTable.getSelectionModel().getSelectedItem();
        int deliveryId = selectedDelivery.getDeliveryID();

        // Show a Warning Message to Confirm the Removal of the Delivery
        boolean confirm = Utilities.showWarning("Are you sure you want to remove the Delivery Schedule?");

        // Check if the User Confirmed the Removal
        if (confirm)
        {
            // Remove the Delivery from the Database and get the Result
            boolean result = client.removeDelivery(deliveryId);

            // Check if the Result is True
            if (result)
            {
                // Show that the Delivery was removed Successfully
                Utilities.showInformation("Delivery Schedule Removed Successfully.");
                clearDeliveryFields();
            }
            else
            {
                // Show that the Delivery was not removed Successfully
                Utilities.showError("Error when removing a Delivery Schedule. Please try again.");
            }
        }

        // Refresh the Delivery Table
        handleViewAllDeliveriesButtonAction();
    }

    // Handle the View All Deliveries Button Action for Delivery
    @FXML
    public void handleViewAllDeliveriesButtonAction()
    {
        // Clear the Delivery Table
        clearDeliveryTable();

        // Get all Deliveries from the Database
        List<Delivery> deliveries = client.getAllDeliveries();

        // Set the Delivery Table with the Deliveries
        deliveryTable.getItems().setAll(deliveries);
    }

    // Handle the Get Delivery Cost Change Action for Delivery
    @FXML
    public void handleGetDeliveryCostChangeAction()
    {
        // Get the Selected Postcode from the Delivery Postcode ComboBox
        int postcode = deliveryPostcode.getSelectionModel().getSelectedItem();

        // Get the Delivery Cost from the Database
        double cost = client.getDeliveryCost(postcode);

        // Set the Delivery Cost TextField with the Cost
        deliveryCost.setText(String.valueOf(cost));
    }

    // Handle the View All Customers Button Action for Customer
    @FXML
    public void handleViewAllCustomersButtonAction()
    {
        // Clear the Customer Table
        clearCustomerTable();

        // Get all Customers from the Database
        List<Customer> customers = client.getAllCustomers();

        // Set the Customer Table with the Customers
        customerTable.getItems().setAll(customers);
    }

    // Handle the View All Orders Button Action for Order
    @FXML
    public void handleViewAllOrdersButtonAction()
    {
        // Clear the Order Table
        clearOrderTable();

        // Get all Orders from the Database
        List<Order> orders = client.getAllOrders();

        // Set the Order Table with the Orders
        orderTable.getItems().setAll(orders);
    }

    // Handle the View All Order Lines Button Action for Order
    @FXML
    public void handleViewAllOrderLinesButtonAction()
    {
        // Get the Selected Order from the Order Table
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        int orderId = selectedOrder.getOrderID();

        // Get all Order Lines by Order ID from the Database
        List<OrderLine> orderLines = client.getOrderLinesByOrderId(orderId);

        // Set the Order Line Table with the Order Lines
        orderLineTable.getItems().setAll(orderLines);
    }

    // Clear the Product Fields
    @FXML
    public void clearProductFields()
    {
        // Clear the Product Values from the GUI
        productName.clear();
        productQuantity.clear();
        productUnit.clear();
        productPrice.clear();
        productIngredients.clear();
    }

    // Clear the Delivery Fields
    @FXML
    public void clearDeliveryFields()
    {
        // Clear the Delivery Values from the GUI
        deliveryPostcode.getSelectionModel().clearSelection();
        deliveryDay.getSelectionModel().clearSelection();
        deliveryCost.clear();
    }

    // Clear the Product Table
    @FXML
    public void clearProductTable()
    {
        // Clear the Product Table of all Products
        productTable.getItems().clear();
    }

    // Clear the Delivery Table
    @FXML
    public void clearDeliveryTable()
    {
        // Clear the Delivery Table of all Deliveries
        deliveryTable.getItems().clear();
    }

    // Clear the Customer Table
    @FXML
    public void clearCustomerTable()
    {
        // Clear the Customer Table of all Customers
        customerTable.getItems().clear();
    }

    // Clear the Order Table
    @FXML
    public void clearOrderTable()
    {
        // Clear the Order Table of all Orders
        orderTable.getItems().clear();

        // Clear the Order Line Table of all Order Lines
        orderLineTable.getItems().clear();
    }
}
