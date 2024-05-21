package com.malenydairysystem.server;

import com.malenydairysystem.database.DatabaseManager;
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
        //database.insertDataFromCSV("A3-products.csv");
        // Insert the Delivery Cost Data from the CSV
        //database.insertDataFromCSV("A3-delivery-cost.csv");
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

            // Connect to the database
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

    public void run()
    {
        try
        {
            while (true)
            {
                String requestType = (String) serverInput.readObject();

                if (requestType.equals("GET_ALL_PRODUCTS"))
                {
                    List<Product> products = databaseManager.getAllProducts();

                    serverOutput.writeObject(products);
                    serverOutput.reset();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
