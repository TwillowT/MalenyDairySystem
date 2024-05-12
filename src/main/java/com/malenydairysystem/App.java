package com.malenydairysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * File:        Main Class
 * Assessment:  
 * Author:      
 * Student ID:  
 */

public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("InitialView.fxml"));
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
