package com.banger.bangerapi.Controller;



import com.banger.bangerapi.Models.DTO.BookingDTO;
import com.banger.bangerapi.Models.Driver;
import com.banger.bangerapi.Service.BookingService;
import com.banger.bangerapi.Service.CSVService;
import com.banger.bangerapi.Service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RequestMapping("api/booking")
@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    CSVService csvService;

    @Autowired
    InsuranceService insuranceService;

    @PostMapping("/addBooking")
    public ResponseEntity<?> AddBooking(@RequestBody BookingDTO booking) {

        return bookingService.addBooking(booking);
    }

    @GetMapping ("/viewBooking/{username}")
    public ResponseEntity<?> viewBooking(@PathVariable("username") String username) {
        return bookingService.getAllPendingBooking(username);
    }


    @PostMapping ("/validateDate")
    public ResponseEntity<?> validateDate(@RequestBody BookingDTO booking) {
        return bookingService.validateBookingDate(booking);
    }

    @PostMapping ("/validateDriver/{username}")
    public ResponseEntity<?> validatedriver(@RequestBody Driver driver,@PathVariable("username") String username) {
        String sss=";";
        return bookingService.validateDriver(driver, username);
    }

//    @PostMapping ("/validateLicense")
//    public ResponseEntity<?> validateLicense(@RequestBody Driver driver) {
//        return csvService.checkLicense(driver.getLicenseNo());
//    }
//
//    @PostMapping ("/validateNIC")
//    public ResponseEntity<?> validateNIC(@RequestBody Driver driver) {
//        return insuranceService.validateNIC(driver.getNic());
//    }
}
