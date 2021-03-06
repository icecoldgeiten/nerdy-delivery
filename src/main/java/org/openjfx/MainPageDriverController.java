package org.openjfx;

import com.dao.OrderDao;
import com.dao.RouteDao;
import com.entity.Customer;
import com.entity.Order;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;


public class MainPageDriverController {
    private Dotenv env;

    private static Route route;
    private DirectionsRoute directionsRoute;

    private OrderDao o;
    private RouteDao r;
    private CalculateRoute calculateRoute;
    private Order selectedOrder;
    public static Order doubleClickedOrder;

    @FXML
    private WebView webView;

    @FXML
    public TableView<Order> tvDeliveries;
    public TableColumn<Order, String> tcOrderID, tcStatus;
    public TableColumn<Order, Customer> tcCustomerID, tCustomerAdress;
    public Label lDuration;
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
        loadRoute();
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
            directionsRoute = calculateRoute.RouteMaker(route.getOrders());
            setTimeIndication();
            loadWebview();
        } catch (RuntimeException ignored) {
        }
    }

    public void loadWebview() {
        WebEngine engine = webView.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.load(generateRouteUrl().toString());
    }

    public void setTimeIndication() {
        long total = 0;
        for (DirectionsLeg l : directionsRoute.legs) {
            total = total + l.duration.inSeconds;
        }
        String timeString = String.format("%02d:%02d:%02d", total / 3600, (total % 3600) / 60, total % 60);

        lDuration.setText(timeString + " min");
    }

    public StringBuilder generateRouteUrl() {
        StringBuilder route = new StringBuilder();
        int index = 1;
        route.append("https://www.google.com/maps/dir/");
        for (DirectionsLeg directionsLeg : directionsRoute.legs) {
            route.append(directionsLeg.startLocation);
            route.append("/");
            if (index == directionsRoute.legs.length) {
                route.append(directionsLeg.endLocation);
            }
            index++;
        }
        return route;
    }

    @FXML
    public void showMaps() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(generateRouteUrl().toString()));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
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
            try {
                selectedOrder = tvDeliveries.getSelectionModel().getSelectedItem();
                if (selectedOrder.getOrderStatus().getStatusCode().equals("NOTHOME") || selectedOrder.getOrderStatus().
                        getStatusCode().equals("DELIVERED")) {
                    bNotHome.setDisable(true);
                    bDelivered.setDisable(true);
                } else {
                    bNotHome.setDisable(false);
                    bDelivered.setDisable(false);
                }
            } catch (NullPointerException e) {
                System.out.println("No click");
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

    @FXML
    public void finishRoute() throws IOException {
        if (!checkOrders(route.getOrders())) {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(App.getScene().getWindow());
            alert.setContentText("Status van bestelling(en) is niet juist");
            alert.show();
            return;
        }
        removeOrders();
        r.setRouteStatus(route, "DELIVERED");
        App.setRoot("route_driver");
    }

    public void removeOrders() {
        for (Order or : route.getOrders()) {
            if (or.getOrderStatus().getStatusCode().equals("NOTHOME")) {
                r.removeNotHome(route, or);
            }
        }
    }

    public Boolean checkOrders(Set<Order> orders) {
        for (Order order : orders) {
            if (!order.getOrderStatus().getStatusCode().equals("DELIVERED") && !order.getOrderStatus().getStatusCode().equals("NOTHOME")) {
                return false;
            }
        }
        return true;
    }

    @FXML
    public void back() throws IOException {
        MainPageDriverController.setRoute(null);
        App.setRoot("route_driver");
    }

    //Setter
    public static void setRoute(Route route) {
        MainPageDriverController.route = route;
    }
}
