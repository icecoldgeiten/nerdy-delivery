package com.google;

import com.entity.Order;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.util.Set;

public class CalculateRoute {
    private final Dotenv env;

    public CalculateRoute() {
        env = Dotenv.configure().load();
    }

    public DirectionsRoute RouteMaker(Set<Order> orders) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(env.get("SECRET_GOOGLE_API_KEY")).maxRetries(10).queryRateLimit(10).build();
        DirectionsApiRequest directionsApiRequest =
                DirectionsApi.newRequest(context)
                        .origin(env.get("GOOGLE_DIRECTION_DEPOT")).destination(env.get("GOOGLE_DIRECTION_DEPOT"))
                        .optimizeWaypoints(true).waypoints(ConvertRoute(orders));
        try {
            DirectionsResult directionsResult = directionsApiRequest.await();
            System.out.println(directionsResult.routes[0]);
            return directionsResult.routes[0];

        } catch (ApiException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String[] ConvertRoute(Set<Order> orders) {
        String[] addresses = new String[orders.size()];
        int index = 0;

        for (Order o : orders) {
            String a = (o.getCustomer().Address());
            addresses[index] = a;
            index = index + 1;
        }

        return addresses;
    }
}
