package org.openjfx;

import com.dao.DriverDao;
import com.entity.Customer;
import com.entity.Order;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.model.DirectionsRoute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    public ListView<DirectionsRoute> lvDirectionsRoute;
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

    //get the orders of the selectedroute in the combobox and load all the orders in the tableview.
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

    //set property values to columns and change cellvalues with cellfactory.
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
        //convert Set Routes to List routes
        List<Route> routelist = new ArrayList<>(DriverDao.getLogedinDriver().getRoutes());

        //add list to observableList.
        ObservableList<Route> routes = FXCollections.observableList(routelist);

        //add observablelist to combobox.
        cbRoutes.setItems(routes);

        //Get values out of combobox and display them correctly:
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

    //Load in screen the selected route in combobox.
    public void loadRouteAfterChangeComboBoxRoute() {
        try {
            //Change labels when clicking a route.
            lDuration.setText(cbRoutes.getValue().getDuration() + " min");
            lETA.setText(Integer.toString(calculateRoute.RouteMaker(cbRoutes.getValue().getOrders()).legs.length));

            //Get the directions of the selected route.
            List<DirectionsRoute> list = Arrays.asList(calculateRoute.RouteMaker(cbRoutes.getValue().getOrders()));
            ObservableList<DirectionsRoute> directionsRoutes = FXCollections.observableList(list);
            lvDirectionsRoute.setItems(directionsRoutes);
            lvDirectionsRoute.setCellFactory(new Callback<>() {
                @Override
                public ListCell<DirectionsRoute> call(ListView<DirectionsRoute> p) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(DirectionsRoute t, boolean bln) {
                            super.updateItem(t, bln);
                            if (t != null) {
                                setText(t.toString());
                            }
                        }
                    };
                }
            });
        } catch (RuntimeException ex) {
            lvDirectionsRoute.setPlaceholder(new Label("WORK IN PROGRESS"));
        }
        //sla geselecteerde route op in selectedRoute
        selectedRoute = cbRoutes.getValue();
        updateDeliveries();
    }

    private void showWindow() throws IOException {
        URL url = getClass().getResource("/org.openjfx/orderdetails.fxml");
        System.out.println(url);
        final FXMLLoader loader = new FXMLLoader(url);
        final Parent root = loader.load();
        final Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Orderdetails: " + doubleClickedOrder.getId());
        stage.setScene(scene);
        stage.show();
    }

    // When mouse clicked on list show details of driver!
    @FXML
    public void OnMouseClickedCustomer() {
        //Detecting mouse clicked
        tvDeliveries.setOnMouseClicked(event -> {
            //Check wich list index is selected then set txtContent value for that index
            selectedOrder = tvDeliveries.getSelectionModel().getSelectedItem();
            System.out.println(selectedOrder.getId());
            //when order status is not empty buttons are disabled when order status is empty buttons are enabled.
            if (selectedOrder.getStatus() != null) {
                bNotHome.setDisable(true);
                bDelivered.setDisable(true);
            } else {
                bNotHome.setDisable(false);
                bDelivered.setDisable(false);
            }
        });

        //Detecting mouse clicked
        tvDeliveries.setOnMouseClicked(event -> {
            //Check wich list index is selected then set txtContent value for that index
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

    //when clicking button 'Geleverd' change status and disable buttons so the status can't be changed.
    public void onActionButtonDelivered() {
        driverDao.updateOrderStatus("Bezorgd", selectedOrder);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
        updateDeliveries();
    }

    //when clicking button 'NietThuis' change status and disable buttons so the status can't be changed.
    public void onActionButtonNotHome() {
        driverDao.updateOrderStatus("Niet thuis", selectedOrder);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
        updateDeliveries();
    }
}
