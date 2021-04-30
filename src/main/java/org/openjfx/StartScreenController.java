//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.openjfx;

import com.dao.DriverDao;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScreenController {
    DriverDao driverDao;
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
