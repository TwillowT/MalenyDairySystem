package com.malenydairysystem.model;

import java.io.Serializable;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Admin Object class to manage Admin records in the Program.
 */
public class Admin extends User implements Serializable
{

    private String staffID;
    private String fullName;
    private String phone;
    private String email;

    public Admin()
    {
    }

    public Admin(String staffID, String fullName, String phone, String email)
    {
        this.staffID = staffID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    public Admin(String staffID, String fullName, String phone, String email, String username, String password)
    {
        super(username, password);
        this.staffID = staffID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    public String getStaffID()
    {
        return staffID;
    }

    public void setStaffID(String staffID)
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
        return "Admin{" + "staffID=" + staffID + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email + '}';
    }
}
