/*
    Students: Joshua White (12196075), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description: Manages the homepage.
 */

package com.malenydairysystem.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class HomeViewController {
    
    @FXML
    private StackPane contentArea; 
    
    @FXML
    private void loadHomeView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("com/malenydairysystem/HomeView.fxml"));
        Pane homeView = loader.load();
        contentArea.getChildren().setAll(homeView);
    }
    
}
