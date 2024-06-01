package com.malenydairysystem.model;

import java.io.Serializable;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Customer Object class to manage Customer records in the Program.
 */
public class Customer extends User implements Serializable
{

    private int customerID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String deliveryAddress;

    public Customer()
    {
    }

    public Customer(int customerID, String fullName, String phoneNumber, String email, String deliveryAddress)
    {
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
    }

    public Customer(int customerID, String fullName, String phoneNumber, String email, String deliveryAddress, String username, String password)
    {
        super(username, password);
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress)
    {
        this.deliveryAddress = deliveryAddress;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Customer{" + "customerID=" + customerID + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", deliveryAddress=" + deliveryAddress + '}';
    }
}
