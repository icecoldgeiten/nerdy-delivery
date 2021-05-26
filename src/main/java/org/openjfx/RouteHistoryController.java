package org.openjfx;

import com.dao.RouteDao;
import com.entity.Route;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class RouteHistoryController {
    private RouteDao route;

    @FXML
    public TableView<Route> tableView;
    public TableColumn<Route, String> ID;
    public TableColumn<Route, String> Driver;
    public TableColumn<Route, String> Status;
    public TableColumn<Route, String> Date;
    public TableColumn<Route, String> TimeSlot;

    @FXML
    public void initialize() {
        route = new RouteDao();
        updateRoutes();

        //set default values
        Date.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(Date);
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
        tableView.getItems().setAll(route.getAllDeliveredRoutes());
    }

    public void back(ActionEvent event) throws IOException {
        App.setPage("routes");
    }
}
