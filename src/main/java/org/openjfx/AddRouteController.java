package org.openjfx;

import com.dao.DriverDao;
import com.dao.OrderDao;
import com.dao.RouteDao;
import com.entity.Driver;
import com.entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

public class AddRouteController {
    DriverDao d;
    OrderDao o;

    @FXML
    private ComboBox<Driver> combo;
    public ListView<Order> list;
    public Button generate;
    public Button back;

    @FXML
    private void initialize() {
        d = new DriverDao();
        o = new OrderDao();
        update();
    }

    public void update() {
        list.getItems().clear();
        combo.getItems().clear();
        loadDrivers();
        loadOrders();
    }

    private void loadDrivers() {
        ObservableList<Driver> observableList = FXCollections.observableList(d.getAllDrivers());
        combo.setItems(observableList);
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
                            setText(t.getId() + ":" + t.getCustomer().Address());
                        }
                    }
                };
            }
        });
    }

    public void generateRoute(ActionEvent actionEvent) {
        try {
            if (combo.getSelectionModel().getSelectedItem() != null) {
                ObservableList<Order> selectedItems = list.getSelectionModel().getSelectedItems();
                RouteDao r = new RouteDao();
                r.generateRoute(combo.getSelectionModel().getSelectedItem(), selectedItems);
                App.setPage("routes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        App.setPage("routes");
    }
}
