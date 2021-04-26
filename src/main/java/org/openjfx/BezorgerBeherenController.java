package org.openjfx;

import java.net.URL;

import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.Dao.DriverDao;
import com.entity.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


public class BezorgerBeherenController implements Initializable {
    DriverDao driverDao;
    @FXML
    ListView<String> lvDrivers;
    @FXML
    public Button bBack, bDelete, bChange;
    @FXML
    public Label lName, lInserts, lSirname, lBirthday, lPhone, lVehicle, lLicense;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverDao = new DriverDao();
        loadData();
    }

    private void loadData() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            for (Driver d : driverDao.listviewDrivers()) {
                list.add(d.getName());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        lvDrivers.setItems(list);
    }

    // When mouse clicked on list show details of driver!
    @FXML
    private void handleMouseClick(MouseEvent arg0) {
        lvDrivers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Clicked on " + lvDrivers.getSelectionModel().getSelectedItem());
                for (Driver d : driverDao.listviewDrivers()) {
                    if (lvDrivers.getSelectionModel().getSelectedItem().equals(d.getName())) {
                        lName.setText(d.getName());
                        lInserts.setText(d.getInserts());
                        lSirname.setText(d.getSirname());
                        String bd = d.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        lBirthday.setText(bd);
                        lPhone.setText(Integer.toString(d.getPhone()));
                        lVehicle.setText(Integer.toString(d.getVehicle()));
                        lLicense.setText(Integer.toString(d.getLincenseNr()));

                    }
                }
            }
        });
    }



    @FXML
    void closeButtonBack(ActionEvent event) {
        Stage stage = (Stage) bBack.getScene().getWindow();
        stage.close();
    }
}
