package com.malenydairysystem.client;

import com.malenydairysystem.model.Product;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the Client connection to the Server for the Program. Passess requests to the Server for processing
                    and retrieval of information from the Database.
 */
public class Client
{

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5586;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client()
    {
        try
        {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to Server on Port " + SERVER_PORT);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts()
    {
        List<Product> products = null;

        try
        {
            outputStream.writeObject("GET_ALL_PRODUCTS");

            products = (List<Product>) inputStream.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return products;
    }
}
