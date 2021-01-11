package com.banger.bangerapi.Service;

import com.banger.bangerapi.Exception.CustomException;
import com.banger.bangerapi.Exception.RunTimeException;
import com.banger.bangerapi.Models.*;
import com.banger.bangerapi.Models.DTO.BookingDTO;
import com.banger.bangerapi.Repository.BookingRepository;
import com.banger.bangerapi.Repository.UserRepository;
import com.banger.bangerapi.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BookingService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    DriverService driverService;

    @Autowired
    UserService userService;

    @Autowired
    CSVService csvService;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    BookingEquipmnetService bookingEquipmnetService;

    public ResponseEntity<?> addBooking(BookingDTO booking) {
        User user= userRepository.findByUserName(booking.getUserName());
        Optional <Vehicle> vehicleop=vehicleRepository.findById(booking.getVehicleId());
        Vehicle vehicle= vehicleop.get();
//        List <BookingEquipment> specialEquipment=new ArrayList<>();
//        specialEquipment=equipmentService.getEquipment(booking.);


        if(!user.getStatus().equals("active")){
            throw  new CustomException("User Black Listed",HttpStatus.BAD_REQUEST);
        }
        Boolean flag=true;
        try {
            System.out.println(booking.getPickUpDate()+"    "+booking.getReturnDate());
            Date pickupdate=new SimpleDateFormat("yyyy-mm-dd").parse(booking.getPickUpDate().toString());
            Date returndte=new SimpleDateFormat("yyyy-mm-dd").parse(booking.getReturnDate().toString());

            List<Booking> bookinglist =bookingRepository.findByVehicleAndPickUpDateLessThanEqualAndReturnDateGreaterThanEqual(vehicle,pickupdate,returndte);
            if(bookinglist.size()>0){
                flag=false;
                throw  new CustomException("Vehicle is not Available for the selected Date and Time",HttpStatus.BAD_REQUEST);
            }
            Booking bookings = new Booking();
            if(flag){
                Driver driver= driverService.addDriver(booking.getDriver());
                bookings.setAge(booking.getAge());
                bookings.setPickUpTime(booking.getPickUpTime());
                bookings.setReturnTime(booking.getReturnTime());
                bookings.setVehicle(vehicle);
                bookings.setTimePeriod(booking.getTimePeriod());
                bookings.setStatus("Pending");
                bookings.setDriver(driver);
                bookings.setPickUpDate(pickupdate);
                bookings.setReturnDate(returndte);
                bookings.setUser(user);
                bookings.setTotal(booking.getTotal());
                Booking booked=bookingRepository.save(bookings);
                int total=  bookingEquipmnetService.addEquipment(booking.getEquipment(),booked);
                return new ResponseEntity<>("Booking Successful", HttpStatus.OK);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  ResponseEntity.ok("asd");
    }

    public ResponseEntity<?> getAllPendingBooking(String username){
        User user=userService.getUser(username);
        List<Booking> bookings=bookingRepository.findByUser(user);
//        List<Booking> booking=bookingRepository.findBookingByStatus("Pending");
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    public ResponseEntity<?> validateDriver(Driver driver,String username){
        User user=userService.getUser(username);
        if(csvService.checkLicense(driver.getLicenseNo())){
            if(insuranceService.validateNIC(driver.getNic())){

                return ResponseEntity.ok(true);
            }
            else {
                if(user.getLicenseImage().isEmpty()){
                    throw new CustomException("Upload License Image",HttpStatus.BAD_REQUEST);
                }
                else if(user.getUtilityImage().isEmpty()){
                    throw new CustomException("Upload Utility Image",HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok(true);
            }
        }
        else{
            throw new CustomException("License Number Entered cannot be accepted contact with the administration",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> validateBookingDate(BookingDTO bookingDTO){

        User user= userRepository.findByUserName(bookingDTO.getUserName());
        Optional <Vehicle> vehicleop=vehicleRepository.findById(bookingDTO.getVehicleId());
        Vehicle vehicle= vehicleop.get();
        if(!user.getStatus().equals("active")){
            throw  new RunTimeException("User Black Listed",HttpStatus.BAD_REQUEST);
        }
        Boolean flag=true;
        try {
            System.out.println(bookingDTO.getPickUpDate() + "    " + bookingDTO.getReturnDate());
            Date pickupdate = new SimpleDateFormat("yyyy-mm-dd").parse(bookingDTO.getPickUpDate().toString());
            Date returndte = new SimpleDateFormat("yyyy-mm-dd").parse(bookingDTO.getReturnDate().toString());

            List<Booking> bookinglist = bookingRepository.findByVehicleAndPickUpDateLessThanEqualAndReturnDateGreaterThanEqual(vehicle, pickupdate, returndte);
            if (bookinglist.size() > 0) {
                flag = false;
                throw new CustomException("Vehicle is not Available for the selected Date and Time", HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(flag);
        }catch (Exception e){
            throw new  CustomException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }


}
