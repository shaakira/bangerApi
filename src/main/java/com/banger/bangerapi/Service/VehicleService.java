package com.banger.bangerapi.Service;

import com.banger.bangerapi.Exception.CustomException;
import com.banger.bangerapi.Models.Booking;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Models.Vehicle;
import com.banger.bangerapi.Repository.BookingRepository;
import com.banger.bangerapi.Repository.UserRepository;
import com.banger.bangerapi.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;


    @Value("${C:\\Users\\SHAAKIRA\\Desktop\\EIRLS\\Banger\\bangerapi\\src\\main\\webapp\\resources\\Image}")
    public String uploadDir;


    public ResponseEntity<String> addVehicle(MultipartFile files,Vehicle vehicle) {

        upload(files);
        vehicle.setImage(files.getOriginalFilename());
        vehicleRepository.save(vehicle);
        return new ResponseEntity<>("Vehicle Added Successfully", HttpStatus.OK);
    }

    public void upload(MultipartFile file) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {

        }

    }


    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    public ResponseEntity<String> deleteVehicle(int id) {
        Vehicle vehicle=vehicleRepository.findById(id).get();
        if(vehicle!=null){
            List<Booking> booking=bookingRepository.findByVehicle(vehicle);
            for (Booking b:booking) {
                if(b.getStatus().equals("confirmed")||b.getStatus().equals("collected")){
                    throw  new CustomException("Cannot delete Vehicle it has been booked cancel the booking to delete the Vehicle",HttpStatus.BAD_REQUEST);
                }
            }
            vehicleRepository.deleteById(id);
        }
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
      }

    public ResponseEntity<String> updatePrice(Vehicle vehicle, int id) {
        Vehicle existingVehicle = vehicleRepository.findById(id).orElse(null);
        existingVehicle.setPrice(vehicle.getPrice());
        vehicleRepository.save(existingVehicle);
        return new ResponseEntity<>("price Updated Successfully", HttpStatus.OK);
    }
}
