package com.malenydairysystem;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.malenydairysystem.model.Order;
import com.malenydairysystem.model.OrderLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    Utilities Class to allow the Program to call repeated actions, such as changing a scene or changing
                    a pane in the GUI.
 */
public class Utilities
{

    // Switch Scene Method
    public static void switchScene(ActionEvent event, String fxmlPath, Object controller) throws IOException
    {
        // Load the FXML File
        URL fxmlLocation = Utilities.class.getClassLoader().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        // Set the Controller
        loader.setController(controller);

        // Load the Parent
        Parent root = loader.load();

        // Close the Current Stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Create a New Stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Maleny Diary to Home System");
        stage.show();
    }

    // Switch Pane Method
    public static void switchPane(BorderPane contentArea, String fxmlPath, Object controller) throws IOException
    {
        // Load the FXML File
        URL fxmlLocation = Utilities.class.getClassLoader().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        // Set the Controller
        loader.setController(controller);

        // Load the Pane
        Pane pane = loader.load();

        // Set the Pane
        contentArea.getChildren().setAll(pane);
    }

    // Show Error Method
    public static void showError(String errorText)
    {
        // Create an Alert of type Error
        Alert alert = new Alert(Alert.AlertType.ERROR, errorText);
        alert.setHeaderText("Error");
        alert.showAndWait();
    }

    // Show Information Method
    public static void showInformation(String informationText)
    {
        // Create an Alert of type Information
        Alert alert = new Alert(Alert.AlertType.INFORMATION, informationText);
        alert.setHeaderText("Information");
        alert.showAndWait();
    }

    // Show Warning Method
    public static boolean showWarning(String warningText)
    {
        // Create an Alert of type Confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, warningText);
        alert.setHeaderText("Warning");
        alert.showAndWait();

        // Return true if the user clicks OK
        return alert.getResult().getText().equals("OK");
    }

    // Show Order Summary Method
    public static boolean showOrderSummary(Order order, List<OrderLine> orderLines, double deliveryCost)
    {
        // Create a StringBuilder to Store the Order Summary
        StringBuilder sb = new StringBuilder();

        // Append the Order Details
        sb.append("Customer ID: \t\t").append(order.getCustomerID()).append("\n");
        sb.append("Order Date: \t\t").append(order.getOrderDate()).append("\n\n");

        // Append the Order Lines
        sb.append("Items:\n");
        for (OrderLine od : orderLines)
        {
            sb.append(od).append("\n");
        }

        // Append the GST
        sb.append("\nGST: \t\t\t10%\n");

        // Append the Delivery Cost
        sb.append("Delivery Cost: \t\t$").append(deliveryCost).append("\n");

        // Append the Total Price
        sb.append("Total Price: \t\t$").append(order.getTotalPrice()).append("\n");

        // Append Message to Confirm Order
        sb.append("\nDo you wish to place this Order and Accept the Cost?");

        // Create an Alert of type Confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, sb.toString());
        alert.setHeaderText("Order Summary");
        alert.showAndWait();

        // Return true if the user clicks OK
        return alert.getResult().getText().equals("OK");
    }
}
