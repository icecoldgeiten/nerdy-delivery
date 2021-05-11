package org.openjfx;

import com.dao.AdminDao;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginAdministratorController {
    private AdminDao admin;
    public TextField username;
    public PasswordField password;
    public Button primaryButton;
    public Button bBack;

    @FXML
    public void initialize() {
        this.admin = new AdminDao();
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        if (admin.validate(username.getText(), password.getText())) {
            App.setRoot("sidebar");
        }
    }

    public void OnActionButtonBack() throws IOException {
        App.setRoot("start_screen");
    }
}
