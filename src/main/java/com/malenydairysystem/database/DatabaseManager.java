package com.malenydairysystem.database;

import com.malenydairysystem.model.Customer;
import com.malenydairysystem.model.Delivery;
import com.malenydairysystem.model.Order;
import com.malenydairysystem.model.OrderLine;
import com.malenydairysystem.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Handles database interactions for reading and saving data related to customer details, products, product 
                    receipt options, and order details. Ensures data consistency during concurrent access by multiple clients.
 */
public class DatabaseManager
{

    // Database Connection Details
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private final String DB_NAME;

    // Product Table Prepared Statements
    PreparedStatement addProduct;
    PreparedStatement updateProduct;
    PreparedStatement deleteProduct;
    PreparedStatement getAllProducts;

    // Delivery Table Prepared Statements
    PreparedStatement addDelivery;
    PreparedStatement updateDelivery;
    PreparedStatement deleteDelivery;
    PreparedStatement getAllDeliveries;

    // Delivery Cost Table Prepared Statements
    PreparedStatement getDeliveryPostcodes;
    PreparedStatement getDeliveryCost;

    // Customer Table Prepared Statements
    PreparedStatement getAllCustomers;

    // Order Table Prepared Statements
    PreparedStatement getAllOrders;

    // Order Line Table Prepared Statements
    PreparedStatement getAllOrderLines;

    // Constructor for the Database Manager
    public DatabaseManager()
    {
        // Initialize the Database Connection Details
        DB_URL = "jdbc:mysql://localhost:3306/";
        DB_USER = "root";
        DB_PASSWORD = "password";
        DB_NAME = "maleny";
    }

    // Connect to the Database
    public void connectDatabase()
    {
        // Initialize the Database Connection
        Connection dbConnection = null;

        try
        {
            // Create the Database Connection
            dbConnection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);

            // Initialize the Prepared Statements for the Product Database Table
            addProduct = dbConnection.prepareStatement("INSERT INTO products (product_name, unit, quantity, price, ingredients) VALUES (?, ?, ?, ?, ?)");
            updateProduct = dbConnection.prepareStatement("UPDATE products SET product_name = ?, unit = ?, quantity = ?, price = ?, ingredients = ? WHERE product_id = ?");
            deleteProduct = dbConnection.prepareStatement("DELETE FROM products WHERE product_id = ?");
            getAllProducts = dbConnection.prepareStatement("SELECT * FROM products");

            // Initialize the Prepared Statements for the Delivery Database Table
            addDelivery = dbConnection.prepareStatement("INSERT INTO delivery (postcode, delivery_day, delivery_cost) VALUES (?, ?, ?)");
            updateDelivery = dbConnection.prepareStatement("UPDATE delivery SET postcode = ?, delivery_day = ?, delivery_cost = ? WHERE delivery_id = ?");
            deleteDelivery = dbConnection.prepareStatement("DELETE FROM delivery WHERE delivery_id = ?");
            getAllDeliveries = dbConnection.prepareStatement("SELECT * FROM delivery");

            // Initialize the Prepared Statements for the Delivery Cost Database Table
            getDeliveryPostcodes = dbConnection.prepareStatement("SELECT postcode FROM delivery_costs");
            getDeliveryCost = dbConnection.prepareStatement("SELECT delivery_cost FROM delivery_costs WHERE postcode = ?");

            // Initialize the Prepared Statements for the Customer Database Table
            getAllCustomers = dbConnection.prepareStatement("SELECT * FROM customers");

            // Initialize the Prepared Statements for the Order Database Table
            getAllOrders = dbConnection.prepareStatement("SELECT * FROM orders");

            // Initialize the Prepared Statements for the Order Line Database Table
            getAllOrderLines = dbConnection.prepareStatement("SELECT * FROM orders_lines WHERE order_id = ?");
        }
        catch (SQLException e)
        {
            // Print the Database Connection Error Message
            System.out.println("Database connection failed...");

            // Print the Stack Trace
            e.printStackTrace();
        }
    }

    // Create the Database
    public void createDatabase() throws SQLException
    {
        // Initialize the Database Connection and Statement
        Connection dbConnection = null;
        Statement dbStatement = null;

        try
        {
            // Create the Database Connection
            dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create the Database Statement
            dbStatement = dbConnection.createStatement();

            // Create the Database Creation Statement
            String database = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;

            // Execute the Database Creation Statement
            dbStatement.executeUpdate(database);

            // Print the Database Creation Success Message
            System.out.println("Database created Successfully...");
        }
        catch (SQLException e)
        {
            // Print the Database Creation Error Message
            System.out.println("Database creation Failed...");

            // Print the Stack Trace
            e.printStackTrace();
        }
        finally
        {
            // Close the Database Connection and Statement
            if (dbStatement != null)
            {
                dbStatement.close();
            }
            if (dbConnection != null)
            {
                dbConnection.close();
            }
        }
    }

    // Create the Tables
    public void createTables() throws SQLException
    {
        // Initialize the Database Connection and Statement
        Connection dbConnection = null;
        Statement dbStatement = null;

        try
        {
            // Create the Database Connection
            dbConnection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);

            // Create the Database Statement
            dbStatement = dbConnection.createStatement();

            // Create the User Table with (User ID, Full Name, Phone, Email, Password, Delivery Address)
            String customerTable = "CREATE TABLE IF NOT EXISTS customers ("
                    + "customer_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "full_name VARCHAR(255) NOT NULL,"
                    + "phone VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) NOT NULL UNIQUE,"
                    + "password CHAR(64) NOT NULL,"
                    + "delivery_address VARCHAR(255) NOT NULL"
                    + ")";

            // Execute the User Table Creation Statement
            dbStatement.executeUpdate(customerTable);

            //Create the Admin Table with (Admin ID, Full Name, Phone, Email, Password)
            String adminTable = "CREATE TABLE IF NOT EXISTS admins ("
                    + "admin_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "full_name VARCHAR(255) NOT NULL,"
                    + "phone VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) NOT NULL UNIQUE,"
                    + "password CHAR(64) NOT NULL"
                    + ")";

            // Execute the Admin Table Creation Statement
            dbStatement.executeUpdate(adminTable);

            // Create the Order Table with (Order ID, Customer ID, Total Price, Order Date)
            String orderTable = "CREATE TABLE IF NOT EXISTS orders ("
                    + "order_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "customer_id INT NOT NULL,"
                    + "total_price DOUBLE NOT NULL,"
                    + "order_date DATE NOT NULL,"
                    + "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)"
                    + ")";

            // Execute the Order Table Creation Statement
            dbStatement.executeUpdate(orderTable);

            // Create the Product Table with (Product ID, Product Name, Unit, Quantity, Price, Ingredients)
            String productTable = "CREATE TABLE IF NOT EXISTS products ("
                    + "product_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "product_name VARCHAR(255) NOT NULL,"
                    + "unit VARCHAR(255) NOT NULL,"
                    + "quantity INT NOT NULL,"
                    + "price DOUBLE NOT NULL,"
                    + "ingredients VARCHAR(255) NOT NULL"
                    + ")";

            // Execute the Product Table Creation Statement
            dbStatement.executeUpdate(productTable);

            // Create the Order Line Table which is a junction table between Order and Product Tables with (Order ID, Product ID, Quantity, Price, Total)
            String orderLineTable = "CREATE TABLE IF NOT EXISTS orders_lines ("
                    + "order_id INT NOT NULL,"
                    + "product_id INT NOT NULL,"
                    + "quantity INT NOT NULL,"
                    + "price DOUBLE NOT NULL,"
                    + "total DOUBLE NOT NULL,"
                    + "FOREIGN KEY (order_id) REFERENCES orders(order_id),"
                    + "FOREIGN KEY (product_id) REFERENCES products(product_id)"
                    + ")";

            // Execute the Order Line Table Creation Statement
            dbStatement.executeUpdate(orderLineTable);

            // Create the Delivery Cost Table with (Delivery Cost ID, Postcode, Delivery Cost)
            String deliveryCostTable = "CREATE TABLE IF NOT EXISTS delivery_costs ("
                    + "delivery_cost_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "postcode INT NOT NULL UNIQUE,"
                    + "delivery_cost DOUBLE NOT NULL"
                    + ")";

            // Execute the Delivery Cost Table Creation Statement
            dbStatement.executeUpdate(deliveryCostTable);

            // Create the Delivery Table with (Delivery ID, Postcode, Delivery Day, Delivery Time)
            String deliveryTable = "CREATE TABLE IF NOT EXISTS delivery ("
                    + "delivery_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "postcode INT NOT NULL,"
                    + "delivery_day VARCHAR(255) NOT NULL,"
                    + "delivery_cost DOUBLE NOT NULL,"
                    + "FOREIGN KEY (postcode) REFERENCES delivery_costs(postcode)"
                    + ")";

            // Execute the Delivery Table Creation Statement
            dbStatement.executeUpdate(deliveryTable);

            // Print the Table Creation Success Message
            System.out.println("Tables created Successfully...");
        }
        catch (SQLException e)
        {
            // Print the Table Creation Error Message
            System.out.println("Tables creation Failed...");

            // Print the Stack Trace
            e.printStackTrace();
        }
        finally
        {
            // Close the Database Connection and Statement
            if (dbStatement != null)
            {
                dbStatement.close();
            }
            if (dbConnection != null)
            {
                dbConnection.close();
            }
        }
    }

    // Insert the Required Product and Delivery Cost Data from the CSV Files
    public void insertDataFromCSV(String filename) throws SQLException
    {
        Connection dbConnection = null;
        PreparedStatement dbStatement = null;

        try
        {
            // Create the Database Connection
            dbConnection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);

            // Prepare the SQL Statement based on the filename
            String sql;
            if (filename.equals("A3-products.csv"))
            {
                sql = "INSERT INTO products (product_name, unit, quantity, price, ingredients) VALUES (?, ?, ?, ?, ?)";
            }
            else if (filename.equals("A3-delivery-cost.csv"))
            {
                sql = "INSERT INTO delivery_costs (postcode, delivery_cost) VALUES (?, ?)";
            }
            else
            {
                throw new IllegalArgumentException("Unknown Filename: " + filename);
            }

            dbStatement = dbConnection.prepareStatement(sql);

            // Open the CSV file
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + filename));

            // Skip the first line (header)
            reader.readLine();

            // Read the file line by line
            String line;
            while ((line = reader.readLine()) != null)
            {
                // Split the line by comma
                String[] fields = line.split(",");

                // Bind the values to the SQL statement
                for (int i = 0; i < fields.length; i++)
                {
                    dbStatement.setString(i + 1, fields[i]);
                }

                // Execute the statement
                dbStatement.executeUpdate();
            }

            // Close the CSV file
            reader.close();

            // Print the Data Insertion Success Message
            System.out.println("Data inserted Successfully from " + filename);
        }
        catch (SQLException | IOException e)
        {
            // Print the Data Insertion Error Message
            System.out.println("Data insertion Failed from " + filename);

            // Print the Stack Trace
            e.printStackTrace();
        }
        finally
        {
            // Close the Database Connection and Statement
            if (dbStatement != null)
            {
                dbStatement.close();
            }
            if (dbConnection != null)
            {
                dbConnection.close();
            }
        }
    }

    // Add a Product and return a response
    public boolean addProduct(Product product)
    {
        try
        {
            // Bind the Product Details to the Prepared Statement
            addProduct.setString(1, product.getProductName());
            addProduct.setString(2, product.getUnit());
            addProduct.setInt(3, product.getQuantity());
            addProduct.setDouble(4, product.getPrice());
            addProduct.setString(5, product.getIngredients());

            // Execute the Prepared Statement
            addProduct.executeUpdate();

            //  Return a Success Response
            return true;
        }
        catch (SQLException e)
        {
            // Print the Stack Trace and Return a Failure Response
            e.printStackTrace();
            return false;
        }
    }

    // Update a Product and return a response
    public boolean updateProduct(Product product)
    {
        try
        {
            // Bind the Product Details to the Prepared Statement
            updateProduct.setString(1, product.getProductName());
            updateProduct.setString(2, product.getUnit());
            updateProduct.setInt(3, product.getQuantity());
            updateProduct.setDouble(4, product.getPrice());
            updateProduct.setString(5, product.getIngredients());
            updateProduct.setInt(6, product.getProductID());

            // Execute the Prepared Statement
            updateProduct.executeUpdate();

            // Return a Success Response
            return true;
        }
        catch (SQLException e)
        {
            // Print the Stack Trace and Return a Failure Response
            e.printStackTrace();
            return false;
        }
    }

    // Remove a Product and return a response
    public boolean removeProduct(int productId)
    {
        try
        {
            // Bind the Product ID to the Prepared Statement
            deleteProduct.setInt(1, productId);

            // Execute the Prepared Statement
            deleteProduct.executeUpdate();

            // Return a Success Response
            return true;
        }
        catch (SQLException e)
        {
            // Print the Stack Trace and Return a Failure Response
            e.printStackTrace();
            return false;
        }
    }

    // Get all Products and return a list
    public List<Product> getAllProducts()
    {
        // Initialize the Product List
        List<Product> products = new ArrayList<>();
        try
        {
            // Execute the Prepared Statement
            ResultSet rs = getAllProducts.executeQuery();

            // Iterate through the Result Set
            while (rs.next())
            {
                // Create a new Product Object
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("unit"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("ingredients")
                );

                // Add the Product to the List
                products.add(product);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Product List
        return products;
    }

    // Add a Delivery and return a response
    public boolean addDelivery(Delivery delivery)
    {
        try
        {
            // Bind the Delivery Details to the Prepared Statement
            addDelivery.setInt(1, delivery.getPostCode());
            addDelivery.setString(2, delivery.getDeliveryDay());
            addDelivery.setDouble(3, delivery.getDeliveryCost());

            // Execute the Prepared Statement
            addDelivery.executeUpdate();

            // Return a Success Response
            return true;
        }
        catch (SQLException e)
        {
            // Print the Stack Trace and Return a Failure Response
            e.printStackTrace();
            return false;
        }
    }

    // Update a Delivery and return a response
    public boolean updateDelivery(Delivery delivery)
    {
        try
        {
            // Bind the Delivery Details to the Prepared Statement
            updateDelivery.setInt(1, delivery.getPostCode());
            updateDelivery.setString(2, delivery.getDeliveryDay());
            updateDelivery.setDouble(3, delivery.getDeliveryCost());
            updateDelivery.setInt(4, delivery.getDeliveryID());

            // Execute the Prepared Statement
            updateDelivery.executeUpdate();

            // Return a Success Response
            return true;
        }
        catch (SQLException e)
        {
            // Print the Stack Trace and Return a Failure Response
            e.printStackTrace();
            return false;
        }
    }

    // Remove a Delivery and return a response
    public boolean removeDelivery(int deliveryId)
    {
        try
        {
            // Bind the Delivery ID to the Prepared Statement
            deleteDelivery.setInt(1, deliveryId);

            // Execute the Prepared Statement
            deleteDelivery.executeUpdate();

            // Return a Success Response
            return true;
        }
        catch (SQLException e)
        {
            // Print the Stack Trace and Return a Failure Response
            e.printStackTrace();
            return false;
        }
    }

    // Get all Deliveries and return a list
    public List<Delivery> getAllDeliveries()
    {
        // Initialize the Delivery List
        List<Delivery> deliveries = new ArrayList<>();
        try
        {
            // Execute the Prepared Statement
            ResultSet rs = getAllDeliveries.executeQuery();

            // Iterate through the Result Set
            while (rs.next())
            {
                // Create a new Delivery Object
                Delivery delivery = new Delivery(
                        rs.getInt("delivery_id"),
                        rs.getInt("postcode"),
                        rs.getString("delivery_day"),
                        rs.getDouble("delivery_cost")
                );

                // Add the Delivery to the List
                deliveries.add(delivery);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Delivery List
        return deliveries;
    }

    // Get all Delivery Postcodes and return a list
    public List<Integer> getDeliveryPostcodes()
    {
        // Initialize the Delivery Postcode List
        List<Integer> deliveryPostcodes = new ArrayList<>();
        try
        {
            // Execute the Prepared Statement
            ResultSet rs = getDeliveryPostcodes.executeQuery();

            // Iterate through the Result Set
            while (rs.next())
            {
                // Add the Postcode to the List
                deliveryPostcodes.add(rs.getInt("postcode"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Delivery Postcode List
        return deliveryPostcodes;
    }

    // Get the Delivery Cost for a Postcode
    public Double getDeliveryCost(int postcode)
    {
        // Initialize the Delivery Cost
        Double deliveryCost = null;
        try
        {
            // Bind the Postcode to the Prepared Statement
            getDeliveryCost.setInt(1, postcode);

            // Execute the Prepared Statement
            ResultSet rs = getDeliveryCost.executeQuery();

            // Iterate through the Result Set
            if (rs.next())
            {
                // Get the Delivery Cost
                deliveryCost = rs.getDouble("delivery_cost");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Delivery Cost
        return deliveryCost;
    }

    // Get all Customers and return a list
    public List<Customer> getAllCustomers()
    {
        // Initialize the Customer List
        List<Customer> customers = new ArrayList<>();
        try
        {
            // Execute the Prepared Statement
            ResultSet rs = getAllCustomers.executeQuery();

            // Iterate through the Result Set
            while (rs.next())
            {
                // Create a new Customer Object
                Customer customer = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("delivery_address")
                );

                // Add the Customer to the List
                customers.add(customer);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Customer List
        return customers;
    }

    // Get all Orders and return a list
    public List<Order> getAllOrders()
    {
        // Initialize the Order List
        List<Order> orders = new ArrayList<>();
        try
        {
            // Execute the Prepared Statement
            ResultSet rs = getAllOrders.executeQuery();

            // Iterate through the Result Set
            while (rs.next())
            {
                // Create a new Order Object
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("total_price"),
                        rs.getDate("order_date")
                );

                // Add the Order to the List
                orders.add(order);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Order List
        return orders;
    }

    // Get all Order Lines by Order ID and return a list
    public List<OrderLine> getOrderLinesByOrderId(int orderId)
    {
        // Initialize the Order Line List
        List<OrderLine> orderLines = new ArrayList<>();
        try
        {
            // Bind the Order ID to the Prepared Statement
            getAllOrderLines.setInt(1, orderId);

            // Execute the Prepared Statement
            ResultSet rs = getAllOrderLines.executeQuery();

            // Iterate through the Result Set
            while (rs.next())
            {
                // Create a new Order Line Object
                OrderLine orderLine = new OrderLine(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("total")
                );

                // Add the Order Line to the List
                orderLines.add(orderLine);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Return the Order Line List
        return orderLines;
    }
}
