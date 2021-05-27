package org.openjfx;

import com.dao.DriverDao;
import com.dialog.DriverChangeDialog;
import com.entity.Driver;
import com.entity.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagedriverController implements Initializable {
    private DriverDao driverDao;
    DriverChangeDialog dcd;
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
        ObservableList<Driver> observableList = FXCollections.observableList(driverDao.getAllActiveDrivers());
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
        //Check wich list index is selected then set txtContent value for that index
        clickedDriver = (lvDrivers.getSelectionModel().getSelectedItem().getId());
        for (Driver d : driverDao.getAllActiveDrivers()) {
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
                if(d.getVehicle() == 1){
                    lVehicle.setText("Ja");
                } else{
                    lVehicle.setText("Nee");
                }
                if(d.getLincenseNr() == 1){
                    lLicense.setText("Ja");
                } else {
                    lLicense.setText("Nee");
                }
            }
        }
    }

    //Clicking on 'Bewerk..' button opens new dialog with textfiels to change the driver.
    @FXML
    void actionButtonChange(ActionEvent event) {
        dcd = new DriverChangeDialog(clickedDriver);
    }

    //GETTER
    public int getClickedDriver() {
        return clickedDriver;
    }

    public void rowDelete(ActionEvent event) {
        Driver driver = driverDao.searchDriver(clickedDriver);
        ArrayList<Route> deliveredRoutes = new ArrayList<>();
        for(Route r : driver.getRoutes()){
            if(r.getRouteStatus().getStatusCode().equals("DELIVERED")){
                deliveredRoutes.add(r);
            }
        }
        try {
            if (driver.getRoutes().isEmpty() || (driver.getRoutes().size() == deliveredRoutes.size())) {
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

    public void refreshPage(ActionEvent event) {
        updateDrivers();
    }
}
