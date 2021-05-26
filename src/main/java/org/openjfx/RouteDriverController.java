package org.openjfx;

import com.dao.DriverDao;
import com.dao.OrderDao;
import com.dao.RouteDao;
import com.dao.StatusDao;
import com.entity.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class RouteDriverController {
    private RouteDao r;
    private OrderDao o;

    @FXML
    public TableView<Route> routes;
    public TableColumn<Route, String> ID;
    public TableColumn<Route, String> timeSlotStart;
    public TableColumn<Route, String> timeSlotEnd;
    public TableColumn<Route, String> timeSlotName;

    @FXML
    private void initialize() {
        update();
    }

    public void update() {
        r = new RouteDao();
        o = new OrderDao();
        routes.getItems().clear();
        loadRoutes();
    }

    public void loadRoutes() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeSlotStart.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getTimeslot().getStartTime().toString()));
        timeSlotEnd.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getTimeslot().getEndTime().toString()));
        timeSlotName.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getTimeslot().getName()));
        routes.getItems().setAll(r.getDriveableRoutes(DriverDao.getLogedinDriver(), LocalDate.now()));
    }


    public void startRoute() {
        if (routes.getSelectionModel().getSelectedItem() != null) {
            Route route = routes.getSelectionModel().getSelectedItem();
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(App.getScene().getWindow());
            if (route.getRouteStatus().getStatusCode().equals("OUTFORDELIVERY") || route.getRouteStatus().getStatusCode().equals("INTERRUPTED")) {
                alert.setContentText("Weet je zeker dat je route " + route.getId() + " " + route.getTimeslot().getName() + " gaat vervolgen?");
            } else {
                alert.setContentText("Weet je zeker dat je route " + route.getId() + " " + route.getTimeslot().getName() + " gaat rijden?");
            }
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        MainPageDriverController.setRoute(route);
                        if (!route.getRouteStatus().getStatusCode().equals("OUTFORDELIVERY")) {
                            if (!route.getRouteStatus().getStatusCode().equals("INTERRUPTED")) {
                                for (Order d : route.getOrders()) {
                                    o.updateStatus("OUTFORDELIVERY", d);
                                }
                            }
                            r.setRouteStatus(route, "OUTFORDELIVERY");
                        }
                        App.setRoot("mainpage_driver");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void logout() throws IOException {
        DriverDao.setLogedinDriver(null);
        App.setRoot("start_screen");
    }
}
