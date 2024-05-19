/*
    Students: Tina Losin (10569238)
    Description: Manages server operations, including handling multiple client connections simultaneously using a thread-per-connection 
                 model, and synchronizing access to the database during concurrent operations.  
 */
package com.malenydairysystem.server;

import com.malenydairysystem.database.DatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

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
            System.out.println("Server started on Port " + SERVER_PORT);

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
    Socket serverSocket;

    // Object Input and Output Streams
    ObjectInputStream serverInput;
    ObjectOutputStream serverOutput;

    // Constructor for the Server Connection
    public ServerConnection(Socket serverSocket)
    {
        try
        {
            // Set the Client Socket
            this.serverSocket = serverSocket;

            // Create the Object Input and Output Streams
            serverInput = new ObjectInputStream(serverSocket.getInputStream());
            serverOutput = new ObjectOutputStream(serverSocket.getOutputStream());

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
        
    }
}
