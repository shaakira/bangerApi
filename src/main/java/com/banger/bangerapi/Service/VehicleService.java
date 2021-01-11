package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Vehicle;
import com.banger.bangerapi.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }
    public ResponseEntity<String> deleteVehicle(int id){
        vehicleRepository.deleteById(id);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }
    public ResponseEntity<String> updatePrice(Vehicle vehicle,int id){
        Vehicle existingVehicle=vehicleRepository.findById(id).orElse(null);
        existingVehicle.setPrice(vehicle.getPrice());
        vehicleRepository.save(existingVehicle);
        return new ResponseEntity<>("price Updated Successfully",HttpStatus.OK);
    }
}
