package com.banger.bangerapi.Service;


import com.banger.bangerapi.Models.Booking;
import com.banger.bangerapi.Models.BookingEquipment;
import com.banger.bangerapi.Repository.BookingEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingEquipmnetService {

    @Autowired
    BookingEquipmentRepository bookingEquipmentRepository;
    public Integer addEquipment(List<com.banger.bangerapi.Models.BookingEquipment> books, Booking booking){
        int total = 0;
        for (BookingEquipment bookingEquipment: books
                ) {
            int amount=Integer.parseInt(bookingEquipment.getPrice());
            amount=amount*bookingEquipment.getCount();
            total=total+amount;
            bookingEquipment.setBooking(booking);
        }
        bookingEquipmentRepository.saveAll(books);
        return total;
    }
}
