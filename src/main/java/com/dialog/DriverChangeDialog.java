package com.dialog;

import com.dao.DriverDao;
import com.entity.Driver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DriverChangeDialog {
    Dialog<String> dialogChangeDriver;
    DriverDao driverDao;
    Driver driver;
    Boolean isChange = false;

    @FXML
    Button bBack, bChange;
    @FXML
    TextField tfName, tfInserts, tfSirname, tfBirthday, tfPhone, tfVehicle;
    @FXML
    PasswordField pfChangePassword;
    @FXML
    Label lIntro, lChangeAlert;
    @FXML
    CheckBox cbVehicle;

    public DriverChangeDialog(int clickedOnName) {
        driverDao = new DriverDao();

        //nieuwe dialoog maken
        dialogChangeDriver = new Dialog<>();
        Scene scene = new Scene(new Group());
        Stage changeDialogStage = new Stage();
        changeDialogStage.setHeight(360);
        changeDialogStage.setWidth(340);
        try {
            driver = driverDao.searchDriver(clickedOnName);
            changeDialogStage.setTitle("Bewerk bezorger " + driver.getName());

            //Nieuwe root pane
            BorderPane root = new BorderPane();

            //Nieuwe pane's maken voor in de root:
            FlowPane fp1 = new FlowPane();
            BorderPane.setMargin(fp1, new Insets(10));

            GridPane gp = new GridPane();
            gp.setHgap(20);
            gp.setVgap(10);

            FlowPane fp2 = new FlowPane();
            fp2.setVgap(10);
            fp2.setHgap(30);
            BorderPane.setMargin(fp2, new Insets(20));

            //panes toevoegen aan root.
            root.setTop(fp1);
            root.setCenter(gp);
            root.setBottom(fp2);

            //initialeren elementen:

            //LABEL
            lIntro = new Label("Vul onderstaande velden in:");
            lIntro.setFont(new Font("Segoe UI", 16));
            lChangeAlert = new Label("");

            //TEXTFIELD
            try {
                //new textfields.
                tfName = new TextField(driver.getName());
                tfInserts = new TextField(driver.getInserts());
                tfSirname = new TextField(driver.getSirname());
                tfBirthday = new TextField(driver.getBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                tfPhone = new TextField(Integer.toString(driver.getPhonenumber()));
                tfVehicle = new TextField(Integer.toString(driver.getVehicle()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            //Password field
            pfChangePassword = new PasswordField();
            pfChangePassword.setPromptText("Nieuw wachtwoord..");


            //BUTTON
            bBack = new Button("Terug");
            bChange = new Button("Bewerk");

            //CHECKBOX
            cbVehicle = new CheckBox("Voertuig");
            if (driver.getVehicle() == 1) {
                cbVehicle.setSelected(true);
            }

            //toevoegen elementen top:
            fp1.getChildren().add(lIntro);

            //toevoegen elementen center:
            gp.add(new Label("Naam"), 1, 0);
            gp.add(tfName, 2, 0);
            gp.add(new Label("Tussenvoegsels"), 1, 1);
            gp.add(tfInserts, 2, 1);
            gp.add(new Label("Achternaam"), 1, 2);
            gp.add(tfSirname, 2, 2);
            gp.add(new Label("Geboortedatum"), 1, 3);
            gp.add(tfBirthday, 2, 3);
            gp.add(new Label("Telefoonnummer"), 1, 4);
            gp.add(tfPhone, 2, 4);
            gp.add(new Label("Wachtwoord"), 1, 5);
            gp.add(pfChangePassword, 2, 5);
            gp.add(cbVehicle, 2, 6);

            //toevoegen elementen bottom:
            fp2.getChildren().add(bBack);
            fp2.getChildren().add(bChange);
            fp2.getChildren().add(lChangeAlert);

            //root toevoegen aan scene en scene toevoegen aan dialog, dialog zichtbaar maken:
            scene.setRoot(root);
            changeDialogStage.setScene(scene);
            changeDialogStage.show();

            bBack.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    changeDialogStage.close();
                }
            });

            bChange.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LocalDate bd;
                    try {
                        int phone = Integer.parseInt(tfPhone.getText());
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            bd = LocalDate.parse(tfBirthday.getText(), formatter);
                        } catch (Exception e) {
                            bd = LocalDate.now();
                        }
                        driverDao.changeDriver(clickedOnName, tfName.getText(), tfInserts.getText(), tfSirname.getText(), phone, bd, cbVehicle.isSelected());
                        if (!pfChangePassword.getText().equals("")) {
                            driverDao.changePassword(pfChangePassword.getText(), clickedOnName);
                        }
                        changeDialogStage.close();
                    } catch (NumberFormatException e) {
                        lChangeAlert.setText("Graag de velden invullen!");
                    }
                }
            });
        } catch (NullPointerException e) {
            System.out.println("No result");
        }
    }

    public Boolean getChange() {
        return isChange;
    }
}