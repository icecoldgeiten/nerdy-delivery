package org.openjfx;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScreenController {
    @FXML
    Button bLoginAdministrator;
    @FXML
    Button bLoginDriver;

    public StartScreenController() {
    }

    public void OnActionButtonLoginAdministrator() throws IOException {
        App.setRoot("login_administrator");
    }

    public void OnActionButtonLoginDriver() throws IOException {
        App.setRoot("login_driver");
    }
}
