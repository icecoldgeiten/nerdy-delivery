package com.dao;

import com.entity.Driver;
import com.entity.Order;
import com.entity.Route;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;

public class RouteDao {
    private final EntityManager em;

    public RouteDao() {
        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        em = session.createEntityManager();
    }

    public List<Route> getAllRoutes() {
        try {
            em.getTransaction().begin();
            List<Route> routes = em.createQuery("from Route", Route.class).getResultList();
            em.close();
            return routes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateRoute(Driver driver, ObservableList<Order> orders) {
        em.getTransaction().begin();
        try {
            Route r = new Route();
            r.setDriver(driver);
            r.setDuration(25);
            for (Order d : orders) {
                d.setRoute(r);
            }
            r.setOrders(new HashSet<>(orders));
            em.merge(r);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        em.close();
    }
}
