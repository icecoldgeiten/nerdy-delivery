package org.openjfx;

import com.dao.DriverDao;
import com.entity.Orderline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsController implements Initializable {
    DriverDao driverDao;
    MainPageDriverController mpdc;

    @FXML TableView<Orderline> tvOrderLines;
    @FXML TableColumn<Orderline, Integer> tcStockItemID;
    @FXML TableColumn<Orderline, String> tcDescription;
    @FXML TableColumn<Orderline, Integer> tcQuantity;
    @FXML Button bBack;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateOrderLines();
        driverDao = new DriverDao();
    }

    public void updateOrderLines() {
        tvOrderLines.getItems().clear();
        loadOrderLines();
    }

    public void loadOrderLines() {
        try {
            List<Orderline> orderlinesSelectedOrder = new ArrayList<>(MainPageDriverController.doubleClickedOrder.getOrderlines());
            System.out.println(MainPageDriverController.doubleClickedOrder.getId());
            ObservableList<Orderline> orderlines = FXCollections.observableArrayList(orderlinesSelectedOrder);
            tvOrderLines.setItems(orderlines);
            setCellValueColumns();
        } catch (NullPointerException ex) {
            tvOrderLines.setPlaceholder(new Label("Er is iets fouts gegaan!"));
        }
    }

    private void setCellValueColumns() {
        try {
            tcStockItemID.setCellValueFactory(new PropertyValueFactory<>("stockitemid"));
            tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        mpdc.updateDeliveries();
            App.setRoot("mainpage_driver");
        }
}