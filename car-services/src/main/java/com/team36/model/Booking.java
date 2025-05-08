package com.team36.model;

public class Booking {
    private int id;
    private String userName;
    private String carMakeModel;
    private String serviceDate;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getCarMakeModel() { return carMakeModel; }
    public void setCarMakeModel(String carMakeModel) { this.carMakeModel = carMakeModel; }
    public String getServiceDate() { return serviceDate; }
    public void setServiceDate(String serviceDate) { this.serviceDate = serviceDate; }
}