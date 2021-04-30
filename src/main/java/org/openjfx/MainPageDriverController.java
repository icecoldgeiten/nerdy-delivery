//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.openjfx;

import com.dao.DriverDao;
import com.entity.Customer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainPageDriverController {
    DriverDao driverDao;
    @FXML
    TableView<Customer> tvDeliveries;
    @FXML
    TableColumn<Customer, String> tcAdress;
    @FXML
    TableColumn<Customer, String> tcPostalcode;
    @FXML
    TableColumn<Customer, String> tcLocation;
    @FXML
    TableColumn<Customer, Button> tcDelivered;
    @FXML
    TableColumn<Customer, Button> tcNotHome;
    ObservableList<Customer> lijstCustomers;

    public MainPageDriverController() {
    }

    public void initialize() {
        this.driverDao = new DriverDao();
        this.lijstCustomers = FXCollections.observableList(this.driverDao.listCustomers());
        this.loadDeliveries();
    }

    public void loadDeliveries() {
////        this.tcAdress.setCellValueFactory((cellData) -> {
////            String rowIndex = ((Customer)cellData.getValue()).getAddres();
////            return new ReadOnlyStringWrapper(rowIndex);
//        });
////        this.tvDeliveries.getItems().addAll(new Customer[0]);
//    }
    }
}
