/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.malenydairysystem.model;

/**
 *
 * @author ashle
 */
public class OrderLine {
    int orderID;
    int productID;
    int quantity;
    double price;
    double total;

    public OrderLine() {
    }

    public OrderLine(int orderID, int productID, int quantity, double price, double total) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderLine{" + "orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity + ", price=" + price + ", total=" + total + '}';
    }
    
    
}
