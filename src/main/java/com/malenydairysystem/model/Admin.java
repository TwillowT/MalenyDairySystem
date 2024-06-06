package com.malenydairysystem.model;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Admin Object class to manage Admin records in the Program.
 */
public class Admin extends User
{

    // Variable Declarations for the Admin Class
    private int staffID;
    private String fullName;
    private String phone;
    private String email;

    // Default constructor
    public Admin()
    {
    }

    // Parameterised constructor
    public Admin(int staffID, String fullName, String phone, String email)
    {
        this.staffID = staffID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    // Parameterised constructor
    public Admin(int staffID, String fullName, String phone, String email, String username, String password)
    {
        super(username, password);
        this.staffID = staffID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getStaffID()
    {
        return staffID;
    }

    public void setStaffID(int staffID)
    {
        this.staffID = staffID;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
        return "Admin{" + "staffID=" + staffID + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email + '}';
    }
}
