
package org.openjfx;

import com.dao.DriverDao;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginDriverController {
    DriverDao driverDao;
    @FXML
    TextField tfUsername;
    @FXML PasswordField pfPassword;
    @FXML Button bBack,bLogin ;
    @FXML Label lErrorLoginDriver;


    @FXML
    public void initialize() {
        this.driverDao = new DriverDao();
    }

    @FXML
    public void handleLoginButton() throws IOException {
            if (this.driverDao.validate(this.tfUsername.getText(), this.pfPassword.getText())) {
                App.setRoot("mainpage_driver");
            } else {
            lErrorLoginDriver.setText("Gebruikersnaam of wachtwoord onjuist ingevuld!");
            lErrorLoginDriver.setTextFill(Color.RED);
            }
    }

    public void OnActionButtonBack() throws IOException {
        App.setRoot("start_screen");
    }
}
