package org.openjfx;

import com.dao.RouteDao;
import com.entity.Route;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class RouteController {
    @FXML
    public ListView<Route> list;

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
        ObservableList<Route> observableList = FXCollections.observableList(route.getAllRoutes());
        list.setItems(observableList);

        list.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Route> call(ListView<Route> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Route t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("Route nummer: " + t.getId() + ", bestuurder: " + t.getDriver().getName());
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void handleMouseClick() {
        try {
            Route route = list.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNewRoute(ActionEvent event) throws IOException {
        App.setRoot("add_route");
    }
}
