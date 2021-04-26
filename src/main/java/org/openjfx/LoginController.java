package org.openjfx;

import com.dao.AdminDao;
import com.entity.Administrator;
import com.entity.Employee;
import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private AdminDao admin;

    public TextField username;
    public PasswordField password;
    public Button primaryButton;

    @FXML
    public void initialize() {
        admin = new AdminDao();
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        if (admin.validate(username.getText(), password.getText())) {
            App.setRoot("secondary");
        }
    }
}