package org.openjfx;

import com.dao.DriverDao;
import com.dao.RouteDao;
import com.dao.TimeslotDao;
import com.entity.Driver;
import com.entity.Order;
import com.entity.Route;
import com.entity.Timeslot;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;

public class EditRouteController {
    private static Route route;

    private DriverDao d;
    private RouteDao r;
    private TimeslotDao t;

    @FXML
    public Label id;
    public Button back;
    public Button add;
    public Button remove;
    public DatePicker date;
    public ComboBox<Timeslot> timeSlot;
    public ComboBox<Driver> combo;
    public TableView<Order> tableView;
    public TableColumn<Order, String> ID;
    public TableColumn<Order, String> Customer;
    public TableColumn<Order, String> Address;
    public TableColumn<Order, String> Status;


    @FXML
    private void initialize() {
        d = new DriverDao();
        r = new RouteDao();
        t = new TimeslotDao();
        Default();
        update();
    }

    private void Default() {
        combo.setValue(route.getDriver());
        id.setText(route.getId().toString());
        tableView.getSortOrder().add(ID);
        tableView.setPlaceholder(new Label("Deze route heeft geen bestellingen"));
        combo.setDisable(true);
        add.setDisable(false);
        remove.setDisable(false);
        date.setDisable(false);
        timeSlot.setDisable(false);
    }

    private void update() {
        combo.getItems().clear();
        tableView.getItems().clear();
        checkStatus();
        checkDrivers();
        loadOrders();
        loadDate();
        loadTimeSlot();
    }

    private void checkStatus() {
        if (!route.getRouteStatus().getStatusCode().equals("OPENFORDELIVERY")) {
            combo.setDisable(true);
            add.setDisable(true);
            remove.setDisable(true);
            date.setDisable(true);
            timeSlot.setDisable(true);
        }
    }

    @FXML
    private void loadDrivers() {
        ObservableList<Driver> observableList = FXCollections.observableList(d.getAvailableDrivers(date.getValue(), timeSlot.getSelectionModel().getSelectedItem()));
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

    @FXML
    public void loadOrders() {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Customer.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getCustomername()));
        Address.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getAddres()));
        Status.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getOrderStatus().toString()));
        tableView.getItems().setAll(route.getOrders());
    }

    @FXML
    public void loadDate() {
        date.setValue(route.getDate());
        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    @FXML
    public void loadTimeSlot() {
        ObservableList<Timeslot> observableList = FXCollections.observableList(t.getAllTimeslots());
        timeSlot.setItems(observableList);
        timeSlot.getSelectionModel().select(selectTimeslot(observableList));
        timeSlot.setConverter(new StringConverter<>() {
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


    @FXML
    public void addOrders() throws IOException {
        EditRouteAddOrderController.setRoute(route);
        App.setPage("edit_route_add_order");
    }

    @FXML
    public void changeDriver() {
        if (combo.getSelectionModel().getSelectedItem() != null) {
            r.updateDriver(route, combo.getSelectionModel().getSelectedItem(), date.getValue(), timeSlot.getValue());
        }
    }

    public Integer selectTimeslot(ObservableList<Timeslot> timeslots) {
        int index = 0;
        if (route == null) {
            return 0;
        }
        for (Timeslot d : timeslots) {
            if (d.equals(route.getTimeslot())) {
                return index;
            }
            index++;
        }
        return null;
    }

    public void checkDrivers() {
        System.out.println(date.getValue());
        if (timeSlot.getSelectionModel().getSelectedItem() != null && date.getValue() != null) {
            loadDrivers();
            combo.setDisable(false);
        }
    }

    //Setters
    public static void setRoute(Route route) {
        EditRouteController.route = route;
    }

    public void deleteSelected() throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            r.removeOrder(route, tableView.getSelectionModel().getSelectedItem());
            EditRouteController.setRoute(route);
            App.setPage("edit_route");
        }
    }

    @FXML
    public void handleBackButton() throws IOException {
        changeDriver();
        App.setPage("routes");
    }
}