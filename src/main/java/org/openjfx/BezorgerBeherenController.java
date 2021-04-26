package org.openjfx;
import java.net.URL;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.dao.DriverDao;
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
    public Button bOK;
    public Button bBACK;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverDao = new DriverDao();
        loadData();
    }

    private void loadData(){
        ObservableList<String> list = FXCollections.observableArrayList();

        try {
            for (Driver d : driverDao.listviewDrivers()) {
                list.add(d.getName());
            }
        }catch (Exception e) {
            System.out.println(e);
        }

        lvDrivers.setItems(list);
    }

    @FXML
    void buttonP(ActionEvent event) {
        Stage stage=(Stage) bOK.getScene().getWindow();
        stage.close();
    }

    @FXML
    void buttonB(ActionEvent event) {
        Stage stage=(Stage) bBACK.getScene().getWindow();
        stage.close();
    }





}
