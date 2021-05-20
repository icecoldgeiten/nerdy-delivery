package org.openjfx;

import com.dao.AdminDao;
import com.dao.OrderDao;
import com.dao.RouteDao;
import com.entity.Customer;
import com.entity.Order;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class MainPageDriverController {
    private static Route route;

    private OrderDao o;
    private RouteDao r;
    private CalculateRoute calculateRoute;
    private Order selectedOrder;
    public static Order doubleClickedOrder;

    @FXML
    public TableView<Order> tvDeliveries;
    public TableColumn<Order, String> tcOrderID, tcStatus;
    public TableColumn<Order, Customer> tcCustomerID, tCustomerAdress;
    public ListView<DirectionsStep> directionsStep;
    public Label lDuration, lETA;
    public Button bDelivered, bNotHome;
    public Button submit;
    public Button logout;

    public void initialize() {
        o = new OrderDao();
        r = new RouteDao();
        calculateRoute = new CalculateRoute();

        //Buttons disabled because route and customer is not yet selected
        bDelivered.setDisable(true);
        bNotHome.setDisable(true);

        update();
    }

    public void update() {
        tvDeliveries.getItems().clear();
        loadDeliveries();
//        loadRoute();
    }

    public void loadDeliveries() {
        try {
            ObservableList<Order> orderDriver = FXCollections.observableArrayList(route.getOrders());
            tvDeliveries.setItems(orderDriver);
            setCellValueColumns();
        } catch (NullPointerException ex) {
            tvDeliveries.setPlaceholder(new Label("Selecteer route om bezorgingen zichtbaar te maken."));
        }
    }

    private void setCellValueColumns() {
        tcOrderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCustomerID.setCellValueFactory(new PropertyValueFactory<>("customer"));
        tCustomerAdress.setCellValueFactory(new PropertyValueFactory<>("customer"));
        tcStatus.setCellValueFactory(v -> new ReadOnlyStringWrapper(v.getValue().getOrderStatus().toString()));

        tcCustomerID.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Customer c, boolean bln) {
                super.updateItem(c, bln);
                if (c != null) {
                    setText(Integer.toString(c.getId()));
                }
            }
        });
        tCustomerAdress.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Customer c, boolean bln) {
                super.updateItem(c, bln);
                if (c != null) {
                    setText(c.getAddres() + c.getPostal());
                }
            }
        });
    }

    public void loadRoute() {
        try {
            DirectionsRoute directionsRoute = calculateRoute.RouteMaker(route.getOrders());

            lDuration.setText(route.getDuration() + " min");
            lETA.setText(Integer.toString(directionsRoute.legs.length));
            ;
            ObservableList<DirectionsStep> steps = FXCollections.observableList(Arrays.asList(directionsRoute.legs[0].steps));
            for (DirectionsLeg di : directionsRoute.legs) {
                System.out.println(di.toString());
            }

            directionsStep.setItems(steps);
            directionsStep.setCellFactory(new Callback<>() {
                @Override
                public ListCell<DirectionsStep> call(ListView<DirectionsStep> p) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(DirectionsStep t, boolean bln) {
                            super.updateItem(t, bln);
                            if (t != null) {
                                setText(t.htmlInstructions);
                            }
                        }
                    };
                }
            });
        } catch (RuntimeException ex) {
            directionsStep.setPlaceholder(new Label("WORK IN PROGRESS"));
        }
    }

    private void showWindow() throws IOException {
        URL url = getClass().getResource("/org.openjfx/orderdetails.fxml");
        final FXMLLoader loader = new FXMLLoader(url);
        final Parent root = loader.load();
        final Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Orderdetails: " + doubleClickedOrder.getId());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void OnMouseClickedCustomer(MouseEvent event) {
        if (event.getClickCount() == 2) {
            doubleClickedOrder = tvDeliveries.getSelectionModel().getSelectedItem();
            try {
                showWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (event.getClickCount() == 1) {
            selectedOrder = tvDeliveries.getSelectionModel().getSelectedItem();
            if (selectedOrder.getOrderStatus().getStatusCode().equals("NOTHOME") || selectedOrder.getOrderStatus().getStatusCode().equals("DELIVERED")) {
                bNotHome.setDisable(true);
                bDelivered.setDisable(true);
            } else {
                bNotHome.setDisable(false);
                bDelivered.setDisable(false);
            }
        }
    }

    @FXML
    public void onActionButtonDelivered() {
        o.updateStatus("DELIVERED", selectedOrder);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
        selectedOrder = null;
        update();
    }

    @FXML
    public void onActionButtonNotHome() {
        o.updateStatus("NOTHOME", selectedOrder);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
        selectedOrder = null;
        update();
    }

    //Setter
    public static void setRoute(Route route) {
        MainPageDriverController.route = route;
    }

    @FXML
    public void finishRoute() throws IOException {
        for (Order order : route.getOrders()) {
            if (!order.getOrderStatus().getStatusCode().equals("DELIVERED") && !order.getOrderStatus().getStatusCode().equals("NOTHOME")) {
                final Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(App.getScene().getWindow());
                alert.setContentText("Status van bestelling(en) is niet juist");
                alert.show();
                return;
            }
            if (order.getOrderStatus().getStatusCode().equals("NOTHOME")) {
                r.removeOrder(route, order);
            }
        }
        r.setRouteStatus(route, "DELIVERED");
        App.setRoot("route_driver");
    }

    public void back() throws IOException {
        MainPageDriverController.setRoute(null);
        App.setRoot("route_driver");
    }
}
