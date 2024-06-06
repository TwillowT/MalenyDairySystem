package com.malenydairysystem.server;

import com.malenydairysystem.database.DatabaseManager;
import com.malenydairysystem.model.Admin;
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
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Handles all Server side operations for the program, receiving requests from the client and processing
                    them, and returning results to program controllers.
 */
public class Server
{

    // Server Port
    private static final int SERVER_PORT = 5586;

    public static void main(String[] args) throws SQLException
    {
        // Create the Database Manager Object
        DatabaseManager database = new DatabaseManager();

        // Output the Database Configuration
        System.out.println(outputBreak());
        System.out.println("Configuring Database");
        System.out.println(outputBreak());

        // Create the Database
        database.createDatabase();

        // Create the Database Tables
        database.createTables();

        // Output the Data Insertion
        System.out.println(outputBreak());
        System.out.println("Inserting Data into Database");
        System.out.println(outputBreak());

        // Check if the Product Data has already been Inserted
        if (database.checkIfDataExists("products"))
        {
            // Notify that Product Data already exists
            System.out.println("Data already exists in the Product Table. CSV Data wont be Inserted.");
        }
        else
        {
            // Insert the Product Data from the CSV
            database.insertDataFromCSV("A3-products.csv");
        }

        // Check if the Delivery Cost Data has already been Inserted
        if (database.checkIfDataExists("delivery_costs"))
        {
            // Notify that Delivery Cost Data already exists
            System.out.println("Data already exists in the Delivery Cost Table. CSV Data wont be Inserted.");
        }
        else
        {
            // Insert the Delivery Cost Data from the CSV
            database.insertDataFromCSV("A3-delivery-cost.csv");
        }

        // Check if the Admin User has already been Inserted
        if (database.checkIfDataExists("admins"))
        {
            // Notify that Admin User already exists
            System.out.println("Data already exists in the Customer Table. Admin User wont be Inserted.");
        }
        else
        {
            // Insert the Admin User
            database.insertAdmin();
        }

        // Check if the Customer Data has already been Inserted
        if (database.checkIfDataExists("customers"))
        {
            // Notify that Customer Data already exists
            System.out.println("Data already exists in the Customer Table. Customer User wont be Inserted.");
        }
        else
        {
            // Insert the Customer Data from the CSV
            database.insertCustomer();
        }

        try
        {
            // Create a new Server Socket Object
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            // Output the Server Information
            System.out.println(outputBreak());
            System.out.println("Starting Maleny Dairy System Server");
            System.out.println(outputBreak());

            // Output the Server Port
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

    // Print Link Break to Console
    private static String outputBreak()
    {
        return ("========================================");
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

    // RSA Keys
    private PublicKey publicKey;
    private PrivateKey privateKey;

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

            // Generate RSA keys
            generateRSAKeys();

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

    // Method to Generate RSA Keys
    private void generateRSAKeys()
    {
        try
        {
            // Generate RSA Keys
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();

            // Set the Public and Private Keys
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
        }
        catch (Exception e)
        {
            // Print the Stack Trace
            e.printStackTrace();
        }
    }

    // Method to Decrypt Password
    private String decryptPassword(byte[] encryptedPassword) throws Exception
    {
        // Decrypt the Password using the Private Key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedPassword);
        return new String(decryptedBytes, "UTF-8");
    }

    // Method to Hash password
    private String hashPassword(String password) throws NoSuchAlgorithmException
    {
        // Hash the Password using SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Method to compare passwords during Login     
    private boolean checkPassword(String storedPassword, String providedPassword) throws NoSuchAlgorithmException
    {
        // Compare the Stored Password with the Provided Password
        String hashedProvidedPassword = hashPassword(providedPassword);
        return storedPassword.equals(hashedProvidedPassword);
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
                System.out.println("Received request type: " + requestType);

                // Switch Statement to Handle the Request
                switch (requestType)
                {
                    // Handle public key request
                    case "GET_PUBLIC_KEY":
                        // Send the server's public key to the client
                        byte[] keyBytes = publicKey.getEncoded();
                        serverOutput.writeObject(keyBytes);
                        break;

                    // Handle User Sign In
                    case "SIGN_IN_USER":
                        String userName = (String) serverInput.readObject();
                        byte[] encryptedLoginPassword = (byte[]) serverInput.readObject();

                        // Get the User Type
                        String userType = databaseManager.getUserType(userName);

                        // Authenticate the User
                        if (userType.equals("CUSTOMER"))
                        {
                            // Authenticate the Customer
                            Customer customer = databaseManager.authenticateCustomer(userName);

                            // Get the Stored Password
                            String storedPassword = customer.getPassword();

                            // Decrypt the Password
                            String decryptedPassword = decryptPassword(encryptedLoginPassword);

                            // Check if the Passwords Match
                            boolean loginSuccess = checkPassword(storedPassword, decryptedPassword);
                            if (loginSuccess)
                            {
                                // Send the Customer Object to the Client
                                serverOutput.writeObject(customer);
                            }
                            else
                            {
                                // Send a Null Object to the Client
                                serverOutput.writeObject(null);
                            }
                        }
                        // Authenticate the Admin
                        else if (userType.equals("ADMIN"))
                        {
                            // Authenticate the Admin
                            Admin admin = databaseManager.authenticateAdmin(userName);

                            // Get the Stored Password
                            String storedPassword = admin.getPassword();

                            // Decrypt the Password
                            String decryptedPassword = decryptPassword(encryptedLoginPassword);

                            // Check if the Passwords Match
                            boolean loginSuccess = checkPassword(storedPassword, decryptedPassword);
                            if (loginSuccess)
                            {
                                // Send the Admin Object to the Client
                                serverOutput.writeObject(admin);
                            }
                            else
                            {
                                // Send a Null Object to the Client
                                serverOutput.writeObject(null);
                            }
                        }
                        else
                        {
                            // Send a Null Object to the Client
                            serverOutput.writeObject(null);
                        }
                        break;

                    // Handle add Customer Request
                    case "ADD_CUSTOMER":
                        String name = (String) serverInput.readObject();
                        String address = (String) serverInput.readObject();
                        String phone = (String) serverInput.readObject();
                        String email = (String) serverInput.readObject();
                        String username = (String) serverInput.readObject();
                        byte[] encryptedPassword = (byte[]) serverInput.readObject();

                        String password = decryptPassword(encryptedPassword);

                        boolean addCustomerResult = databaseManager.addCustomer(name, address, phone, email, username, password);
                        serverOutput.writeObject(addCustomerResult);
                        break;

                    // Handle add Product Request
                    case "ADD_PRODUCT":
                        Product product = (Product) serverInput.readObject();
                        boolean addResult = databaseManager.addProduct(product);
                        serverOutput.writeObject(addResult);
                        break;

                    // Handle update Product Request    
                    case "UPDATE_PRODUCT":
                        Product updatedProduct = (Product) serverInput.readObject();
                        boolean updateResult = databaseManager.updateProduct(updatedProduct);
                        serverOutput.writeObject(updateResult);
                        break;

                    // Handle remove Product Request
                    case "REMOVE_PRODUCT":
                        int productId = (int) serverInput.readObject();
                        boolean removeResult = databaseManager.removeProduct(productId);
                        serverOutput.writeObject(removeResult);
                        break;

                    case "GET_ALL_PRODUCTS":
                        List<Product> products = databaseManager.getAllProducts();
                        serverOutput.writeObject(products);
                        break;

                    // Handle add Delivery request        
                    case "ADD_DELIVERY":
                        Delivery delivery = (Delivery) serverInput.readObject();
                        boolean addDeliveryResult = databaseManager.addDelivery(delivery);
                        serverOutput.writeObject(addDeliveryResult);
                        break;

                    // Handle update Delivery Request
                    case "UPDATE_DELIVERY":
                        Delivery updatedDelivery = (Delivery) serverInput.readObject();
                        boolean updateDeliveryResult = databaseManager.updateDelivery(updatedDelivery);
                        serverOutput.writeObject(updateDeliveryResult);
                        break;

                    // Handle remove Delivery Request
                    case "REMOVE_DELIVERY":
                        int deliveryId = (int) serverInput.readObject();
                        boolean removeDeliveryResult = databaseManager.removeDelivery(deliveryId);
                        serverOutput.writeObject(removeDeliveryResult);
                        break;

                    // Handle Get All Deliveries Request
                    case "GET_ALL_DELIVERIES":
                        List<Delivery> deliveries = databaseManager.getAllDeliveries();
                        serverOutput.writeObject(deliveries);
                        serverOutput.reset();
                        break;

                    // Handle Get Delivery Postcodes Request
                    case "GET_DELIVERY_POSTCODES":
                        List<Integer> deliveryPostcodes = databaseManager.getDeliveryPostcodes();
                        serverOutput.writeObject(deliveryPostcodes);
                        serverOutput.reset();
                        break;

                    // Handle Get Delivery Cost Request
                    case "GET_DELIVERY_COST":
                        int postcode = (int) serverInput.readObject();
                        double deliveryCost = databaseManager.getDeliveryCost(postcode);
                        serverOutput.writeObject(deliveryCost);
                        break;

                    // Handle Get All Customers Request
                    case "GET_ALL_CUSTOMERS":
                        List<Customer> customers = databaseManager.getAllCustomers();
                        serverOutput.writeObject(customers);
                        serverOutput.reset();
                        break;

                    // Handle Get All Orders Request
                    case "GET_ALL_ORDERS":
                        List<Order> orders = databaseManager.getAllOrders();
                        serverOutput.writeObject(orders);
                        serverOutput.reset();
                        break;

                    // Handle Get Order Lines Request
                    case "GET_ORDER_LINES":
                        int orderId = (int) serverInput.readObject();
                        List<OrderLine> orderLines = databaseManager.getOrderLinesByOrderId(orderId);
                        serverOutput.writeObject(orderLines);
                        serverOutput.reset();
                        break;

                    // Handle Get All Orders Request
                    case "ADD_ORDER":
                        Order order = (Order) serverInput.readObject();
                        order = databaseManager.addOrder(order);
                        serverOutput.writeObject(order);
                        serverOutput.reset();
                        break;

                    // Handle Get All Orders Request
                    case "ADD_ORDER_LINE":
                        OrderLine orderLine = (OrderLine) serverInput.readObject();
                        boolean addOrderLineResult = databaseManager.addOrderLine(orderLine);
                        serverOutput.writeObject(addOrderLineResult);
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
