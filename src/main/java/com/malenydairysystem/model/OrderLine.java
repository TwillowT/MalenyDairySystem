package com.malenydairysystem.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The OrderLine Object class to manage OrderLine records in the Program.
 */
public class OrderLine implements Serializable
{

    // Variable Declarations for the OrderLine Class
    private int orderID;
    private int productID;
    private int quantity;
    private double price;
    private double total;

    // Additional variables for the OrderLine Class
    private String productName;
    private double gst;
    private double totalGST;

    // Default constructor
    public OrderLine()
    {
    }

    // Parameterised constructor
    public OrderLine(int orderID, int productID, int quantity, double price, double total)
    {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        calculateTotal();
    }

    // Getters and setters
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
        calculateTotal();
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
        calculateTotal();
    }

    public double getTotal()
    {
        return total;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public double getGst()
    {
        return gst;
    }

    public double getTotalGST()
    {
        return totalGST;
    }

    // Calculates the total and GST Values
    void calculateTotal()
    {
        // Use Decimal Format to round to Two Decial Places
        DecimalFormat df = new DecimalFormat("0.00");

        // Calculate the Total and GST
        total = Double.parseDouble(df.format(quantity * price));
        gst = Double.parseDouble(df.format(total * 0.1));

        // Calculate the Total with GST
        totalGST = Double.parseDouble(df.format(total + gst));
    }

    // toString Method
    @Override
    public String toString()
    {
        return "Product ID=" + productID + ", Quantity=" + quantity + ", Price=" + price + ", Total=" + total;
    }
}
