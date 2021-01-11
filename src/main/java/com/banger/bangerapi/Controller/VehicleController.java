package com.banger.bangerapi.Controller;


import com.banger.bangerapi.Models.Vehicle;
import com.banger.bangerapi.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/deleteVehicle/{id}")
    public  ResponseEntity<?> deleteVehicle(@PathVariable int id){
        return  vehicleService.deleteVehicle(id);
    }
    @PostMapping("/updatePrice/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") int id,@RequestBody Vehicle vehicle) throws Exception {
        return vehicleService.updatePrice(vehicle,id);
    }
}
