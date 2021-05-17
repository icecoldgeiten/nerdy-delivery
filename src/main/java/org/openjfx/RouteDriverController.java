package org.openjfx;

import com.dao.DriverDao;
import com.entity.Route;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class RouteDriverController {
    @FXML
    public TableView<Route> routes;
    public TableColumn<Route, String> ID;
    public TableColumn<Route, String> timeIndication;
    public TableColumn<Route, String> timeSlot;

    @FXML
    private void initialize() {
        update();
    }

    public void update() {
        routes.getItems().clear();
        loadRoutes();
    }

    public void loadRoutes() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        timeIndication.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getDriver().toString()));
//        Status.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getRouteStatus().toString()));
        routes.getItems().setAll(DriverDao.getLogedinDriver().getRoutes());
    }


    public void startRoute() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(App.getScene().getWindow());
        alert.setContentText("Weet je zeker dat je route " + routes.getSelectionModel().getSelectedItem().getId() + " gaat rijden?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    MainPageDriverController.setRoute(routes.getSelectionModel().getSelectedItem());
                    App.setRoot("mainpage_driver");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
