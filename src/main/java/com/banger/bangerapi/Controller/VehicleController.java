package com.banger.bangerapi.Controller;


import com.banger.bangerapi.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api/v1/vehicle")
@RestController
@CrossOrigin
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;




    @GetMapping("/getVehicles")
    private ResponseEntity<?> getAllVehicles() throws Exception{
        return ResponseEntity.ok(vehicleService.findAllVehicles());
    }
}
