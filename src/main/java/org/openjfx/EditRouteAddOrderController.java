package org.openjfx;

import com.dao.OrderDao;
import com.dao.RouteDao;
import com.entity.Order;
import com.entity.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;

public class EditRouteAddOrderController {
    private static Route route;
    OrderDao o;

    @FXML
    public ListView<Order> list;
    public Button add;
    public Button back;

    @FXML
    private void initialize() {
        o = new OrderDao();
        update();
    }

    public void update() {
        list.getItems().clear();
        loadOrders();
    }

    public void loadOrders() {
        ObservableList<Order> observableList = FXCollections.observableList(o.getOrders());
        list.setItems(observableList);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Order> call(ListView<Order> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Order t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getId() + ":" + t.getCustomer().toString());
                        }
                    }
                };
            }
        });
    }

    public void addOrders(ActionEvent actionEvent) {
        try {
            if (list.getSelectionModel().getSelectedItems() != null) {
                ObservableList<Order> selectedItems = list.getSelectionModel().getSelectedItems();
                RouteDao r = new RouteDao();
                r.updateRoute(route, selectedItems);
                EditRouteController.setRoute(route);
                App.setRoot("edit_route");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        EditRouteController.setRoute(route);
        App.setRoot("edit_route");
    }

    public static void setRoute(Route route) {
        EditRouteAddOrderController.route = route;
    }
}
