package com.banger.bangerapi.Models;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Data
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

    public BookingEquipment() {
    }
}
