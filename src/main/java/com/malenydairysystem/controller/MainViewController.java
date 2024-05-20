/*
    Students: Joshua White (12196075), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description: Manages the main application window, serving as the primary interface for navigation between different sections of the system.
 */

package com.malenydairysystem.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController {
    
    @FXML
    private BorderPane contentArea; 
           
    @FXML
    private void loadProductView(ActionEvent event) throws IOException{
        Pane homeView = FXMLLoader.load(getClass().getClassLoader().getResource("com/malenydairysystem/ProductView.fxml"));
         contentArea.getChildren().setAll(homeView);
    }
    
    @FXML
    private void loadScheduleView(ActionEvent event) throws IOException{
        Pane homeView = FXMLLoader.load(getClass().getClassLoader().getResource("com/malenydairysystem/ScheduleView.fxml"));
        contentArea.getChildren().setAll(homeView);
    }
    
    @FXML
    private void loadOrderView(ActionEvent event) throws IOException{
        Pane homeView = FXMLLoader.load(getClass().getClassLoader().getResource("com/malenydairysystem/OrderView.fxml"));
        contentArea.getChildren().setAll(homeView);
    }
    
    @FXML
    private void loadAdminView(ActionEvent event) throws IOException{
        Pane homeView = FXMLLoader.load(getClass().getClassLoader().getResource("com/malenydairysystem/AdminView.fxml"));
        contentArea.getChildren().setAll(homeView);
    }
    
    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException{
        // Load the Initial View
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/malenydairysystem/InitialView.fxml"));
        Pane initialView = loader.load();
        
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      
        // Set the new scene
        Scene scene = new Scene(initialView);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleContentArea(ActionEvent event)throws IOException{
        
    }
    
    
}
