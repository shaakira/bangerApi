package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Booking;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser(User user);

    @Override
    Optional<Booking> findById(Integer integer);
    List<Booking> findByVehicleAndPickUpDateLessThanEqualAndReturnDateGreaterThanEqual(Vehicle vehicle, Date returnDate, Date pickUpDate);
//    @Query(value="SELECT * from booking  where status=(:Pending)", nativeQuery = true)
    List<Booking> findBookingByStatus(String status);
    @Query("select booking from Booking booking where  booking.status='confirmed' and booking.user.userName=:userName")
    List<Booking> findConfirmedBooking(@Param("userName") String userName);
    @Query("select booking from Booking booking where  booking.status='returned' and booking.user.userName=:userName")
    List<Booking> findPastBooking(@Param("userName") String userName);
    @Query("select booking from Booking booking where  booking.status='confirmed' and booking.pickUpDate=:returnDate and booking.vehicle.Id=:vehicleId")
    List<Booking> findBookingTime(@Param("returnDate") Date returnDate, @Param("vehicleId") int vehicleId);

}
