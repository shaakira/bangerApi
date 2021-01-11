package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Booking;
import org.springframework.stereotype.Repository;
import com.banger.bangerapi.Models.Booking;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByUser(User user);

    @Override
    Optional<Booking> findById(Integer integer);


    List<Booking>  findByVehicleAndPickUpDateLessThanEqualAndReturnDateGreaterThanEqual(Vehicle vehicle,Date returnDate,Date pickUpDate);
//    @Query(value="SELECT * from booking  where status=(:Pending)", nativeQuery = true)


    List<Booking> findBookingByStatus(String status);

}
