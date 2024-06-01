package com.malenydairysystem.model;

import java.io.Serializable;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The OrderLine Object class to manage OrderLine records in the Program.
 */
public class OrderLine implements Serializable
{

    private int orderID;
    private int productID;
    private int quantity;
    private double price;
    private double total;

    public OrderLine()
    {
    }

    public OrderLine(int orderID, int productID, int quantity, double price, double total)
    {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public void setOrderID(int orderID)
    {
        this.orderID = orderID;
    }

    public int getProductID()
    {
        return productID;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    @Override
    public String toString()
    {
        return "OrderLine{" + "orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity + ", price=" + price + ", total=" + total + '}';
    }
}
