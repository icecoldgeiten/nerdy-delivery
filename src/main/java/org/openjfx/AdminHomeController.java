package org.openjfx;

import com.dao.DriverDao;
import com.dao.OrderDao;
import com.entity.Driver;
import com.entity.Order;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminHomeController {
    private OrderDao orderDao;
    private DriverDao driverDao;

    @FXML
    public TableView<Order> Order;
    public TableColumn<Order, String> OrderID;
    public TableColumn<Order, String> Address;
    public TableColumn<Order, String> Customer;

    @FXML
    public TableView<Driver> Driver;
    public TableColumn<Driver, String> DriverID;
    public TableColumn<Driver, String> Name;
    public TableColumn<Driver, String> Surname;
    public TableColumn<Driver, String> BirthDate;
    public TableColumn<Driver, String> Number;
    public TableColumn<Driver, String> Vehicle;

    @FXML
    private void initialize() {
        orderDao = new OrderDao();
        driverDao = new DriverDao();
        update();
    }

    private void update() {
        Order.getItems().clear();
        Driver.getItems().clear();
        loadOrders();
        loadDrivers();
    }

    public void loadOrders() {
        OrderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Address.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getAddres()));
        Customer.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getCustomer().getCustomername()));
        Order.getItems().setAll(orderDao.getOrders());
    }

    public void loadDrivers() {
        DriverID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getName()));
        Surname.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getSirname()));
        BirthDate.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getBirthdate().toString()));
        Number.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getPhonenumber().toString()));
        Vehicle.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getVehicle().toString()));
        Driver.getItems().setAll(driverDao.getAllActiveDrivers());
    }
}
