package org.openjfx;

import com.dao.RouteDao;
import com.entity.Route;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;


public class RouteController {
    public Button back;
    public Button done;
    public Button new_route;
    public ListView<String> list;

    public RouteDao route;

    @FXML
    public void initialize() {
        route = new RouteDao();
        updateRoutes();
    }

    public void updateRoutes() {
        list.getItems().clear();
        loadRoutes();
    }

    public void loadRoutes() {
        List<Route> routes = route.getAllRoutes();

        for (Route r : routes) {
            list.getItems().add(r.toString());
        }
    }

    @FXML
    public void handBackButton() {
        System.out.println("yeet");
    }
}
