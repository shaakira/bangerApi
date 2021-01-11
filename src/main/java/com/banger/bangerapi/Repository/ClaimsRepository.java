package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims,Integer> {
}

