package com.banger.bangerapi.Models;


import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity

public class BookingEquipment {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private String price;
    private int count;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookings", nullable = false)
    private Booking booking;

    public BookingEquipment(String name, String price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BookingEquipment() {
    }
}
