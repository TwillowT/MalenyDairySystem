package com.malenydairysystem.model;

import java.io.Serializable;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Product Object class to manage Product records in the Program.
 */
public class Product implements Serializable
{

    // Variable Declarations for the Product Class
    private int productID;
    private String productName;
    private String unit;
    private int quantity;
    private double price;
    private String ingredients;

    // Default constructor
    public Product()
    {
    }

    // Parameterised constructor
    public Product(int productID, String productName, String unit, int quantity, double price, String ingredients)
    {
        this.productID = productID;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.ingredients = ingredients;
    }

    // Getters and Setters
    public int getProductID()
    {
        return productID;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
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

    public String getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }

    // toString Method
    @Override
    public String toString()
    {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", unit=" + unit + ", quantity=" + quantity + ", price=" + price + ", ingredients=" + ingredients + '}';
    }
}
