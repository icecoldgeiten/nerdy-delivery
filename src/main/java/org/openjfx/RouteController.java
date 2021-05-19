package org.openjfx;

import com.dao.RouteDao;
import com.entity.Route;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class RouteController {
    @FXML
    public TableView<Route> tableView;
    public TableColumn<Route, String> ID;
    public TableColumn<Route, String> Driver;
    public TableColumn<Route, String> Status;
    public TableColumn<Route, String> Date;
    public TableColumn<Route, String> TimeSlot;



    public RouteDao route;

    @FXML
    public void initialize() {
        route = new RouteDao();
        updateRoutes();

        //set default values
        tableView.getSortOrder().add(ID);
        tableView.setPlaceholder(new Label("Er zijn geen openstaande routes"));
    }

    public void updateRoutes() {
        tableView.getItems().clear();
        loadRoutes();
    }

    public void loadRoutes() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Driver.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getDriver().toString()));
        Status.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getRouteStatus().toString()));
        Date.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getDate().toString()));
        TimeSlot.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getTimeslot().toString()));
        tableView.getItems().setAll(route.getAllRoutes());
    }

    @FXML
    public void handleMouseClick() {
        try {
            Route route = tableView.getSelectionModel().getSelectedItem();
            if (!route.getRouteStatus().getStatusCode().equals("OUTFORDELIVERY")) {
                EditRouteController.setRoute(route);
                App.setPage("edit_route");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNewRoute() throws IOException {
        App.setPage("add_route");
    }
}
