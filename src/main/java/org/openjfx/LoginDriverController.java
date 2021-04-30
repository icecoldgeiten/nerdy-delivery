//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.openjfx;

import com.dao.DriverDao;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginDriverController {
    DriverDao driverDao;
    @FXML
    TextField tfUsername;
    @FXML
    PasswordField pfPassword;
    @FXML
    Button bBack;
    @FXML
    Button bLogin;

    public LoginDriverController() {
    }

    @FXML
    public void initialize() {
        this.driverDao = new DriverDao();
    }

    @FXML
    public void handleLoginButton() throws IOException {
        try {
            if (this.driverDao.validate(this.tfUsername.getText(), this.pfPassword.getText())) {
                App.setRoot("mainpage_driver");
            }
        } catch (Exception var2) {
            System.out.println(var2);
            System.out.println("Log in niet gelukt");
        }

    }

    public void OnActionButtonBack() throws IOException {
        App.setRoot("start_screen");
    }
}
