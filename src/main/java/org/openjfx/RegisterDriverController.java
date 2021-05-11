package org.openjfx;

import com.dao.DriverDao;
import com.helpers.AES256;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
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
            LocalDate bd = dpBirthday.getValue();
            String ph = tfPhone.getText();
            int tel = Integer.parseInt(ph);
            System.out.println(tfPhone.getText());
            driver.addDriver(tfName.getText(), tfInserts.getText(), tfSirname.getText(), tel, bd, tfEmail.getText());
            lErrorGegevens.setText("Bezorger is toegevoegd!");
        } catch (Exception e) {
            lErrorGegevens.setText("Gegevens niet correct ingevuld!");
        }
    }

    public void handleBackButtonAction(ActionEvent event) throws IOException {
        App.setPage("manage_driver");
    }
}
