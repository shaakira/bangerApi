package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Driver;
import com.banger.bangerapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Integer> {
    Driver findByFirstName(String name);
    Driver findByEmail(String email);

}