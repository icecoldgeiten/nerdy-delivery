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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageDriverController implements Initializable {
    DriverDao driverDao;
    CalculateRoute calculateRoute;
    Route selectedRoute;
    Customer selectedCustomer;

    @FXML TableView<Customer> tvDeliveries;
    @FXML TableColumn<Customer, Integer> tcCustomerID;
    @FXML TableColumn<Customer, String> tcCustomerAdress, tcPostalCode;
    @FXML TableColumn<Customer, String> tcStatus;
    @FXML Label lDuration,lETA;
    @FXML ComboBox<Route> cbRoutes;
    @FXML ListView<DirectionsRoute> lvDirectionsRoute;
    @FXML Button bDelivered, bNotHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.driverDao = new DriverDao();
        loadComboBoxRoute();
        calculateRoute = new CalculateRoute();
        updateDeliveries();

        //Buttons disabled because route and customer is not yet selected
        bDelivered.setDisable(true);
        bNotHome.setDisable(true);
    }


    public void updateDeliveries(){
        tvDeliveries.getItems().clear();
        loadDeliveries();
    }

    public void loadDeliveries() {
        try {
            ObservableList<Customer> customerDriver = FXCollections.observableArrayList();
            List<Order> ordersSelectedRoute = new ArrayList<>(selectedRoute.getOrders());
            for (Order o : ordersSelectedRoute) {
                customerDriver.add(o.getCustomer());
            }
            //checken of er iets inzit en gebeurd?
            for(Customer c : customerDriver){
                System.out.println(c.toString());
            }

            tcCustomerID.setCellValueFactory(new PropertyValueFactory<>("id"));
            tcCustomerAdress.setCellValueFactory(new PropertyValueFactory<>("addres"));
            tcPostalCode.setCellValueFactory(new PropertyValueFactory<>("postal"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("deliverystatus"));
            tvDeliveries.setItems(customerDriver);
        }catch (NullPointerException ex){
            tvDeliveries.setPlaceholder(new Label("Selecteer route om bezorgingen zichtbaar te maken."));
        }
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
        try {
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
        }catch (RuntimeException ex){
            lvDirectionsRoute.setPlaceholder(new Label("WORK IN PROGRESS"));
        }
        //sla geselecteerde route op in selectedRoute
        selectedRoute = cbRoutes.getValue();
        updateDeliveries();
    }

    // When mouse clicked on list show details of driver!
    @FXML
    public void OnMouseClickedCustomer() {
        //Detecting mouse clicked
        tvDeliveries.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buttons are enabled because route and customer are selected.
                bDelivered.setDisable(false);
                bNotHome.setDisable(false);
                //Check wich list index is selected then set txtContent value for that index
                selectedCustomer = (tvDeliveries.getSelectionModel().getSelectedItem());
                System.out.println(selectedCustomer.getId());
            }
        });
    }




    public void onActionButtonDelivered(){
        driverDao.updateOrderStatus(selectedRoute,"Bezorgd",selectedCustomer);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);

    }

    public void onActionButtonNotHome(){
        driverDao.updateOrderStatus(selectedRoute,"NietThuis", selectedCustomer);
        bNotHome.setDisable(true);
        bDelivered.setDisable(true);
    }


}
