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

    private Client client;

    @Override
    public void start(Stage stage) throws IOException
    {
        client = new Client();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("InitialView.fxml"));
        InitialViewController controller = new InitialViewController(client);
        loader.setController(controller);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.setTitle("Maleny Diary to Home System");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
