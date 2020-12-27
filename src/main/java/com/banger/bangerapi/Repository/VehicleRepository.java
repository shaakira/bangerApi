package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository  extends JpaRepository<Vehicle,Integer> {

}
