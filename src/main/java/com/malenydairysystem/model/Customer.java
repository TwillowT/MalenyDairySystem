package com.malenydairysystem.model;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Customer Object class to manage Customer records in the program, extending the User clsas.
 */
public class Customer extends User
{

    // Variable Declarations for the Customer Class
    private int customerID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String deliveryAddress;

    // Default constructor
    public Customer()
    {
    }

    // Parameterised constructor
    public Customer(int customerID, String fullName, String phoneNumber, String email, String deliveryAddress)
    {
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
    }

    // Extended constructor including username and password from the User class
    public Customer(int customerID, String fullName, String phoneNumber, String email, String deliveryAddress, String username, String password)
    {
        super(username, password);
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
    }

    // Getters and Setters
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

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    // toString Method
    @Override
    public String toString()
    {
        return "Customer{" + "customerID=" + customerID + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", deliveryAddress=" + deliveryAddress + '}';
    }
}
