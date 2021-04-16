package com.route;

import java.util.ArrayList;

public class Order {
    private String deliveryInstructions;
    private String comments;
    private int delivered;
    private int notHome;
    private Customer customer;
    private ArrayList<Orderline> orderlines;
}
