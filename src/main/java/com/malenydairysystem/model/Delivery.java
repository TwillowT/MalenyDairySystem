package com.malenydairysystem.model;

import java.io.Serializable;

/*
    Students:       Joshua White (12196075), Joshua Gibson (S0263435), Ashley Hansen (S0213276), Tina Losin (10569238)
    Description:    The Delivery Object class to manage Delivery records in the Program.
 */
public class Delivery implements Serializable
{

    private int deliveryID;
    private int postCode;
    private String deliveryDay;
    private double deliveryCost;

    public Delivery()
    {
    }

    public Delivery(int deliveryID, int postCode, String deliveryDay, double deliveryCost)
    {
        this.deliveryID = deliveryID;
        this.postCode = postCode;
        this.deliveryDay = deliveryDay;
        this.deliveryCost = deliveryCost;
    }

    public int getDeliveryID()
    {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID)
    {
        this.deliveryID = deliveryID;
    }

    public int getPostCode()
    {
        return postCode;
    }

    public void setPostCode(int postCode)
    {
        this.postCode = postCode;
    }

    public String getDeliveryDay()
    {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay)
    {
        this.deliveryDay = deliveryDay;
    }

    public double getDeliveryCost()
    {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost)
    {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public String toString()
    {
        return "Delivery{" + "deliveryID=" + deliveryID + ", postCode=" + postCode + ", deliveryDay=" + deliveryDay + ", deliveryCost=" + deliveryCost + '}';
    }
}
