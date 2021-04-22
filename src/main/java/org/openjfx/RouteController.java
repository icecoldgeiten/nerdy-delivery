package org.openjfx;

import com.dao.RouteDao;
import com.entity.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;


public class RouteController {
    public Button back;
    public Button done;
    public Button new_route;

    public RouteController() {
        RouteDao route = new RouteDao();
        ListView<Route> list = new ListView<>();
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
                            setText(t.getId() + ":" + t.getDriver());
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void handBackButton() {
        System.out.println("yeet");
    }
}
