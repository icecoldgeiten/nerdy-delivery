
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
    @FXML PasswordField pfPassword;
    @FXML Button bBack;
    @FXML Button bLogin;


    @FXML
    public void initialize() {
        this.driverDao = new DriverDao();
    }

    @FXML
    public void handleLoginButton() throws IOException {
            if (this.driverDao.validate(this.tfUsername.getText(), this.pfPassword.getText())) {
                App.setRoot("mainpage_driver");
            }
    }

    public void OnActionButtonBack() throws IOException {
        App.setRoot("start_screen");
    }
}
