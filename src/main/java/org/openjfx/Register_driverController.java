package org.openjfx;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.entity.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Register_driverController {

    @FXML
    public Button bGereed;
    public Button bTerug;
    public TextField tfVoornaam;
    public TextField tfTussenvoegstels;
    public TextField tfAchternaam;
    public TextField tfEmail;
    public TextField tfTelefoonnummer;
    public TextField tfGeboorteDatum;
    public Label lErrorGegevens;
    public Driver driver;



    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        if (event.getSource() == bGereed) {
            Stage stage = (Stage) bGereed.getScene().getWindow();
//          tfEmail.getText();
//          Email van Driver moet nog toegevoegd worden in database en in de erd.
            int telefoonnr = 0;
                if(tfTelefoonnummer.getText().equals("")){
                    telefoonnr = 0;
                } else {
                    telefoonnr = Integer.parseInt(tfTelefoonnummer.getText());
                }
                driver.newDriver(tfVoornaam.getText(), tfTussenvoegstels.getText(), tfAchternaam.getText(), telefoonnr, tfGeboorteDatum.getText());
                stage.close();

//            lErrorGegevens.setText("Geen datum!");

        } else if (event.getSource() == bTerug) {
            Stage stage = (Stage) bTerug.getScene().getWindow();
            stage.close();
        }
    }
}