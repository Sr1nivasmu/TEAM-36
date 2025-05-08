package com.team36.model;

public class CarResale {
    private int id;
    private String carName;
    private String currentOwner;
    private String carType;
    private int kilometersDriven;
    private int serviceRecord;
    private int carAge;
    private int accidentsMajor;
    private int accidentsMinor;
    private boolean insurance;
    private int numberOfOwners;
    private String description;

    public CarResale() {
    }

    public CarResale(int id, String carName, String currentOwner, String carType, int kilometersDriven,
            int serviceRecord, int carAge, int accidentsMajor, int accidentsMinor,
            boolean insurance, int numberOfOwners, String description) {
        this.id = id;
        this.carName = carName;
        this.currentOwner = currentOwner;
        this.carType = carType;
        this.kilometersDriven = kilometersDriven;
        this.serviceRecord = serviceRecord;
        this.carAge = carAge;
        this.accidentsMajor = accidentsMajor;
        this.accidentsMinor = accidentsMinor;
        this.insurance = insurance;
        this.numberOfOwners = numberOfOwners;
        this.description = description;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(int kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public int getServiceRecord() {
        return serviceRecord;
    }

    public void setServiceRecord(int serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    public int getCarAge() {
        return carAge;
    }

    public void setCarAge(int carAge) {
        this.carAge = carAge;
    }

    public int getAccidentsMajor() {
        return accidentsMajor;
    }

    public void setAccidentsMajor(int accidentsMajor) {
        this.accidentsMajor = accidentsMajor;
    }

    public int getAccidentsMinor() {
        return accidentsMinor;
    }

    public void setAccidentsMinor(int accidentsMinor) {
        this.accidentsMinor = accidentsMinor;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public int getNumberOfOwners() {
        return numberOfOwners;
    }

    public void setNumberOfOwners(int numberOfOwners) {
        this.numberOfOwners = numberOfOwners;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
