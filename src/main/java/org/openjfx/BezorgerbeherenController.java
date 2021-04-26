package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BezorgerbeherenController {

    @FXML
    public Button bOK;
    public Button bBACK;

    @FXML
    void buttonP(ActionEvent event) {

            Stage stage=(Stage) bOK.getScene().getWindow();
            stage.close(); 
    }

    @FXML
    void buttonB(ActionEvent event) {
        Stage stage=(Stage) bBACK.getScene().getWindow();
        stage.close();
    }


}





