/*
    Students: Tina Losin (10569238)
    Description: Represents order details, including product selections, quantities, pricing, and delivery information necessary for invoice 
                 generation and order management.
 */
package com.malenydairysystem.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Order implements Serializable
{   
    int orderID;
    int customerID;
    List <OrderLine> lineItem;
    double totalPrice;
    Date orderDate;

    public Order() {
    }

    public Order(int orderID, int customerID, List<OrderLine> lineItem, double totalPrice, Date orderDate) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.lineItem = lineItem;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List<OrderLine> getLineItem() {
        return lineItem;
    }

    public void setLineItem(List<OrderLine> lineItem) {
        this.lineItem = lineItem;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", customerID=" + customerID + ", lineItem=" + lineItem + ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + '}';
    }
    
    


    
    
    
    
}
