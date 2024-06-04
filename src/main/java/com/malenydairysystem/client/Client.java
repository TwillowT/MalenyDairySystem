package com.malenydairysystem.client;

import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.Delivery;
import com.malenydairysystem.model.Order;
import com.malenydairysystem.model.OrderLine;
import com.malenydairysystem.model.Product;
import com.malenydairysystem.model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the Client connection to the Server for the Program. Passess requests to the Server for processing
                    and retrieval of information from the Database.
 */
public class Client
{

    // Initialisation of Server Connection
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5586;

    // Initialisation of Socket, Object Input and Output Streams
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private PublicKey serverPublicKey;

    // Constructor for Client
    public Client()
    {
        try
        {
            // Connect to Server
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to Server on Port " + SERVER_PORT);

            // Initialise Object Input and Output Streams
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            // Request the server's public key
            requestServerPublicKey();

        }
        catch (Exception e)
        {
            // Print Stack Trace if Connection Fails
            e.printStackTrace();
        }
    }

    // Method to Request Server Public Key
    private void requestServerPublicKey()
    {
        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_PUBLIC_KEY");

            // Retrieve Server Public Key
            byte[] keyBytes = (byte[]) inputStream.readObject();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            // Generate Server Public Key
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            serverPublicKey = keyFactory.generatePublic(keySpec);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Method to Encrypt Password
    private byte[] encryptPassword(String password) throws Exception
    {
        // Encrypt Password using RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // Initialise Cipher with Server Public Key
        cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);

        // Return Encrypted Password
        return cipher.doFinal(password.getBytes("UTF-8"));
    }

    // Method to authenticte a customer
    public User authenticateUser(String username, String password)
    {
        try
        {
            // Encrypt Password
            byte[] encryptedPassword = encryptPassword(password);

            // Send Request to Server
            outputStream.writeObject("SIGN_IN_USER");
            outputStream.writeObject(username);
            outputStream.writeObject(encryptedPassword);

            // Retrieve Response from Server
            Object response = inputStream.readObject();

            // Return Response
            if (response instanceof User)
            {
                // Return Response as User
                User authenticatedUser = (User) response;
                return authenticatedUser;
            }
            else
            {
                // Return null if Response is not User
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // Send Request to Server to Add a Customer
    public boolean addCustomer(String name, String address, String phone, String email, String username, String password)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("ADD_CUSTOMER");
            outputStream.writeObject(name);
            outputStream.writeObject(address);
            outputStream.writeObject(phone);
            outputStream.writeObject(email);
            outputStream.writeObject(username);
            byte[] encryptedPassword = encryptPassword(password);
            outputStream.writeObject(encryptedPassword);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Send Rquest to Server to Add a Product
    public boolean addProduct(Product product)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("ADD_PRODUCT");
            outputStream.writeObject(product);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Send Request to Server to Update a Product
    public boolean updateProduct(Product product)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("UPDATE_PRODUCT");
            outputStream.writeObject(product);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Send Request to Server to Remove a Product
    public boolean removeProduct(int productId)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("REMOVE_PRODUCT");
            outputStream.writeObject(productId);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Retrieve all Products from the Server
    public List<Product> getAllProducts()
    {
        // Initialise List of Products as Null
        List<Product> products = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_ALL_PRODUCTS");

            // Retrieve List of Products from Server
            products = (List<Product>) inputStream.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return List of Products
        return products;
    }

    // Send Request to Server to Add a Customer
    public boolean addDelivery(Delivery delivery)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("ADD_DELIVERY");
            outputStream.writeObject(delivery);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Send Request to Server to Update a Delivery
    public boolean updateDelivery(Delivery delivery)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("UPDATE_DELIVERY");
            outputStream.writeObject(delivery);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Send Request to Server to Remove a Delivery
    public boolean removeDelivery(int deliveryId)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("REMOVE_DELIVERY");
            outputStream.writeObject(deliveryId);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }

    // Retrieve all Deliveries from the Server
    public List<Delivery> getAllDeliveries()
    {
        // Initialise List of Deliveries as Null
        List<Delivery> deliveries = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_ALL_DELIVERIES");

            // Retrieve List of Deliveries from Server
            deliveries = (List<Delivery>) inputStream.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return List of Deliveries
        return deliveries;
    }

    // Retrieve all Customers from the Server
    public List<Customer> getAllCustomers()
    {
        // Initialise List of Customers as Null
        List<Customer> customers = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_ALL_CUSTOMERS");

            // Retrieve List of Customers from Server
            customers = (List<Customer>) inputStream.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return List of Customers
        return customers;
    }

    // Retrieve all Orders from the Server
    public List<Order> getAllOrders()
    {
        // Initialise List of Orders as Null
        List<Order> orders = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_ALL_ORDERS");

            // Retrieve List of Orders from Server
            orders = (List<Order>) inputStream.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return List of Orders
        return orders;
    }

    // Retrieve all Order Lines from the Server
    public List<OrderLine> getOrderLinesByOrderId(int orderId)
    {
        // Initialise List of Order Lines as Null
        List<OrderLine> orderLines = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_ORDER_LINES");
            outputStream.writeObject(orderId);

            // Retrieve List of Order Lines from Server
            orderLines = (List<OrderLine>) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return List of Order Lines
        return orderLines;
    }

    // Retrieve all Delivery Cost Postcodes from the Server
    public List<Integer> getDeliveryPostcodes()
    {
        // Initialise List of Postcodes as Null
        List<Integer> postcodes = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_DELIVERY_POSTCODES");

            // Retrieve List of Postcodes from Server
            postcodes = (List<Integer>) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return List of Postcodes
        return postcodes;
    }

    // Retrieve Delivery Cost for a Postcode from the Server
    public Double getDeliveryCost(int postcode)
    {
        // Initialise Cost as Null
        Double cost = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("GET_DELIVERY_COST");
            outputStream.writeObject(postcode);

            // Retrieve Cost from Server
            cost = (Double) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Cost
        return cost;
    }

    // Send Request to Server to Add an Order
    public Order addOrder(Order order)
    {
        Order returnOrder = null;

        try
        {
            // Send Request to Server
            outputStream.writeObject("ADD_ORDER");
            outputStream.writeObject(order);

            returnOrder = (Order) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return returnOrder;
    }

    // Send Request to Server to Add an OrderLine
    public boolean addOrderLine(OrderLine orderLine)
    {
        // Initialise Result as False
        boolean result = false;

        try
        {
            // Send Request to Server
            outputStream.writeObject("ADD_ORDER_LINE");
            outputStream.writeObject(orderLine);

            // Retrieve Result from Server
            result = (boolean) inputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Return Result
        return result;
    }
}
