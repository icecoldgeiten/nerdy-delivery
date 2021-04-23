package org.openjfx;

import com.dao.DriverDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class Register_driverController {

    @FXML
    public Button bReady;
    public Button bBack;
    public TextField tfName;
    public TextField tfInserts;
    public TextField tfSirname;
    public TextField tfPhone;
    public Label lErrorGegevens;
    public EntityManager em;
    public DatePicker dpBirthday;
    public TextField tfEmail;
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

    public void handleBackButtonAction(ActionEvent event) {
        Stage stage = (Stage) bBack.getScene().getWindow();
        stage.close();
    }
}
