package org.openjfx;

import com.dao.DriverDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;

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
            boolean isValid = EmailAddressValidator.isValid(tfEmail.getText());
            if(isValid) {
                driver.addDriver(tfName.getText(), tfInserts.getText(), tfSirname.getText(), tel, bd, tfEmail.getText());
                lErrorGegevens.setText("Bezorger is toegevoegd!");
            } else {
                lErrorGegevens.setText("Email adres klopt niet!");
            }
        } catch (Exception e) {
            if(tfPhone.getLength() > 11){
                lErrorGegevens.setText("Telefoonnummer is te lang!");
            } else {
                lErrorGegevens.setText("Gegevens niet correct ingevuld!");
            }
        }
    }

    public void handleBackButtonAction(ActionEvent event) throws IOException {
        App.setPage("manage_driver");
    }
}
