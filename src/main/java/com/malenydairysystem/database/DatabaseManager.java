/*
    Students: Tina Losin (10569238)
    Description: Handles database interactions for reading and saving data related to customer details, products, product receipt options, 
                 and order details. Ensures data consistency during concurrent access by multiple clients.
 */
package com.malenydairysystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager
{

    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private final String DB_NAME;

    public DatabaseManager()
    {
        DB_URL = "jdbc:mysql://localhost:3306/";
        DB_USER = "root";
        DB_PASSWORD = "password";
        DB_NAME = "maleny";
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
                    + "delivery_time TIME NOT NULL,"
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
}
