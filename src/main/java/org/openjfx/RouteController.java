package org.openjfx;

import com.dao.RouteDao;
import com.entity.Driver;
import com.entity.Route;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class RouteController {
    public Button back;
    public Button done;
    public Button new_route;
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
                            setText(t.getId() + ":" + t.getDriver().getName());
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void handleMouseClick() {
        list.setOnMouseClicked(event -> System.out.println("clicked on " + list.getSelectionModel().getSelectedItem()));
    }

    @FXML
    public void handBackButton() {
        System.out.println("yeet");
    }
}
