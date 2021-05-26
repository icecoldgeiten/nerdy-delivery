package com.dao;

import com.entity.*;

import com.helpers.CEntityManagerFactory;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RouteDao {
    EntityManagerFactory emf;

    public RouteDao() {
        emf = CEntityManagerFactory.getEntityManagerFactory();
    }

    public List<Route> getAllDeliveredRoutes() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Route> routes = em.createQuery("from Route where status = :status", Route.class).setParameter("status", StatusDao.getRouteStatus("DELIVERED")).getResultList();
            em.getTransaction().commit();
            em.close();
            return routes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public List<Route> getDailyRoutes() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Route> routes = em.createQuery("from Route", Route.class).getResultList();
            routes.removeIf(r -> r.getRouteStatus().getStatusCode().equals("DELIVERED") && LocalDate.now().isAfter(r.getDate()));
            em.getTransaction().commit();
            em.close();
            return routes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public void generateRoute(Driver driver, ObservableList<Order> orders, LocalDate date, Timeslot timeslot) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Route r = new Route();
            RouteStatus status = em.createQuery("from RouteStatus R where R.statusCode = :status", RouteStatus.class).setParameter("status", "OPENFORDELIVERY").getSingleResult();

            r.setDriver(driver);
            for (Order d : orders) {
                d.setRoute(r);
                d.setOrderStatus(StatusDao.getOrderStatus("OPENFORDELIVERY"));
            }
            r.setOrders(new HashSet<>(orders));
            r.setDuration(25);
            r.setRouteStatus(status);
            r.setTimeslot(timeslot);
            r.setDate(date);
            em.merge(r);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.getTransaction().commit();
        em.close();
    }

    public void updateDriver(Route route, Driver driver, LocalDate date, Timeslot time) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            route.setDriver(driver);
            route.setDate(date);
            route.setTimeslot(time);
            em.merge(route);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.getTransaction().commit();
        em.close();
    }

    public void updateRoute(Route route, ObservableList<Order> orders) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Set<Order> old = route.getOrders();
            for (Order d : orders) {
                d.setRoute(route);
                old.add(d);
            }
            route.setOrders(new HashSet<>(old));
            em.merge(route);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
    }

    public void removeOrder(Route route, Order order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            route.getOrders().removeIf(value -> value.equals(order));
            order.setRoute(null);
            em.merge(order);
            em.merge(route);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
    }

    public List<Route> getDriveableRoutes(Driver driver, LocalDate date) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Route> routes = em.createQuery("from Route where date = :date AND driver = :driver AND NOT status = :status", Route.class)
                    .setParameter("date", date)
                    .setParameter("driver", driver)
                    .setParameter("status", StatusDao.getRouteStatus("DELIVERED")).getResultList();
            em.getTransaction().commit();
            em.close();
            return routes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public void setRouteStatus(Route route, String statusCode) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            RouteStatus status = em.createQuery("from RouteStatus where statusCode = :status", RouteStatus.class).setParameter("status", statusCode).getSingleResult();
            route.setRouteStatus(status);
            em.merge(route);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.getTransaction().commit();
        em.close();
    }
}
