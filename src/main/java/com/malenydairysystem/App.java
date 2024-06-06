package com.malenydairysystem;

import com.malenydairysystem.client.Client;
import com.malenydairysystem.controller.InitialViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Manages the intial welcome screen with sign in and sign up options, directing users to the appropriate 
                    authentication processes. 
 */
public class App extends Application
{

    // Initialisation of Client
    private Client client;

    // Start Method for JavaFX Application
    @Override
    public void start(Stage stage) throws IOException
    {
        // Create a new Client Connection
        client = new Client();

        // Load Initial View
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InitialView.fxml"));
        InitialViewController controller = new InitialViewController(client);

        // Set the Controller
        loader.setController(controller);

        // Load the Parent
        Parent root = loader.load();

        // Set the Scene
        Scene scene = new Scene(root);

        // Set the Stage
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Maleny Diary to Home System");
        stage.show();
    }

    // Main Method
    public static void main(String[] args)
    {
        // Launch the Application
        launch();
    }
}
