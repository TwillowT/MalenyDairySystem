/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malenydairysystem.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author ashle
 */
public class OrderLine implements Serializable {
    int orderID;
    int productID;
    int quantity;
    double price;
    double total;
    
    // Variables to help help with displaying
    String productName;
    double gst;
    double totalGST;

    public OrderLine() {
    }

    public OrderLine(int orderID, int productID, int quantity, double price) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        calculateTotal();
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        calculateTotal();
    }
    
    public double getTotal() {
        return total;
    }

    public double getGst() {
        return gst;
    }

    public double getTotalGST() {
        return totalGST;
    }
    
    public void calculateTotal() {
        DecimalFormat df = new DecimalFormat("0.00");
        total = Double.parseDouble(df.format(price * quantity));
        gst = Double.parseDouble(df.format(price * quantity * 0.1));
        totalGST = Double.parseDouble(df.format(total + gst));
        
    }

    @Override
    public String toString() {
        return "OrderLine{" + "orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity + ", price=" + price + ", total=" + total + '}';
    }
    
    
}
