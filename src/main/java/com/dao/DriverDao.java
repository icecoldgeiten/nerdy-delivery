
package com.dao;

import com.entity.Customer;
import com.entity.Driver;
import com.entity.Order;
import com.entity.Route;
import com.helpers.AES256;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DriverDao {
    EntityManager em;
    Driver driver;
    Order order;
    private static Driver ingelogdeDriver;

    //Constructor
    public DriverDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        this.em = emf.createEntityManager();
    }

    //get observable list with customers of drivers order.
//    public List<Order> getDriverOrders(){
////
////        // search thru the routes with the same driver and put them in a list.
////        List<Route> driverRoutes = new ArrayList<>();
////        List<Route> routes =
////        if(route.getDriver().equals(ingelogdeDriver)){
////            driverRoutes.add(route);
////        }
////        // search thru the routes and find the orders that belong to the route and put them in a list.
////        List<Order> driverOrders = new ArrayList<>();
////        for(Route r : driverRoutes){
////
////        }
////        // search thru the orders and find the orders
////
////
////
////        return driverOrders;
//    }

    //Adds a new driver
    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un, String pw) {
        try {
            this.em.getTransaction().begin();
            this.driver = new Driver();
            this.driver.setName(name);
            this.driver.setInserts(sn);
            this.driver.setInserts(ins);
            this.driver.setSirname(sn);
            this.driver.setPhonenumber(phone);
            this.driver.setBirthdate(bd);
            this.driver.setUsername(un);
            this.driver.setPassword(pw);
            this.em.persist(this.driver);
            this.em.getTransaction().commit();
            System.out.println("Driver added");
        } catch (Exception var9) {
            System.out.println();
        }
    }

    //Validates password is username and password are the same in database.
    public boolean validate(String username, String password) {
        try {
            em.getTransaction().begin();
            Driver d = em.createQuery("from Driver D where D.username = :username", Driver.class).setParameter("username", username).getSingleResult();
            if (d != null && d.getPassword().equals(AES256.encrypt(password))) {
                ingelogdeDriver = d;
                return true;
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception var4) {
            System.out.println("Validate error: " + var4);
            var4.printStackTrace();
        }

        return false;
    }
    //Generates random password
    public String randomPassword(int length) {
        String passwordSet = "ABCDEFGHIJKLMOPQRSRUVWXYZ0123456789!@#$%";
        char[] password = new char[length];
        for(int i = 0; i < length; ++i) {
            int rand = (int)(Math.random() * (double)passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        return new String(password);
    }

    //Change notHome
    public void changeNothome(int orderid, int i){
        em.getTransaction().begin();
        order = em.find(Order.class, orderid);
        order.setNotHome(i);
        em.merge(order);
        em.getTransaction().commit();
    }
    //Change delivered
    public void changeDelivered(int orderid, int i){
        em.getTransaction().begin();
        order = em.find(Order.class, orderid);
        order.setDelivered(i);
        em.merge(order);
        em.getTransaction().commit();
    }

    //Update order status
    public void updateOrderStatus(Route route, String status,Customer customer){
        em.getTransaction().begin();
        List<Order> ordersroute = em.createQuery("from Order O where O.route = :route", Order.class).setParameter("route", route).getResultList();
        List<Order> orderscustomer = em.createQuery("from Order O where O.customer = :customer", Order.class).setParameter("customer", customer).getResultList();
        //Change status of all the orders of the customer
        for(Order or : ordersroute){
            for(Order oc : orderscustomer){
                if(or.getCustomer().equals(oc.getCustomer())){
                    or.setStatus(status);
                    em.merge(or);
                }
            }
        }
        //Change deliverystatus of customer
//        Customer c = em.createQuery("from Customer c where c.id = :id", Customer.class).setParameter("id", customer.getId()).getSingleResult();
//        c.setDeliverystatus(status);
//        em.merge(c);
//        System.out.println("Status aangepast bij klant: " + c.getId() + c.getDeliverystatus());

        em.getTransaction().commit();

    }




    //GETTER
    public static Driver getIngelogdeDriver() {
        return ingelogdeDriver;
    }

}
