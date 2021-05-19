package com.dao;

import com.entity.*;

import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RouteDao {
    EntityManagerFactory session;

    public RouteDao() {
        session = Persistence.createEntityManagerFactory("ice-unit");
    }

    public List<Route> getAllRoutes() {
        try {
            EntityManager em = session.createEntityManager();
            em.getTransaction().begin();
            List<Route> routes = em.createQuery("from Route", Route.class).getResultList();
            em.close();
            return routes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateRoute(Driver driver, ObservableList<Order> orders, LocalDate date, Timeslot timeslot) {
        EntityManager em = session.createEntityManager();
        em.getTransaction().begin();
        try {
            Route r = new Route();
            RouteStatus status = em.createQuery("from RouteStatus R where R.statusCode = :status", RouteStatus.class).setParameter("status", "OPENFORDELIVERY").getSingleResult();

            r.setDriver(driver);
            for (Order d : orders) {
                d.setRoute(r);
            }
            r.setOrders(new HashSet<>(orders));
            r.setDuration(25);
            r.setRouteStatus(status);
            r.setTimeslot(timeslot);
            r.setDate(date);
            em.merge(r);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        em.close();
    }

    public void updateRoute(Route route, ObservableList<Order> orders) {
        EntityManager em = session.createEntityManager();
        em.getTransaction().begin();
        try {
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
            em.getTransaction().rollback();
        }
        em.close();
    }

    public void updateDriver(Route route, Driver driver, LocalDate date, Timeslot time) {
        EntityManager em = session.createEntityManager();
        em.getTransaction().begin();
        try {
            route.setDriver(driver);
            route.setDate(date);
            route.setTimeslot(time);
            em.merge(route);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        em.close();
    }

    public void removeOrder(Route route, Order order) {
        EntityManager em = session.createEntityManager();
        em.getTransaction().begin();
        try {
            Set<Order> old = route.getOrders();
            for (Order d : old) {
                if (d.equals(order)) {
                    d.setRoute(null);
                }
            }
            route.setOrders(new HashSet<>(old));
            em.merge(route);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        em.close();
    }

    public List<Route> getDriveableRoutes(Driver driver, LocalDate date) {
        EntityManager em = session.createEntityManager();
        em.getTransaction().begin();
        try {
            List<Route> routes = em.createQuery("from Route where date = :date AND driver = :driver ", Route.class).setParameter("date", date).setParameter("driver", driver).getResultList();
            em.getTransaction().commit();
            return routes;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        em.close();
        return null;
    }

    public void setRouteStatus(Route route, String statusCode) {
        EntityManager em = session.createEntityManager();
        em.getTransaction().begin();
        try {
            RouteStatus status = em.createQuery("from RouteStatus where statusCode = :status", RouteStatus.class).setParameter("status", statusCode).getSingleResult();
            route.setRouteStatus(status);
            em.merge(route);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        em.close();
    }
}
