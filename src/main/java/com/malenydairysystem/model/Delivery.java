package com.malenydairysystem.model;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Joshua
 */
public class Delivery implements Serializable
{
    int deliveryID;
    int postCode;
    //Can delivery day and time be consolidated into a single DateTime?
    Date deliveryDay;
    Date deliveryTime;
    double deliveryCost;
    String customerName;
    Order order;

    public Delivery() {
    }

    public Delivery(int deliveryID, int postCode, Date deliveryDay, Date deliveryTime, double deliveryCost, String customerName, Order order) {
        this.deliveryID = deliveryID;
        this.postCode = postCode;
        this.deliveryDay = deliveryDay;
        this.deliveryTime = deliveryTime;
        this.deliveryCost = deliveryCost;
        this.customerName = customerName;
        this.order = order;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public Date getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Date deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Delivery{" + "deliveryID=" + deliveryID + ", postCode=" + postCode + ", deliveryDay=" + deliveryDay + ", deliveryTime=" + deliveryTime + ", deliveryCost=" + deliveryCost + ", customerName=" + customerName + ", order=" + order + '}';
    }
    


    
}