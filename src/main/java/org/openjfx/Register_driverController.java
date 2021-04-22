package org.openjfx;

import com.entity.Driver;
import com.mysql.cj.conf.url.LoadbalanceConnectionUrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Register_driverController {

    @FXML
    public Button bReady;
    public Button bBack;
    public TextField tfName;
    public TextField tfInserts;
    public TextField tfSirname;
    public TextField tfEmail;
    public TextField tfPhone;
    public TextField tfBirthdate;
    public Label lErrorGegevens;
    public Driver driver;



    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        if (event.getSource() == bReady) {
            Stage stage = (Stage) bReady.getScene().getWindow();
//          tfEmail.getText();
//          Email van Driver moet nog toegevoegd worden in database en in de erd.
            int telefoonnr;
                if(tfPhone.getText().equals("")){
                    telefoonnr = 0;
                } else {
                    telefoonnr = Integer.parseInt(tfPhone.getText());
                }
                try {
                    LocalDate bd = LocalDate.parse(tfBirthdate.getText());
                    this.driver = new Driver(tfName.getText(),tfInserts.getText(),tfSirname.getText(),telefoonnr,bd);
                    stage.close();
                }catch (Exception e){
                    System.out.println(e);
                    lErrorGegevens.setText("Gegevens niet correct ingevuld!");
                }
        } else if (event.getSource() == bBack) {
            Stage stage = (Stage) bBack.getScene().getWindow();

        }
    }
}