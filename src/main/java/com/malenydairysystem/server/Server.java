package com.malenydairysystem.server;

import com.malenydairysystem.database.DatabaseManager;
import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.Delivery;
import com.malenydairysystem.model.Order;
import com.malenydairysystem.model.OrderLine;
import com.malenydairysystem.model.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Handles all Server side operations for the program, receiving requests from the client and processing
                    them, and returning results to program controllers.
 */
public class Server
{

    private static final int SERVER_PORT = 5586;

    public static void main(String[] args) throws SQLException
    {
        // Create the DatabaseUtility Object
        DatabaseManager database = new DatabaseManager();

        // Create the Database
        database.createDatabase();

        // Create the Database Tables
        database.createTables();

        // Insert the Product Data from the CSV
        database.insertDataFromCSV("A3-products.csv");

        // Insert the Delivery Cost Data from the CSV
        database.insertDataFromCSV("A3-delivery-cost.csv");

        try
        {
            // Create a new Server Socket Object
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server Started and Listening on Port " + SERVER_PORT);

            while (true)
            {
                // Accept the Client Connection
                Socket clientSocket = serverSocket.accept();
                new ServerConnection(clientSocket);
            }
        }
        catch (IOException e)
        {
            // Print the Stack Trace
            e.printStackTrace();
        }
    }
}

// Server Connection Class
class ServerConnection extends Thread
{

    // Client Socket
    Socket clientSocket;

    // Object Input and Output Streams
    ObjectInputStream serverInput;
    ObjectOutputStream serverOutput;

    // DatabaseManager instance
    DatabaseManager databaseManager;

    // Constructor for the Server Connection
    public ServerConnection(Socket clientSocket)
    {
        try
        {
            // Set the Client Socket
            this.clientSocket = clientSocket;

            // Initialize the DatabaseManager
            this.databaseManager = new DatabaseManager();

            // Connect to the Database
            this.databaseManager.connectDatabase();

            // Create the Object Input and Output Streams
            serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            serverInput = new ObjectInputStream(clientSocket.getInputStream());

            // Start the Thread
            this.start();
        }
        catch (IOException e)
        {
            // Print the Stack Trace
            e.printStackTrace();
        }
    }

    // Run Method
    public void run()
    {
        try
        {
            // Loop to Receive Requests from the Client
            while (true)
            {
                // Read the Request Type
                String requestType = (String) serverInput.readObject();

                // Switch Statement to Handle the Request
                switch (requestType)
                {
                    // Add Product Request
                    case "ADD_PRODUCT":
                        Product product = (Product) serverInput.readObject();
                        boolean addResult = databaseManager.addProduct(product);
                        serverOutput.writeObject(addResult);
                        break;

                    // Update Product Request    
                    case "UPDATE_PRODUCT":
                        Product updatedProduct = (Product) serverInput.readObject();
                        boolean updateResult = databaseManager.updateProduct(updatedProduct);
                        serverOutput.writeObject(updateResult);
                        break;

                    // Remove Product Request
                    case "REMOVE_PRODUCT":
                        int productId = (int) serverInput.readObject();
                        boolean removeResult = databaseManager.removeProduct(productId);
                        serverOutput.writeObject(removeResult);
                        break;

                    // Get All Products Request
                    case "GET_ALL_PRODUCTS":
                        List<Product> products = databaseManager.getAllProducts();
                        serverOutput.writeObject(products);
                        serverOutput.reset();
                        break;

                    // Add Delivery Request
                    case "ADD_DELIVERY":
                        Delivery delivery = (Delivery) serverInput.readObject();
                        boolean addDeliveryResult = databaseManager.addDelivery(delivery);
                        serverOutput.writeObject(addDeliveryResult);
                        break;

                    // Update Delivery Request
                    case "UPDATE_DELIVERY":
                        Delivery updatedDelivery = (Delivery) serverInput.readObject();
                        boolean updateDeliveryResult = databaseManager.updateDelivery(updatedDelivery);
                        serverOutput.writeObject(updateDeliveryResult);
                        break;

                    // Remove Delivery Request
                    case "REMOVE_DELIVERY":
                        int deliveryId = (int) serverInput.readObject();
                        boolean removeDeliveryResult = databaseManager.removeDelivery(deliveryId);
                        serverOutput.writeObject(removeDeliveryResult);
                        break;

                    // Get All Deliveries Request
                    case "GET_ALL_DELIVERIES":
                        List<Delivery> deliveries = databaseManager.getAllDeliveries();
                        serverOutput.writeObject(deliveries);
                        serverOutput.reset();
                        break;

                    // Get Delivery Postcodes Request
                    case "GET_DELIVERY_POSTCODES":
                        List<Integer> deliveryPostcodes = databaseManager.getDeliveryPostcodes();
                        serverOutput.writeObject(deliveryPostcodes);
                        serverOutput.reset();
                        break;

                    // Get Delivery Cost Request
                    case "GET_DELIVERY_COST":
                        int postcode = (int) serverInput.readObject();
                        double deliveryCost = databaseManager.getDeliveryCost(postcode);
                        serverOutput.writeObject(deliveryCost);
                        break;

                    // Get All Customers Request
                    case "GET_ALL_CUSTOMERS":
                        List<Customer> customers = databaseManager.getAllCustomers();
                        serverOutput.writeObject(customers);
                        serverOutput.reset();
                        break;

                    // Get All Orders Request
                    case "GET_ALL_ORDERS":
                        List<Order> orders = databaseManager.getAllOrders();
                        serverOutput.writeObject(orders);
                        serverOutput.reset();
                        break;

                    // Get Order Lines Request
                    case "GET_ORDER_LINES":
                        int orderId = (int) serverInput.readObject();
                        List<OrderLine> orderLines = databaseManager.getOrderLinesByOrderId(orderId);
                        serverOutput.writeObject(orderLines);
                        serverOutput.reset();
                        break;
                }
            }
        }
        catch (Exception e)
        {
            // Print the Stack Trace
            e.printStackTrace();
        }
    }
}
