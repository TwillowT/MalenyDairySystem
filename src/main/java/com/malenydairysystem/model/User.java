package com.malenydairysystem.model;

import java.io.Serializable;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The User Object class to manage User records in the Program.
 */
public class User implements Serializable
{

    // Variable Declarations for the User Class
    public String username;
    public String password;

    // Default constructor 
    public User()
    {
    }

    // Parameterised constructor
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
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

    // toString method
    @Override
    public String toString()
    {
        return "User{" + "username=" + username + ", password=" + password + '}';
    }
}
