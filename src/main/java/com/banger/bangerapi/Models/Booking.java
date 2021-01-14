package com.banger.bangerapi.Models;



import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


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
            cascade=
                    {CascadeType.DETACH,  CascadeType.MERGE,  CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<BookingEquipment> bookingEquipments;
    private String Total;
    private String status;
    @Nullable
    private String note;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver", nullable = false)
    private Driver driver;

//    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Driver driver;

    public Booking() {

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public User getUser() {
        return user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }



    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }





    public Set<BookingEquipment> getBookingEquipments() {
        return bookingEquipments;
    }



    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

