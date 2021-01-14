package com.banger.bangerapi.Service;

import com.banger.bangerapi.Exception.CustomException;
import com.banger.bangerapi.Exception.RunTimeException;
import com.banger.bangerapi.Models.*;
import com.banger.bangerapi.Models.DTO.BookingDTO;
import com.banger.bangerapi.Repository.BookingRepository;
import com.banger.bangerapi.Repository.NotificationRepository;
import com.banger.bangerapi.Repository.UserRepository;
import com.banger.bangerapi.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class BookingService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    EmailService emailService;

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
        User user = userRepository.findByUserName(booking.getUserName());
        Optional<Vehicle> vehicleop = vehicleRepository.findById(booking.getVehicleId());
        Vehicle vehicle = vehicleop.get();
//        List <BookingEquipment> specialEquipment=new ArrayList<>();
//        specialEquipment=equipmentService.getEquipment(booking.);


        if (!user.getStatus().equals("active")) {
            throw new CustomException("User Black Listed", HttpStatus.BAD_REQUEST);
        }
        Boolean flag = true;
        try {
            System.out.println(booking.getPickUpDate() + "    " + booking.getReturnDate());
            Date pickupdate = new SimpleDateFormat("yyyy-mm-dd").parse(booking.getPickUpDate().toString());
            Date returndte = new SimpleDateFormat("yyyy-mm-dd").parse(booking.getReturnDate().toString());

            List<Booking> bookinglist = bookingRepository.findByVehicleAndPickUpDateLessThanEqualAndReturnDateGreaterThanEqual(vehicle, pickupdate, returndte);
            if (bookinglist.size() > 0) {
                for (Booking b : bookinglist
                        ) {
                    if (b.getStatus().equals("confirmed") || b.getStatus().equals("collected")) {
                        flag = false;
                        throw new CustomException("Vehicle is not Available for the selected Date and Time", HttpStatus.BAD_REQUEST);
                    }
                }

            }
            Booking bookings = new Booking();
            if (flag) {
                Driver driver = driverService.addDriver(booking.getDriver());
                bookings.setAge(booking.getAge());
                bookings.setPickUpTime(booking.getPickUpTime());
                bookings.setReturnTime(booking.getReturnTime());
                bookings.setVehicle(vehicle);
                bookings.setTimePeriod(booking.getTimePeriod());
                bookings.setStatus("confirmed");
                bookings.setDriver(driver);
                bookings.setPickUpDate(pickupdate);
                bookings.setReturnDate(returndte);
                bookings.setUser(user);
                bookings.setTotal(booking.getTotal());
                Booking booked = bookingRepository.save(bookings);
                int total = bookingEquipmnetService.addEquipment(booking.getEquipment(), booked);
                return new ResponseEntity<>("Booking Successful", HttpStatus.OK);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("asd");
    }

    public ResponseEntity<?> getAllPendingBooking(String username) {
        User user = userService.getUser(username);
        List<Booking> bookings = bookingRepository.findByUser(user);
//        List<Booking> booking=bookingRepository.findBookingByStatus("Pending");
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    public ResponseEntity<?> validateDriver(Driver driver, String username) {
        User user = userService.getUser(username);
        if (csvService.checkLicense(driver.getLicenseNo())) {
            if (insuranceService.validateNIC(driver.getNic())) {

                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                if (user.getLicenseImage().isEmpty()) {
                    throw new CustomException("Upload License Image", HttpStatus.BAD_REQUEST);
                } else if (user.getUtilityImage().isEmpty()) {
                    throw new CustomException("Upload Utility Image", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>("documents", HttpStatus.OK);

            }
        } else {
            emailService.sendEmail(driver.getLicenseNo());
            throw new CustomException("License Number Entered cannot be accepted contact with the administration", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> validateBookingDate(BookingDTO bookingDTO) {

        User user = userRepository.findByUserName(bookingDTO.getUserName());
        Optional<Vehicle> vehicleop = vehicleRepository.findById(bookingDTO.getVehicleId());
        Vehicle vehicle = vehicleop.get();
        if (!user.getStatus().equals("active")) {
            throw new RunTimeException("User Black Listed", HttpStatus.BAD_REQUEST);
        }
        Boolean flag = true;
        try {
            System.out.println(bookingDTO.getPickUpDate() + "    " + bookingDTO.getReturnDate());
            Date pickupdate = new SimpleDateFormat("yyyy-mm-dd").parse(bookingDTO.getPickUpDate().toString());
            Date returndte = new SimpleDateFormat("yyyy-mm-dd").parse(bookingDTO.getReturnDate().toString());

            List<Booking> bookingList = bookingRepository.findByVehicleAndPickUpDateLessThanEqualAndReturnDateGreaterThanEqual(vehicle, pickupdate, returndte);
            if (bookingList.size() > 0) {
                for (Booking booking : bookingList
                        ) {
                    if (booking.getStatus().equals("confirmed") || booking.getStatus().equals("collected")) {
                        flag = false;
                        throw new CustomException("Vehicle is not Available for the selected Date and Time", HttpStatus.BAD_REQUEST);
                    }
                }
            }
            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date dateObj2 = sdf.parse(bookingDTO.getReturnDate() + " " + bookingDTO.getReturnTime());
            Date dateObj1 = sdf.parse(bookingDTO.getPickUpDate() + " " + bookingDTO.getPickUpDate());
            long diff = dateObj2.getTime() - dateObj1.getTime();

            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
           if(diffDays>14 && diffDays>0){
               flag = false;
               throw new CustomException("Vehicles can be rented for a maximum of 2 weeks", HttpStatus.BAD_REQUEST);
           }
           else if(diffDays==0){
               SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
               Date date1 = format1.parse(bookingDTO.getPickUpTime());
               Date date2 = format1.parse(bookingDTO.getReturnTime());
               long difference = date2.getTime() - date1.getTime();
               int hours=(int) (difference / (60 * 60 * 1000));
               if(hours<5){
                   flag = false;
                   throw new CustomException("Vehicles can be rented for a minimum of 5 hours", HttpStatus.BAD_REQUEST);
               }

           }
            return ResponseEntity.ok(flag);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public List<Booking> getConfirmedBookings(String userName) {
        List<Booking> bookings = bookingRepository.findConfirmedBooking(userName);
        return bookings;
    }

    public List<Booking> getPastBookings(String userName) {
        List<Booking> bookings = bookingRepository.findPastBooking(userName);
        return bookings;
    }

    public ResponseEntity<String> extendBooking(Booking booking, int id, String username) {

        try {
            Booking existingBooking = bookingRepository.findById(id).orElse(null);
            User user = userRepository.findByUserName(username);
            SimpleDateFormat format;
            List<Booking> bookingList = bookingRepository.findBookingTime(existingBooking.getReturnDate(), existingBooking.getVehicle().getId());
            if (bookingList.size() > 0) {
                for (Booking b : bookingList) {
                    if (b.getId() != existingBooking.getId()) {
                        format = new SimpleDateFormat("HH:mm");
                        Date time1 = format.parse(b.getPickUpTime());
                        Date time2 = format.parse(booking.getReturnTime());
                        if (time1.getTime() < time2.getTime()) {
                            throw new CustomException("Couldn't extend due to other bookings ", HttpStatus.BAD_REQUEST);
                        }
                    }

                }

            }
            Notification notification = new Notification();
            SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd yyyy");
            Date date = new Date();
            notification.setNotification("has extended a booking");
            notification.setUser(user);
            notification.setTime(formatter.format(date));
            notification.setType("extend");
            notificationRepository.save(notification);
            existingBooking.setReturnTime(booking.getReturnTime());
            bookingRepository.save(existingBooking);
            return new ResponseEntity<>("Booking extended Successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> cancelBooking(int id, String username, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        User user = userRepository.findByUserName(username);
        Notification notification = new Notification();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd yyyy");
        Date date = new Date();
        notification.setNotification("cancelled a booking");
        notification.setUser(user);
        notification.setTime(formatter.format(date));
        notification.setType("cancel");
        notificationRepository.save(notification);
        existingBooking.setNote(booking.getNote());
        existingBooking.setStatus("cancelled");
        bookingRepository.save(existingBooking);
        return new ResponseEntity<>("Booking cancelled successfully", HttpStatus.OK);
    }

    public List<Booking> getLatest() {
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> latest = bookings.subList(bookings.size() - 3, bookings.size());
        Collections.reverse(latest);

        return latest;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings;
    }

    public ResponseEntity<String> collectVehicle(int id) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        existingBooking.setStatus("collected");
        Vehicle vehicle = vehicleRepository.findById(existingBooking.getVehicle().getId()).orElse(null);
        vehicle.setStatus("unavailable");
        vehicleRepository.save(vehicle);
        bookingRepository.save(existingBooking);
        return new ResponseEntity<>("vehicle collected", HttpStatus.OK);
    }

    public ResponseEntity<String> returnVehicle(int id) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        existingBooking.setStatus("returned");
        Vehicle vehicle = vehicleRepository.findById(existingBooking.getVehicle().getId()).orElse(null);
        vehicle.setStatus("available");
        vehicleRepository.save(vehicle);
        bookingRepository.save(existingBooking);
        return new ResponseEntity<>("vehicle returned", HttpStatus.OK);
    }

    public ResponseEntity<String> refusedVehicle(int id) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        existingBooking.setStatus("refused");
        User user = userRepository.findById(existingBooking.getUser().getId()).orElse(null);
        user.setStatus("blacklist");
        userRepository.save(user);
        bookingRepository.save(existingBooking);
        return new ResponseEntity<>("refused to collect", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteBooking(int id) {
        bookingRepository.deleteById(id);
        return new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK);
    }

}
