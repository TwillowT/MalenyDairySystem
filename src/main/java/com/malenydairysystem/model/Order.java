package com.malenydairysystem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Order Object class to manage Order records in the Program.
 */
public class Order implements Serializable
{

    // Variable Declarations for the Order Class
    private int orderID;
    private int customerID;
    private List<OrderLine> lineItem;
    private double totalPrice;
    private Date orderDate;

    // Default constructor
    public Order()
    {
    }

    // Parameterised constructor
    public Order(int orderID, int customerID, double totalPrice, Date orderDate)
    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // Parameterised constructor 
    public Order(int orderID, int customerID, List<OrderLine> lineItem, double totalPrice, Date orderDate)
    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.lineItem = lineItem;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getOrderID()
    {
        return orderID;
    }

    public void setOrderID(int orderID)
    {
        this.orderID = orderID;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    public List<OrderLine> getLineItem()
    {
        return lineItem;
    }

    public void setLineItem(List<OrderLine> lineItem)
    {
        this.lineItem = lineItem;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    // toString Method
    @Override
    public String toString()
    {
        return "Order{" + "orderID=" + orderID + ", customerID=" + customerID + ", lineItem=" + lineItem + ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + '}';
    }
}
