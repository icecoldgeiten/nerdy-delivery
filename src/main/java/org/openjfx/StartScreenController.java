package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.persistence.Persistence;

public class StartScreenController {
    @FXML
    public Button bLoginAdministrator;
    public Button bLoginDriver;

    public void OnActionButtonLoginAdministrator() throws IOException {
        App.setRoot("login_administrator");
    }

    public void OnActionButtonLoginDriver() throws IOException {
        App.setRoot("login_driver");
    }
}
