package org.openjfx;

import com.dao.DriverDao;
import com.dao.OrderDao;
import com.dao.RouteDao;
import com.dao.TimeslotDao;
import com.entity.Driver;
import com.entity.Order;
import com.entity.OrderLine;
import com.entity.Timeslot;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;

public class AddRouteController {

    private DriverDao d;
    private OrderDao o;
    private TimeslotDao t;

    @FXML
    public TableView<Order> Orders;
    public TableColumn<Order, String> ID;
    public TableColumn<Order, String> Address;
    public TableColumn<Order, String> Postal;
    public TableColumn<Order, String> Date;
    public TableColumn<Order, String> Packages;


    @FXML
    private ComboBox<Driver> combo;
    public Button generate;
    public Button back;
    public DatePicker date;
    public ComboBox<Timeslot> timeslot;

    @FXML
    private void initialize() {
        d = new DriverDao();
        o = new OrderDao();
        t = new TimeslotDao();

        combo.setVisible(false);
        combo.setManaged(false);
        update();
    }

    public void update() {
        Orders.getItems().clear();
        combo.getItems().clear();
        timeslot.getItems().clear();
        loadTimezone();
        loadDates();
        loadOrders();
    }

    public void loadDates() {
        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    public void loadTimezone() {
        ObservableList<Timeslot> observableList = FXCollections.observableList(t.getAllTimeslots());
        timeslot.setItems(observableList);
        timeslot.setConverter(new StringConverter<>() {
            @Override
            public String toString(Timeslot timeslot) {
                if (timeslot != null) {
                    return timeslot.toString();
                }
                return "";
            }

            @Override
            public Timeslot fromString(String string) {
                return null;
            }
        });
    }

    public void loadOrders() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Address.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getAddres()));
        Postal.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getPostal()));
        Date.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getExpectedDeliveryDate()));
        Packages.setCellValueFactory(v -> new ReadOnlyStringWrapper(String.valueOf(v.getValue().getOrderlines().stream().mapToLong(OrderLine::getQuantity).sum())));

        Orders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Orders.getItems().setAll(o.getOrders());
    }

    public void generateRoute() {
        try {
            if (combo.getSelectionModel().getSelectedItem() != null) {
                ObservableList<Order> selectedItems = Orders.getSelectionModel().getSelectedItems();
                RouteDao r = new RouteDao();
                r.generateRoute(combo.getSelectionModel().getSelectedItem(), selectedItems, date.getValue(), timeslot.getSelectionModel().getSelectedItem());
                App.setPage("routes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBackButton() throws IOException {
        App.setPage("routes");
    }

    public void checkDrivers() {
        if (timeslot.getSelectionModel().getSelectedItem() != null && date.getValue() != null) {
            loadDrivers();
            combo.setManaged(true);
            combo.setVisible(true);
        }
    }

    private void loadDrivers() {
        ObservableList<Driver> observableList = FXCollections.observableList(d.getAvailableDrivers(date.getValue(), timeslot.getSelectionModel().getSelectedItem()));
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
}
