/*
    Students: Tina Losin (10569238)
    Description: Represents product details needed for inventory management and order processing.
 */
package com.malenydairysystem.model;

import java.io.Serializable;

public class Product implements Serializable {
    int productID;
    String productName;
    String unit;
    int quantity;
    double price;
    String ingredients;

    public Product() {
    }

    public Product(int productID, String productName, String unit, int quantity, double price, String ingredients) {
        this.productID = productID;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.ingredients = ingredients;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", unit=" + unit + ", quantity=" + quantity + ", price=" + price + ", ingredients=" + ingredients + '}';
    }
    
    
            
}
