package org.openjfx;

import com.dao.DriverDao;
import com.dao.RouteDao;
import com.entity.Driver;
import com.entity.Order;
import com.entity.Route;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;

public class EditRouteController {
    private static Route route;
    private DriverDao d;
    private RouteDao r;

    @FXML
    public Label id;
    public Button back;
    public Button add;
    public Button remove;
    public ComboBox<Driver> combo;
    public TableView<Order> tableView;
    public TableColumn<Order, String> ID;
    public TableColumn<Order, String> Customer;
    public TableColumn<Order, String> Address;

    @FXML
    private void initialize() {
        d = new DriverDao();
        r = new RouteDao();
        update();

        //Set default items
        id.setText(route.getId().toString());
        tableView.getSortOrder().add(ID);
        tableView.setPlaceholder(new Label("Deze route heeft geen bestellingen"));
    }

    public void update() {
        combo.getItems().clear();
        tableView.getItems().clear();
        loadDrivers();
        loadOrders();
    }

    @FXML
    private void loadDrivers() {
        ObservableList<Driver> observableList = FXCollections.observableList(d.getAllDrivers());
        combo.setItems(observableList);
        combo.getSelectionModel().select(selectDriver(observableList));
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Driver driver) {
                if (driver != null) {
                    return driver.toString();
                }
                return "";
            }

            @Override
            public Driver fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    public void loadOrders() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Customer.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getCustomername()));
        Address.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getAddres()));
        tableView.getItems().setAll(route.getOrders());
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        App.setRoot("routes");
    }

    @FXML
    public void addOrders(ActionEvent event) throws IOException {
        EditRouteAddOrderController.setRoute(route);
        App.setRoot("edit_route_add_order");
    }

    @FXML
    public void changeDriver(ActionEvent event) {
        if (combo.getSelectionModel().getSelectedItem() != null) {
            r.updateDriver(route, combo.getSelectionModel().getSelectedItem());
        }
    }

    public Integer selectDriver(ObservableList<Driver> drivers) {
        int index = 0;
        if (route == null) {
            return 0;
        }
        for (Driver d : drivers) {
            if (d.equals(route.getDriver())) {
                return index;
            }
            index++;
        }
        return null;
    }

    //Setters
    public static void setRoute(Route route) {
        EditRouteController.route = route;
    }

    public void deleteSelected(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            r.removeOrder(route, tableView.getSelectionModel().getSelectedItem());
            update();
        }
    }
}