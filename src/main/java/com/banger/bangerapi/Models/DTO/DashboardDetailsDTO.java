package com.banger.bangerapi.Models.DTO;

public class DashboardDetailsDTO {
    private int newBookingCount;
    private int vehicleCount;
    private int customersCount;
    private int availableVehicleCount;
    private double estimation;
    private int onRentCount;

    public DashboardDetailsDTO() {
    }

    public int getNewBookingCount() {
        return newBookingCount;
    }

    public void setNewBookingCount(int newBookingCount) {
        this.newBookingCount = newBookingCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getCustomersCount() {
        return customersCount;
    }

    public void setCustomersCount(int customersCount) {
        this.customersCount = customersCount;
    }

    public int getAvailableVehicleCount() {
        return availableVehicleCount;
    }

    public void setAvailableVehicleCount(int availableVehicleCount) {
        this.availableVehicleCount = availableVehicleCount;
    }

    public double getEstimation() {
        return estimation;
    }

    public void setEstimation(double estimation) {
        this.estimation = estimation;
    }

    public int getOnRentCount() {
        return onRentCount;
    }

    public void setOnRentCount(int onRentCount) {
        this.onRentCount = onRentCount;
    }
}
