package org.openjfx;

import com.Dao.DriverDao;
import com.dialog.DriverChangeDialog;
import com.entity.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class BezorgerBeherenController implements Initializable {
    DriverDao driverDao;
    @FXML
    ListView<Driver> lvDrivers;
    @FXML
    public Button bBack, bDelete, bChange;
    @FXML
    public Label lName, lInserts, lSirname, lBirthday, lPhone, lVehicle, lLicense;
    private int clickedDriver;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverDao = new DriverDao();
        updateDrivers();
    }

    public void updateDrivers() {
        lvDrivers.getItems().clear();
        loadDrivers();
    }

    private void loadDrivers() {
        ObservableList<Driver> observableList = FXCollections.observableList(driverDao.listviewDrivers());
        lvDrivers.setItems(observableList);


        lvDrivers.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Driver> call(ListView<Driver> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Driver t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getId() + ":" + t.getName());
                        }
                    }
                };
            }
        });
    }

    // When mouse clicked on list show details of driver!
    @FXML
    public void lvDriversOnMouseClicked() {
        //Detecting mouse clicked
        lvDrivers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Check wich list index is selected then set txtContent value for that index
                clickedDriver = (lvDrivers.getSelectionModel().getSelectedItem().getId());
                System.out.println("Clicked on " + lvDrivers.getSelectionModel().getSelectedItem());
                for (Driver d : driverDao.listviewDrivers()) {
                    String id = Integer.toString(d.getId());
                    if (lvDrivers.getSelectionModel().getSelectedItem().getId() == d.getId()) {
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

    //Clicking on 'Bewerk..' button opens new dialog with textfiels to change the driver.
    @FXML
    void actionButtonChange(ActionEvent event) {
        DriverChangeDialog dcd = new DriverChangeDialog(clickedDriver);
    }

    //GETTER

    public int getClickedDriver() {
        return clickedDriver;
    }

    public void rowDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verwijderen");
        alert.setHeaderText("Verwijder Bezorger!");
        alert.setContentText("Wilt u zeker dit verwijderen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            driverDao.rowDelete(clickedDriver);

        }
        else {
            /* ... user chose CANCEL or closed the dialog */

        }
        updateDrivers();
    }


}
