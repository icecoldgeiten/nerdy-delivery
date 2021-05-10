package org.openjfx;

import com.dao.DriverDao;
import com.entity.Orderline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsController implements Initializable {
    Orderline orderline;
    DriverDao driverDao;

    @FXML
    TableView<Orderline> tvOrderLines;
    @FXML TableColumn<Orderline,Integer> tcStockItemID;
    @FXML TableColumn<Orderline,String> tcDescription;
    @FXML TableColumn<Orderline,Integer> tcQuantity;
    @FXML Button bBack;

    public void initialize(URL url, ResourceBundle resourceBundle){
        driverDao = new DriverDao();
    }

    public void updateOrderLines(){
        tvOrderLines.getItems().clear();
        loadOrderLines();
    }

    public void loadOrderLines(){
        try {
            List<Orderline> orderlinesSelectedOrder = new ArrayList<>(MainPageDriverController.selectedOrder.getOrderlines());
            System.out.println(MainPageDriverController.selectedOrder.getId());
            ObservableList<Orderline> orderDriver = FXCollections.observableArrayList(orderlinesSelectedOrder);
            tvOrderLines.setItems(orderDriver);
            setCellValueColumns();
        } catch (NullPointerException ex) {
            tvOrderLines.setPlaceholder(new Label("Er is iets fouts gegaan!"));
        }
    }

    private void setCellValueColumns() {
        tcStockItemID.setCellValueFactory(new PropertyValueFactory<>("stockitemid"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

}
