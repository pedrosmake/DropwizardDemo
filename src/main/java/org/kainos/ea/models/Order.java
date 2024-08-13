package org.kainos.ea.models;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private Timestamp orderDate;
    private Customer customer;

    public Order(int orderId, Customer customer, Timestamp orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = Timestamp.valueOf(orderDate);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
