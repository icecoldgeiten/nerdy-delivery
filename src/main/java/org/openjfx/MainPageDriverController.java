package org.openjfx;

import com.dao.DriverDao;
import com.entity.Customer;
import com.entity.Order;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainPageDriverController {
    private DriverDao driverDao;
    private CalculateRoute calculateRoute;
    private Route selectedRoute;
    private Order selectedOrder;
    public static Order doubleClickedOrder;

    @FXML
    public TableView<Order> tvDeliveries;
    public TableColumn<Order, String> tcOrderID, tcStatus;
    public TableColumn<Order, Customer> tcCustomerID, tCustomerAdress;
    public Label lDuration, lETA;
    public ComboBox<Route> cbRoutes;
    public ListView<DirectionsStep> directionsStep;
    public Button bDelivered, bNotHome;

    public void initialize() {
        driverDao = new DriverDao();
        calculateRoute = new CalculateRoute();

        loadComboBoxRoute();
        updateDeliveries();

        //Buttons disabled because route and customer is not yet selected
        bDelivered.setDisable(true);
        bNotHome.setDisable(true);
    }

    public void updateDeliveries() {
        tvDeliveries.getItems().clear();
        loadDeliveries();
    }

    public void loadDeliveries() {
        try {
            List<Order> ordersSelectedRoute = new ArrayList<>(selectedRoute.getOrders());
            ObservableList<Order> orderDriver = FXCollections.observableArrayList(ordersSelectedRoute);
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
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    public void loadComboBoxRoute() {
        List<Route> routelist = new ArrayList<>(DriverDao.getLogedinDriver().getRoutes());
        ObservableList<Route> routes = FXCollections.observableList(routelist);

        cbRoutes.setItems(routes);
        cbRoutes.setConverter(new StringConverter<>() {
            @Override
            public String toString(Route route) {
                if (route != null) {
                    return "Route: " + route.getId() + " | Duratie: " + route.getDuration() + "min";
                }
                return "";
            }

            @Override
            public Route fromString(String string) {
                return null;
            }
        });
    }

    public void loadRouteAfterChangeComboBoxRoute() {
        try {
            lDuration.setText(cbRoutes.getValue().getDuration() + " min");
            lETA.setText(Integer.toString(calculateRoute.RouteMaker(cbRoutes.getValue().getOrders()).legs.length));

            DirectionsRoute route = calculateRoute.RouteMaker(cbRoutes.getValue().getOrders());
            ObservableList<DirectionsStep> steps = FXCollections.observableList(Arrays.asList(route.legs[0].steps));
            for (DirectionsLeg di : route.legs) {
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
        selectedRoute = cbRoutes.getValue();
        updateDeliveries();
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
    public void OnMouseClickedCustomer() {
        tvDeliveries.setOnMouseClicked(event -> {
            selectedOrder = tvDeliveries.getSelectionModel().getSelectedItem();
            if (selectedOrder.getStatus() != null) {
                bNotHome.setDisable(true);
                bDelivered.setDisable(true);
            } else {
                bNotHome.setDisable(false);
                bDelivered.setDisable(false);
            }
        });

        tvDeliveries.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                doubleClickedOrder = tvDeliveries.getSelectionModel().getSelectedItem();
                try {
                    showWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void onActionButtonDelivered() {
        driverDao.updateOrderStatus("Bezorgd", selectedOrder);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
        updateDeliveries();
    }

    @FXML
    public void onActionButtonNotHome() {
        driverDao.updateOrderStatus("Niet thuis", selectedOrder);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
        updateDeliveries();
    }
}
