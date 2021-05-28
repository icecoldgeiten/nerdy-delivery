package org.openjfx;

import com.dao.DriverDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterDriverController {

    @FXML
    public Button bReady, bBack;
    public TextField tfName, tfInserts, tfSirname, tfPhone, tfEmail;
    public Label lErrorGegevens;
    public DatePicker dpBirthday;
    public CheckBox cbVehicle, cbLicense;
    private DriverDao driver;

    @FXML
    public void initialize() {
        driver = new DriverDao();
    }

    @FXML
    public void handleReadyButtonAction(ActionEvent event) {
        try {
            String ph = tfPhone.getText();
            int tel = Integer.parseInt(ph);
            boolean isValid = EmailAddressValidator.isValid(tfEmail.getText());
            int veh = cbVehicle.isSelected() ? 1 :0;
            int lic = cbLicense.isSelected() ? 1 :0;

            //check op wachtwoord en op geboortedatum
            if(!isValid) {
                lErrorGegevens.setText("Email adres klopt niet!");
            } else if(dpBirthday.getValue() == null) {
                lErrorGegevens.setText("Geboortedatum is niet ingevult!");
            } else if(dpBirthday.getValue().isAfter(LocalDate.now()) || dpBirthday.getValue().equals(LocalDate.now())){
                lErrorGegevens.setText("Vul juiste geboortedatum in!");
            } else {
                driver.addDriver(tfName.getText(), tfInserts.getText(), tfSirname.getText(), tel, dpBirthday.getValue(), tfEmail.getText(),veh,lic);
                App.setPage("manage_driver");
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
