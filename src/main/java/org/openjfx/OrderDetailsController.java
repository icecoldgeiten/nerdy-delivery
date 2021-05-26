package org.openjfx;

import com.dao.DriverDao;
import com.entity.OrderLine;
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
    private DriverDao driverDao;

    @FXML
    public TableView<OrderLine> tvOrderLines;
    public TableColumn<OrderLine, Integer> tcStockItemID;
    public TableColumn<OrderLine, String> tcDescription;
    public TableColumn<OrderLine, Integer> tcQuantity;
    public Button bBack;

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
            List<OrderLine> orderlinesSelectedOrder = new ArrayList<>(MainPageDriverController.doubleClickedOrder.getOrderlines());
            ObservableList<OrderLine> orderLines = FXCollections.observableArrayList(orderlinesSelectedOrder);
            tvOrderLines.setItems(orderLines);
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
            e.printStackTrace();
        }
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        App.setRoot("mainpage_driver");
    }
}
