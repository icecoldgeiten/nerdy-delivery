package com.dao;

import com.entity.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
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
}
