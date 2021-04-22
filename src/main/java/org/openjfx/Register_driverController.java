package org.openjfx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Register_driverController {

    @FXML
    public Button bGereed;
    public Button bTerug;
    public TextField hoi;

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        if (event.getSource() == bGereed) {
            Stage stage = (Stage) bGereed.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == bTerug) {
            Stage stage = (Stage) bTerug.getScene().getWindow();
            stage.close();
        }
    }
}