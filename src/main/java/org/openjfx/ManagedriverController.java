package org.openjfx;

import com.dao.DriverDao;
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
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagedriverController implements Initializable {
    private DriverDao driverDao;

    @FXML
    public ListView<Driver> lvDrivers;
    public Button bNew, bDelete, bChange;
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
        ObservableList<Driver> observableList = FXCollections.observableList(driverDao.getAllDrivers());
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
        //Before clicking on mouse labels are empty
        lName.setText("");
        lInserts.setText("");
        lSirname.setText("");
        lBirthday.setText("");
        lPhone.setText("");
        lVehicle.setText("");
        lLicense.setText("");
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
                for (Driver d : driverDao.getAllDrivers()) {
                    String id = Integer.toString(d.getId());
                    if (lvDrivers.getSelectionModel().getSelectedItem().getId() == d.getId()) {
                        lName.setText(d.getName());
                        lInserts.setText(d.getInserts());
                        lSirname.setText(d.getSirname());
                        String bd = d.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        lBirthday.setText(bd);
                        try {
                            lPhone.setText(Integer.toString(d.getPhonenumber()));
                        }catch (NullPointerException ex){
                            lPhone.setText("");
                        }
                        lVehicle.setText(Integer.toString(d.getVehicle()));
                        lLicense.setText(Integer.toString(d.getLincenseNr()));
                    }
                }
            }
        });
    }

    //Clicking on 'Bewerk..' button opens new dialog with textfiels to change the driver.
    @FXML
    void actionButtonChange(ActionEvent event) {
        new DriverChangeDialog(clickedDriver);
    }

    //GETTER
    public int getClickedDriver() {
        return clickedDriver;
    }

    public void rowDelete(ActionEvent event) {
        Driver driver = driverDao.searchDriver(clickedDriver);
        try {
            if (driver.getRoutes().isEmpty()) {
                System.out.println("Mag wel!" + driver.getName());
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Verwijderen");
                alert1.setHeaderText("Bezorger verwijderen?");
                alert1.setContentText("Weet u zeker dat u deze bezorger wilt verwijderen?");

                Optional<ButtonType> result = alert1.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    driverDao.rowDelete(clickedDriver);
                }
                updateDrivers();
            } else {
                System.out.println("Mag niet!" + driver.getName());
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Waarschuwing!");
                alert2.setHeaderText("Bezorger kan niet worden verwijderd!");
                alert2.setContentText("Aan deze bezorger zitten routes gekoppeld.");
                Optional<ButtonType> result = alert2.showAndWait();

            }
        }catch (Exception ex){
            System.out.println(ex);
        }
    }


    public void newBezorger() throws IOException {
        App.setPage("register_driver");
    }
}
