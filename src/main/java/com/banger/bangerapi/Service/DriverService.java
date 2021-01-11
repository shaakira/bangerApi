package com.banger.bangerapi.Service;


import com.banger.bangerapi.Models.DTO.BookingDTO;
import com.banger.bangerapi.Models.Driver;
import com.banger.bangerapi.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;

    public Driver addDriver(Driver driver) {
        int count=driverRepository.findAll().size();
        driver.setId(count+1);
        driverRepository.save(driver);
        return driver;
    }

}