package org.openjfx;

import com.dao.OrderDao;
import com.dao.RouteDao;
import com.entity.Order;
import com.entity.Route;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;


public class EditRouteAddOrderController {
    private static Route route;
    OrderDao o;

    @FXML
    public TableView<Order> Orders;
    public TableColumn<Order, String> ID;
    public TableColumn<Order, String> Address;
    public TableColumn<Order, String> Postal;
    public TableColumn<Order, String> Date;

    @FXML
    public Button add;
    public Button back;

    @FXML
    private void initialize() {
        o = new OrderDao();
        update();
    }

    public void update() {
        Orders.getItems().clear();
        loadOrders();
    }

    public void loadOrders() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Address.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getAddres()));
        Postal.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getPostal()));
        Date.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getExpectedDeliveryDate()));
        Orders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Orders.getItems().setAll(o.getOrders());
    }

    public void addOrders() {
        try {
            ObservableList<Order> orders = Orders.getSelectionModel().getSelectedItems();
            if (orders != null) {
                RouteDao r = new RouteDao();
                r.updateRoute(route, orders);
                EditRouteController.setRoute(route);
                App.setPage("edit_route");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBackButton() throws IOException {
        EditRouteController.setRoute(route);
        App.setPage("edit_route");
    }

    public static void setRoute(Route route) {
        EditRouteAddOrderController.route = route;
    }
}
