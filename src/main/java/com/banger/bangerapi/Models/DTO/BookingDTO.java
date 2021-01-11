package com.banger.bangerapi.Models.DTO;


import com.banger.bangerapi.Models.BookingEquipment;
import com.banger.bangerapi.Models.Driver;
import com.banger.bangerapi.Models.Equipment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Data

public class BookingDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String age;
    private String pickUpDate;
    private String pickUpTime;
    private String returnDate;
    private String returnTime;
    private Integer vehicleId;
    private String timePeriod;
    private String userName;
    private Driver driver;
    private List<BookingEquipment> equipment;
    private String total;
    private String status;

    public BookingDTO() {
    }

    public BookingDTO(String age, String pickUpDate, String pickUpTime, String returnDate, String returnTime, Integer vehicleId, String timePeriod, String userName) {
        this.age = age;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.vehicleId = vehicleId;
        this.timePeriod = timePeriod;
        this.userName = userName;
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

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<BookingEquipment> getEquipmentId() {
        return equipment;
    }

    public void setEquipmentId(List<BookingEquipment> equipmentId) {
        this.equipment = equipmentId;
    }

}
