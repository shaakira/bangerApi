package com.banger.bangerapi.Controller;


import com.banger.bangerapi.Models.Vehicle;
import com.banger.bangerapi.Service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequestMapping("api/v1/vehicle")
@RestController
@CrossOrigin("*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;


    @PostMapping(value = "/addVehicle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile file, String name, double price, int passengerCount, int baggageCount, String transmission, String description, int engine, String AC, int doorCount, String fuelType, String fuelPolicy, String type) {
        Vehicle newVehicle = new Vehicle();
        newVehicle.setName(name);
        newVehicle.setPrice(price);
        newVehicle.setPassengerCount(passengerCount);
        newVehicle.setBaggageCount(baggageCount);
        newVehicle.setDescription(description);
        newVehicle.setAC(AC);
        newVehicle.setTransmission(transmission);
        newVehicle.setDoorCount(doorCount);
        newVehicle.setEngine(engine);
        newVehicle.setFuelPolicy(fuelPolicy);
        newVehicle.setFuelType(fuelType);
        newVehicle.setType(type);
        return vehicleService.addVehicle(file, newVehicle);

    }

    @GetMapping("/getVehicles")
    private ResponseEntity<?> getAllVehicles() throws Exception {
        return ResponseEntity.ok(vehicleService.findAllVehicles());
    }

    @GetMapping("/deleteVehicle/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable int id) {
        return vehicleService.deleteVehicle(id);
    }

    @PostMapping("/updatePrice/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") int id, @RequestBody Vehicle vehicle) throws Exception {
        return vehicleService.updatePrice(vehicle, id);
    }
}
