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
        tableView.getItems().setAll(route.getAllRoutes());
    }

    @FXML
    public void handleMouseClick() {
        try {
            EditRouteController.setRoute(tableView.getSelectionModel().getSelectedItem());
            App.setRoot("edit_route");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNewRoute(ActionEvent event) throws IOException {
        App.setRoot("add_route");
    }
}
