package com.banger.bangerapi.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String age;
    private Date pickUpDate;
    private String pickUpTime;
    private Date returnDate;
    private String returnTime;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle", nullable = false)
    private Vehicle vehicle;
    private String timePeriod;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<BookingEquipment> bookingEquipments;


    private String Total;
    private String status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver", nullable = false)
    private Driver driver;

//    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Driver driver;

    public Booking() {

    }


}

