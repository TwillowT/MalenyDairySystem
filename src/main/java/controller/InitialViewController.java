/*
    Students: Tina Losin (10569238)
    Description: Handles the intial welcome screen with sign in and sign up options, directing users to the appropriate authentication processes. 
 */

package controller;

import com.malenydairysystem.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class InitialViewController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
