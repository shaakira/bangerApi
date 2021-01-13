package com.banger.bangerapi.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private double price;
    private String image;
    private int passengerCount;
    private int baggageCount;
    private String transmission;
    private String description;
    private int engine;
    private String AC;
    private int doorCount;
    private String fuelType;
    private String fuelPolicy;
    private String type;
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Booking> booking;

    public Vehicle() {
    }

    public Vehicle(String name, double price, String image, int passengerCount, int baggageCount, String transmission, String description, int engine, String AC, int doorCount, String fuelType, String fuelPolicy, String type) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.passengerCount = passengerCount;
        this.baggageCount = baggageCount;
        this.transmission = transmission;
        this.description = description;
        this.engine = engine;
        this.AC = AC;
        this.doorCount = doorCount;
        this.fuelType = fuelType;
        this.fuelPolicy = fuelPolicy;
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public int getBaggageCount() {
        return baggageCount;
    }

    public void setBaggageCount(int baggageCount) {
        this.baggageCount = baggageCount;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public int getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(int doorCount) {
        this.doorCount = doorCount;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelPolicy() {
        return fuelPolicy;
    }

    public void setFuelPolicy(String fuelPolicy) {
        this.fuelPolicy = fuelPolicy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
