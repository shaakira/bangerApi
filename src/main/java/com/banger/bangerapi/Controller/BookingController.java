package com.banger.bangerapi.Controller;



import com.banger.bangerapi.Models.Booking;
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

    @GetMapping ("/confirmedBooking/{userName}")
    public ResponseEntity<?> confirmedBooking(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(bookingService.getConfirmedBookings(userName));
    }

    @GetMapping ("/pastBooking/{userName}")
    public ResponseEntity<?> pastBooking(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(bookingService.getPastBookings(userName));
    }
    @PostMapping ("/validateDate")
    public ResponseEntity<?> validateDate(@RequestBody BookingDTO booking) {
        return bookingService.validateBookingDate(booking);
    }

    @PostMapping ("/validateDriver/{username}")
    public ResponseEntity<?> validateDriver(@RequestBody Driver driver,@PathVariable("username") String username) {
        String sss=";";
        return bookingService.validateDriver(driver, username);
    }
    @PostMapping("/extendBooking/{id}/{username}")
    public ResponseEntity<?> extendBooking(@PathVariable("id") int id,@PathVariable("username")String username,@RequestBody Booking booking) throws Exception {
        return bookingService.extendBooking(booking,id,username);
    }
    @PostMapping("/cancelBooking/{id}/{username}")
    public ResponseEntity<?> cancelBooking(@PathVariable("id") int id,@PathVariable("username") String username,@RequestBody Booking booking) throws Exception {
        return bookingService.cancelBooking(id,username,booking);
    }
    @GetMapping("/getLatest")
    public ResponseEntity<?> getLatestBookings(){
        return ResponseEntity.ok(bookingService.getLatest());
    }

    @GetMapping("/getAllBookings")
    public ResponseEntity<?> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    @GetMapping("/collectVehicle/{id}")
    public ResponseEntity<?> collectVehicle(@PathVariable int id) {
        return bookingService.collectVehicle(id);
    }
    @GetMapping("/returnedVehicle/{id}")
    public ResponseEntity<?> returnedVehicle(@PathVariable int id) {
        return bookingService.returnVehicle(id);
    }
    @GetMapping("/refusedVehicle/{id}")
    public ResponseEntity<?> refusedVehicle(@PathVariable int id) {
        return bookingService.refusedVehicle(id);
    }
    @GetMapping("/deleteBooking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable int id) {
        return bookingService.deleteBooking(id);
    }
}
