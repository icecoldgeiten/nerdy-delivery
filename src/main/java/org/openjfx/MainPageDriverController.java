package org.openjfx;

import com.dao.DriverDao;
import com.entity.Customer;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageDriverController {
    DriverDao driverDao;
    CalculateRoute calculateRoute;
    @FXML TableView<Customer> tvDeliveries;
    @FXML TableColumn<Customer, String> tcAdress, tcPostalcode, tcLocation;
    @FXML TableColumn<Customer, Button> tcDelivered,tcNotHome;
    @FXML Label lDuration,lETA;
    @FXML ComboBox<Route> cbRoutes;
    @FXML ListView<DirectionsRoute> lvDirectionsRoute;



    public MainPageDriverController() {
    }

    public void initialize() {
        this.driverDao = new DriverDao();
        loadComboBoxRoute();
        calculateRoute = new CalculateRoute();
//        this.loadDeliveries();
    }

    public void loadDeliveries() {

    }

    public void loadComboBoxRoute() {
        //convert Set Routes to List routes
        List<Route> routelist = new ArrayList<>(DriverDao.getIngelogdeDriver().getRoutes());
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
    public void loadRouteAfterChangeComboBoxRoute(){
        //Change labels when clicking a route.
        lDuration.setText(Integer.toString(cbRoutes.getValue().getDuration()) + " min");
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
    }

    public void onActionButtonDelivered(){

    }

    public void onActionButtonNotHome(){

    }
}
