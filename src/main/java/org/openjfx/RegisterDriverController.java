package org.openjfx;

import com.dao.DriverDao;
import com.helpers.AES256;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RegisterDriverController {

    @FXML
    public Button bReady, bBack;
    public TextField tfName, tfInserts, tfSirname, tfPhone, tfEmail;
    public Label lErrorGegevens;
    public DatePicker dpBirthday;
    private DriverDao driver;

    @FXML
    public void initialize() {
        driver = new DriverDao();
    }

    @FXML
    public void handleReadyButtonAction(ActionEvent event) {
        try {
            driver = new DriverDao();
            LocalDate bd = dpBirthday.getValue();
            String ph = tfPhone.getText();
            int tel = Integer.parseInt(ph);
            driver.addDriver(tfName.getText(), tfInserts.getText(), tfSirname.getText(), tel, bd, tfEmail.getText(),AES256.encrypt(driver.randomPassword(8)));
            lErrorGegevens.setText("Bezorger is toegevoegd!");
        } catch (Exception e) {
            lErrorGegevens.setText("Gegevens niet correct ingevuld!");
        }
    }

    public void handleBackButtonAction(ActionEvent event) {
        Stage stage = (Stage) bBack.getScene().getWindow();
        stage.close();
    }
}
